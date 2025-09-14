#!/usr/bin/env python3
"""
auditoria.py
CLI para auditoria de qualidade com checklist configurável, cálculo de aderência,
registro e acompanhamento de NC (não conformidade) com escalonamento e templates de comunicação.
Banco: SQLite (audits.db)
"""

import argparse
import sqlite3
import json
import os
import datetime
from datetime import timezone
from pathlib import Path
from typing import Dict, Any, List, Optional
import textwrap
import uuid


DB_PATH = "audits.db"

# -------------------------
# Helpers DB
# -------------------------
def get_conn():
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    return conn

def init_db():
    if os.path.exists(DB_PATH):
        print(f"[i] DB já existe em {DB_PATH}")
    conn = get_conn()
    cur = conn.cursor()
    cur.executescript("""
    CREATE TABLE IF NOT EXISTS checklists (
        id TEXT PRIMARY KEY,
        artifact TEXT,
        version TEXT,
        raw_json TEXT,
        created_at TEXT
    );
    CREATE TABLE IF NOT EXISTS audits (
        id TEXT PRIMARY KEY,
        checklist_id TEXT,
        auditor TEXT,
        started_at TEXT,
        finished_at TEXT,
        score REAL,
        total_possible REAL,
        notes TEXT,
        FOREIGN KEY(checklist_id) REFERENCES checklists(id)
    );
    CREATE TABLE IF NOT EXISTS answers (
        id TEXT PRIMARY KEY,
        audit_id TEXT,
        question_id TEXT,
        raw_answer TEXT,
        is_conform INTEGER,
        weight REAL
    );
    CREATE TABLE IF NOT EXISTS nonconformities (
        id TEXT PRIMARY KEY,
        audit_id TEXT,
        question_id TEXT,
        description TEXT,
        severity TEXT,
        assigned_to TEXT,
        created_at TEXT,
        due_date TEXT,
        status TEXT,
        escalation_level INTEGER,
        history TEXT
    );
    """)
    conn.commit()
    conn.close()
    print("[i] DB inicializado.")


# -------------------------
# Checklist load/save
# -------------------------
def load_checklist_from_file(path: str) -> Dict[str, Any]:
    with open(path, "r", encoding="utf-8") as f:
        data = json.load(f)
    return data

def save_checklist_to_db(checklist: Dict[str, Any]) -> str:
    conn = get_conn()
    cur = conn.cursor()
    cid = checklist.get("id", str(uuid.uuid4()))
    raw = json.dumps(checklist, ensure_ascii=False)
    cur.execute("INSERT OR REPLACE INTO checklists (id, artifact, version, raw_json, created_at) VALUES (?, ?, ?, ?, ?)",
                (cid, checklist.get("artifact"), checklist.get("version"), raw, datetime.datetime.now(timezone.utc).isoformat()))
    conn.commit()
    conn.close()
    return cid

def pick_checklist() -> Optional[Dict[str, Any]]:
    conn = get_conn()
    cur = conn.cursor()
    cur.execute("SELECT id, artifact, version FROM checklists")
    rows = cur.fetchall()
    if not rows:
        print("[!] Nenhum checklist salvo no DB. Use --checklist para carregar um JSON primeiro.")
        conn.close()
        return None
    print("Checklists disponíveis:")
    for r in rows:
        print(f"- {r['id']} : {r['artifact']} (v{r['version']})")
    chosen = rows[0]["id"]
    print(f"[i] Usando o primeiro por padrão: {chosen}")
    cur.execute("SELECT raw_json FROM checklists WHERE id = ?", (chosen,))
    raw = cur.fetchone()["raw_json"]
    conn.close()
    return json.loads(raw)

# -------------------------
# Run an audit (interactive)
# -------------------------
def evaluate_answer(q: Dict[str,Any], answer: Any) -> bool:
    """
    Decide se a resposta é conforme.
    - boolean: answer boolean True -> conform
    - choice: if 'accept' defined, check membership; else accept non-empty
    - text: if 'accept' defined check contains any accept; else non-empty -> info only
    """
    qtype = q.get("type", "text")
    if qtype == "boolean":
        # If checklist explicitly defines accepted boolean answers, respect it.
        # 'accept' can be a list of strings that are considered conforming values
        if "accept" in q and q.get("accept") is not None:
            accept = [str(x).strip().lower() for x in q.get("accept")]
            a = str(answer).strip().lower()
            return a in accept
        # 'expected' can be boolean True/False indicating which boolean means conform
        if "expected" in q:
            try:
                expected = bool(q.get("expected"))
                # normalize answer to boolean
                if isinstance(answer, bool):
                    return answer is expected
                if isinstance(answer, (int, float)):
                    return bool(answer) is expected
                a = str(answer).strip().lower()
                truthy = a in ("1", "true", "yes", "y", "sim", "s")
                return truthy is expected
            except Exception:
                pass
        # Default behaviour: consider truthy values as conforming
        if isinstance(answer, bool):
            return answer is True
        if isinstance(answer, (int, float)):
            return bool(answer)
        a = str(answer).strip().lower()
        return a in ("1", "true", "yes", "y", "sim", "s")
    if qtype == "choice":
        accept = q.get("accept")
        if accept:
            return str(answer).strip().lower() in [a.lower() for a in accept]
        return bool(answer)
    if qtype == "text":
        accept = q.get("accept")
        if accept:
            # if any accepted token appears in answer
            ans = str(answer).lower()
            return any(tok.lower() in ans for tok in accept)
        # text may not contribute to score
        return False
    return False

def run_audit(checklist: Dict[str,Any], auditor: str, answers_file: Optional[str]=None) -> str:
    questions: List[Dict[str,Any]] = checklist.get("questions", [])
    audit_id = str(uuid.uuid4())
    conn = get_conn()
    cur = conn.cursor()
    start = datetime.datetime.now(timezone.utc).isoformat()
    cur.execute("INSERT INTO audits (id, checklist_id, auditor, started_at) VALUES (?,?,?,?)",
                (audit_id, checklist.get("id", str(uuid.uuid4())), auditor, start))
    total_possible = 0.0
    total_obtained = 0.0

    # Optional batch answers
    batch_answers = {}
    if answers_file:
        with open(answers_file, "r", encoding="utf-8") as f:
            batch_answers = json.load(f)

    for q in questions:
        qid = q.get("id")
        weight = float(q.get("weight", 1))
        # weight 0 -> informative
        if weight > 0:
            total_possible += weight

        # get answer
        if answers_file and qid in batch_answers:
            ans = batch_answers[qid]
            print(f"[batch] {qid}: {ans}")
        else:
            # interactive prompt
            prompt = f"{qid} - {q.get('text')} (tipo={q.get('type')}, peso={weight}) >> "
            ans = input(prompt)

            # convert boolean-like
            if q.get("type") == "boolean":
                ans = ans.strip().lower() in ("y","yes","s","sim","true","1")

        conform = evaluate_answer(q, ans)
        if conform and weight>0:
            total_obtained += weight

        # save answer
        cur.execute("INSERT INTO answers (id, audit_id, question_id, raw_answer, is_conform, weight) VALUES (?,?,?,?,?,?)",
                    (str(uuid.uuid4()), audit_id, qid, json.dumps(ans, ensure_ascii=False), int(conform), weight))
        # if non conforming and weight>0 create NC
        if weight>0 and not conform:
            print(f"[!] Não conformidade detectada para {qid}.")
            nc_id = str(uuid.uuid4())
            created_at = datetime.datetime.now(timezone.utc).isoformat()
            # severity heuristic: weight >=2 -> medium/high
            severity = "low"
            if weight >= 2:
                severity = "medium"
            if weight >= 3:
                severity = "high"
            cur.execute("""INSERT INTO nonconformities
                (id,audit_id,question_id,description,severity,assigned_to,created_at,due_date,status,escalation_level,history)
                VALUES (?,?,?,?,?,?,?,?,?,?,?)""",
                        (nc_id, audit_id, qid, f"Resposta: {ans}", severity, None, created_at, None, "open", 0, json.dumps([{"t":created_at,"event":"created"}])))
            print(f"  NC registrada: {nc_id} (severity={severity})")

    score = (total_obtained/total_possible*100) if total_possible>0 else 100.0
    finish = datetime.datetime.now(timezone.utc).isoformat()
    cur.execute("UPDATE audits SET finished_at=?, score=?, total_possible=?, notes=? WHERE id=?",
                (finish, score, total_possible, None, audit_id))
    conn.commit()
    conn.close()
    print(f"[i] Auditoria finalizada. ID={audit_id} - Score: {score:.2f}%")
    return audit_id

# -------------------------
# NC management
# -------------------------
def list_nc(filter_status: Optional[str]=None):
    conn = get_conn()
    cur = conn.cursor()
    q = "SELECT * FROM nonconformities"
    args = []
    if filter_status:
        q += " WHERE status = ?"
        args.append(filter_status)
    cur.execute(q, args)
    rows = cur.fetchall()
    if not rows:
        print("[i] Nenhuma NC encontrada.")
    for r in rows:
        print(f"- ID:{r['id']} audit:{r['audit_id']} q:{r['question_id']} status:{r['status']} severity:{r['severity']} assigned:{r['assigned_to']} due:{r['due_date']}")
    conn.close()

def update_nc(nc_id: str, status: Optional[str]=None, assigned_to: Optional[str]=None, due_in_days: Optional[int]=None, note: Optional[str]=None):
    conn = get_conn()
    cur = conn.cursor()
    cur.execute("SELECT * FROM nonconformities WHERE id=?", (nc_id,))
    r = cur.fetchone()
    if not r:
        print("[!] NC não encontrada")
        return
    history = json.loads(r["history"]) if r["history"] else []
    now = datetime.datetime.now(timezone.utc).isoformat()
    if status:
        history.append({"t": now, "event": f"status->{status}"})
    if assigned_to:
        history.append({"t": now, "event": f"assigned->{assigned_to}"})
    if note:
        history.append({"t": now, "note": note})
    due = r["due_date"]
    if due_in_days is not None:
        due = (datetime.datetime.now(timezone.utc) + datetime.timedelta(days=due_in_days)).isoformat()
    cur.execute("""UPDATE nonconformities SET status=?, assigned_to=?, due_date=?, history=? WHERE id=?""",
                (status or r["status"], assigned_to or r["assigned_to"], due, json.dumps(history, ensure_ascii=False), nc_id))
    conn.commit()
    conn.close()
    print(f"[i] NC {nc_id} atualizada.")

def auto_escalate():
    """Verifica NCs abertas e aplica regras de escalonamento conforme checklist escalation thresholds."""
    conn = get_conn()
    cur = conn.cursor()
    # load escalation config from any checklist (just pick first)
    cur.execute("SELECT raw_json FROM checklists LIMIT 1")
    row = cur.fetchone()
    if not row:
        print("[!] Sem checklist cadastrado para obter regras de escalonamento.")
        return
    cfg = json.loads(row["raw_json"])
    esc_cfg = cfg.get("escalation", {})
    levels = esc_cfg.get("levels", ["team_lead","manager","quality_owner"])
    thresholds = esc_cfg.get("threshold_days", {"low":5,"medium":3,"high":1})
    # fetch open NCs
    cur.execute("SELECT * FROM nonconformities WHERE status IN ('open','in_progress')")
    ncs = cur.fetchall()
    for nc in ncs:
        created = datetime.datetime.fromisoformat(nc["created_at"])
        severity = nc["severity"]
        threshold = int(thresholds.get(severity, 5))
        delta = (datetime.datetime.now(timezone.utc) - created).days
        if delta >= threshold:
            # escalate
            new_level = min(nc["escalation_level"] + 1, len(levels)-1)
            history = json.loads(nc["history"] or "[]")
            now = datetime.datetime.now(timezone.utc).isoformat()
            history.append({"t": now, "event": f"escalated to {levels[new_level]}"})
            cur.execute("UPDATE nonconformities SET escalation_level=?, history=? WHERE id=?",
                        (new_level, json.dumps(history, ensure_ascii=False), nc["id"]))
            print(f"[!] NC {nc['id']} escalonada para {levels[new_level]} (level {new_level})")
            # generate communication template
            tmpl = render_nc_notification(nc, levels[new_level])
            fname = f"nc_notify_{nc['id']}.txt"
            with open(fname, "w", encoding="utf-8") as f:
                f.write(tmpl)
            print(f"  [i] Template de comunicação salvo: {fname}")
    conn.commit()
    conn.close()

# -------------------------
# Communication templates
# -------------------------
def render_nc_notification(nc_row, target_role: str) -> str:
    # nc_row may be sqlite row or dict
    nc = dict(nc_row) if not isinstance(nc_row, dict) else nc_row
    subj = f"NC: {nc['id']} - {nc['question_id']} - Severidade {nc['severity']}"
    body = textwrap.dedent(f"""
    Para: {target_role}
    Assunto: {subj}

    Há uma não-conformidade pendente identificada na auditoria {nc['audit_id']}:
    - ID: {nc['id']}
    - Questão: {nc['question_id']}
    - Descrição: {nc['description']}
    - Severidade: {nc['severity']}
    - Status atual: {nc['status']}
    - Responsável atual: {nc['assigned_to']}

    Histórico de eventos:
    {json.dumps(json.loads(nc['history'] or "[]"), indent=2, ensure_ascii=False)}

    Ação requerida:
    - Revisar e atribuir responsável (se necessário).
    - Informar plano de ação e prazo.
    - Atualizar status no sistema assim que houver progresso.

    Obrigado,
    Sistema de Auditoria
    """).strip()
    return f"Subject: {subj}\n\n{body}"

# -------------------------
# Reports
# -------------------------
def generate_report(audit_id: str, out_path: Optional[str]=None):
    conn = get_conn()
    cur = conn.cursor()
    cur.execute("SELECT * FROM audits WHERE id=?", (audit_id,))
    a = cur.fetchone()
    if not a:
        print("[!] Auditoria não encontrada")
        return
    cur.execute("SELECT * FROM answers WHERE audit_id=?", (audit_id,))
    answers = cur.fetchall()
    cur.execute("SELECT * FROM nonconformities WHERE audit_id=?", (audit_id,))
    ncs = cur.fetchall()
    rep = {
        "audit_id": a["id"],
        "auditor": a["auditor"],
        "score": a["score"],
        "total_possible": a["total_possible"],
        "started_at": a["started_at"],
        "finished_at": a["finished_at"],
        "answers": [dict(x) for x in answers],
        "nonconformities": [dict(x) for x in ncs]
    }
    s = json.dumps(rep, indent=2, ensure_ascii=False)
    if out_path:
        with open(out_path, "w", encoding="utf-8") as f:
            f.write(s)
        print(f"[i] Relatório salvo em {out_path}")
    else:
        print(s)
    conn.close()

# -------------------------
# Video script generator
# -------------------------
def gen_video_script(audit_id: Optional[str], team_csv: str, max_minutes: int = 3) -> str:
    team = [t.strip() for t in team_csv.split(",") if t.strip()]
    if not team:
        team = ["Membro1","Membro2"]
    # fetch audit summary if provided
    summary = ""
    if audit_id:
        conn = get_conn()
        cur = conn.cursor()
        cur.execute("SELECT score, started_at, finished_at FROM audits WHERE id=?", (audit_id,))
        r = cur.fetchone()
        if r:
            summary = f"A auditoria obteve {r['score']:.2f}% de aderência. Iniciada: {r['started_at']}, finalizada: {r['finished_at']}."
        conn.close()
    # allocate speaking time (seconds)
    total_seconds = max_minutes * 60
    per = max(5, total_seconds // max(1,len(team)))  # at least 5s
    lines = []
    lines.append("Roteiro para vídeo de apresentação da ferramenta (<= {} min)".format(max_minutes))
    lines.append("")
    lines.append("Introdução (15s):")
    lines.append("  - (Porta-voz) Olá, somos a equipe X. Neste vídeo apresentamos nossa ferramenta automatizada de auditoria de qualidade.")
    lines.append("")
    if summary:
        lines.append("Resumo da auditoria:")
        lines.append(f"  - {summary}")
        lines.append("")
    lines.append("Demonstração (máx 120s):")
    lines.append("  - Mostre o CLI e execute as ações principais: rodar checklist, registrar NC, simular escalonamento, gerar relatório.")
    lines.append("")
    lines.append("Cada membro (cerca de {}s cada):".format(per))
    for i, name in enumerate(team):
        lines.append(f"  - {name}: fala de ~{per}s. Diga seu papel e explique rapidamente uma funcionalidade (ex: criação de checklist, gerenciamento de NC, comunicação).")
    lines.append("")
    lines.append("Encerramento (10s):")
    lines.append("  - (Todos juntos) Agradecimentos e como acessar o repositório / instruções.")
    lines.append("")
    lines.append("Dicas de gravação:")
    lines.append("  - Cada membro apareça por pelo menos 3 segundos dizendo seu nome e função.")
    lines.append("  - Fale de forma clara; mostre comandos no terminal rapidamente.")
    res = "\n".join(lines)
    # save
    fname = f"video_script_{audit_id or 'generic'}.txt"
    with open(fname, "w", encoding="utf-8") as f:
        f.write(res)
    print(f"[i] Roteiro salvo em {fname}")
    return res

# -------------------------
# CLI
# -------------------------
def main():
    p = argparse.ArgumentParser(description="Ferramenta de auditoria - CLI")
    sp = p.add_subparsers(dest="cmd")

    sp_init = sp.add_parser("init-db", help="Inicializa o DB")
    sp_load = sp.add_parser("load-checklist", help="Carrega checklist JSON para o DB")
    sp_load.add_argument("--file", required=True)

    sp_run = sp.add_parser("run-audit", help="Executa uma auditoria (interativa ou por arquivo)")
    sp_run.add_argument("--checklist", required=False, help="Arquivo JSON do checklist (se ausente pega do DB)")
    sp_run.add_argument("--auditor", required=True)
    sp_run.add_argument("--answers", required=False, help="JSON com respostas por id")

    sp_list = sp.add_parser("list-audits", help="Lista auditorias")
    sp_view = sp.add_parser("view-audit", help="Ver detalhes de uma auditoria")
    sp_view.add_argument("--id", required=True)

    sp_report = sp.add_parser("report", help="Gera relatório JSON da auditoria")
    sp_report.add_argument("--id", required=True)
    sp_report.add_argument("--out", required=False)

    sp_listnc = sp.add_parser("list-nc", help="Lista NCs")
    sp_listnc.add_argument("--status", required=False)

    sp_updnc = sp.add_parser("update-nc", help="Atualiza NC")
    sp_updnc.add_argument("--id", required=True)
    sp_updnc.add_argument("--status", required=False)
    sp_updnc.add_argument("--assign", required=False)
    sp_updnc.add_argument("--due-days", type=int, required=False)
    sp_updnc.add_argument("--note", required=False)

    sp_esc = sp.add_parser("auto-escalate", help="Roda verificação de escalonamento")

    args = p.parse_args()
    if args.cmd == "init-db":
        init_db()
    elif args.cmd == "load-checklist":
        chk = load_checklist_from_file(args.file)
        # add id if missing
        if "id" not in chk:
            chk["id"] = str(uuid.uuid4())
        save_checklist_to_db(chk)
        print("[i] Checklist carregado no DB.")
    elif args.cmd == "run-audit":
        if args.checklist:
            chk = load_checklist_from_file(args.checklist)
            if "id" not in chk:
                chk["id"] = str(uuid.uuid4())
            save_checklist_to_db(chk)
        else:
            chk = pick_checklist()
            if not chk:
                return
        run_audit(chk, args.auditor, args.answers)
    elif args.cmd == "list-audits":
        conn = get_conn()
        cur = conn.cursor()
        cur.execute("SELECT id,auditor,score,started_at,finished_at FROM audits ORDER BY started_at DESC")
        for r in cur.fetchall():
            print(f"- {r['id']} by {r['auditor']} score:{r['score']} ({r['started_at']} -> {r['finished_at']})")
        conn.close()
    elif args.cmd == "view-audit":
        generate_report(args.id, out_path=None)
    elif args.cmd == "report":
        generate_report(args.id, out_path=args.out)
    elif args.cmd == "list-nc":
        list_nc(args.status)
    elif args.cmd == "update-nc":
        update_nc(args.id, status=args.status, assigned_to=args.assign, due_in_days=args.due_days, note=args.note)
    elif args.cmd == "auto-escalate":
        auto_escalate()
    else:
        p.print_help()

if __name__ == "__main__":
    main()