import os
import sqlite3
import json
import datetime
import tempfile
import unittest
from pathlib import Path
import auditoria


class AuditoriaTests(unittest.TestCase):
    def test_gen_video_script_writes_file(self):
        with tempfile.TemporaryDirectory() as d:
            cwd = os.getcwd()
            try:
                os.chdir(d)
                fname = Path(d) / "video_script_generic.txt"
                if fname.exists():
                    fname.unlink()
                res = auditoria.gen_video_script(None, "Alice,Bob", max_minutes=1)
                self.assertTrue(fname.exists())
                txt = fname.read_text(encoding="utf-8")
                self.assertIn("Roteiro para vídeo", txt)
            finally:
                os.chdir(cwd)

    def test_generate_report_writes_file(self):
        with tempfile.TemporaryDirectory() as d:
            db = Path(d) / "audits.db"
            auditoria.DB_PATH = str(db)
            auditoria.init_db()
            conn = sqlite3.connect(auditoria.DB_PATH)
            cur = conn.cursor()
            aid = "test-audit-1"
            cur.execute("INSERT INTO audits (id, checklist_id, auditor, started_at, finished_at, score, total_possible, notes) VALUES (?,?,?,?,?,?,?,?)",
                        (aid, "chk1", "tester", datetime.datetime.utcnow().isoformat(), datetime.datetime.utcnow().isoformat(), 95.0, 100.0, None))
            conn.commit()
            conn.close()
            out = Path(d) / "report.json"
            auditoria.generate_report(aid, out_path=str(out))
            self.assertTrue(out.exists())
            data = json.loads(out.read_text(encoding="utf-8"))
            self.assertEqual(data["audit_id"], aid)

    def test_auto_escalate_writes_notification(self):
        with tempfile.TemporaryDirectory() as d:
            db = Path(d) / "audits.db"
            auditoria.DB_PATH = str(db)
            auditoria.init_db()
            cfg = {
                "artifact": "X",
                "version": "1",
                "escalation": {"levels": ["team_lead","manager"], "threshold_days": {"low": 1, "medium": 1, "high": 1}}
            }
            conn = sqlite3.connect(auditoria.DB_PATH)
            cur = conn.cursor()
            cid = "chk-escalation"
            cur.execute("INSERT OR REPLACE INTO checklists (id, artifact, version, raw_json, created_at) VALUES (?,?,?,?,?)",
                        (cid, cfg["artifact"], cfg["version"], json.dumps(cfg, ensure_ascii=False), datetime.datetime.utcnow().isoformat()))
            nc_id = "nc-old"
            created_at = (datetime.datetime.utcnow() - datetime.timedelta(days=2)).isoformat()
            cur.execute("INSERT INTO nonconformities (id,audit_id,question_id,description,severity,assigned_to,created_at,due_date,status,escalation_level,history) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                        (nc_id, "audit1", "Q1", "descr", "low", None, created_at, None, "open", 0, json.dumps([])))
            conn.commit()
            conn.close()
            cwd = os.getcwd()
            try:
                os.chdir(d)
                auditoria.auto_escalate()
                notif = Path(d) / f"nc_notify_{nc_id}.txt"
                self.assertTrue(notif.exists())
                txt = notif.read_text(encoding="utf-8")
                self.assertTrue("nc-old" in txt)
            finally:
                os.chdir(cwd)


if __name__ == '__main__':
    unittest.main()
