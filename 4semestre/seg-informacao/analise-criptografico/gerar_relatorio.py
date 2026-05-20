import csv
import platform
import subprocess
from collections import defaultdict
from pathlib import Path

from reportlab.lib import colors
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import cm
from reportlab.platypus import (
    Image,
    PageBreak,
    Paragraph,
    Preformatted,
    SimpleDocTemplate,
    Spacer,
    Table,
    TableStyle,
)

ORDER = ["RSA-1024", "RSA-2048", "RSA-4096", "RSA-8192", "AES-128", "AES-256"]


def get_system_info() -> list[list[str]]:
    info = []
    info.append(["Sistema operacional", platform.platform()])

    try:
        cpu = subprocess.check_output(
            ["sysctl", "-n", "machdep.cpu.brand_string"], text=True
        ).strip()
    except Exception:
        cpu = platform.processor() or "Nao identificado"
    info.append(["Processador", cpu])

    try:
        mem_bytes = int(subprocess.check_output(["sysctl", "-n", "hw.memsize"], text=True).strip())
        mem_gb = mem_bytes / (1024**3)
        mem_text = f"{mem_gb:.1f} GB"
    except Exception:
        mem_text = "Nao identificado"
    info.append(["Memoria RAM", mem_text])

    try:
        disk = subprocess.check_output(["df", "-h", "/"], text=True).splitlines()[1]
        parts = [p for p in disk.split(" ") if p]
        disk_text = f"Tamanho: {parts[1]} | Usado: {parts[2]} | Livre: {parts[3]}"
    except Exception:
        disk_text = "Nao identificado"
    info.append(["Armazenamento", disk_text])

    return info


def load_results(csv_path: Path) -> tuple[dict[str, list[float]], list[int]]:
    by_algo = defaultdict(list)
    iterations = set()
    with csv_path.open("r", encoding="utf-8") as f:
        reader = csv.DictReader(f)
        for row in reader:
            algo = row["algoritmo"]
            by_algo[algo].append(float(row["tempo_ms"]))
            iterations.add(int(row["iteracao"]))
    return by_algo, sorted(iterations)


def build_table(by_algo: dict[str, list[float]], iterations: list[int]) -> list[list[str]]:
    header = ["Algoritmo"] + [f"It. {i} (ms)" for i in iterations] + ["Media (ms)"]
    rows = [header]
    for algo in ORDER:
        times = by_algo.get(algo, [])
        avg = sum(times) / len(times) if times else 0.0
        row = [algo] + [f"{t:.3f}" for t in times] + [f"{avg:.3f}"]
        rows.append(row)
    return rows


def txt_to_png(txt_path: Path, out_png: Path) -> bool:
    try:
        from PIL import Image as PILImage
        from PIL import ImageDraw, ImageFont
    except Exception:
        return False

    lines = txt_path.read_text(encoding="utf-8").splitlines()
    font = ImageFont.load_default()
    line_height = 18
    width = 1400
    height = max(300, 40 + len(lines) * line_height)

    img = PILImage.new("RGB", (width, height), color=(245, 245, 245))
    draw = ImageDraw.Draw(img)
    y = 20
    for line in lines:
        draw.text((20, y), line, fill=(10, 10, 10), font=font)
        y += line_height
    out_png.parent.mkdir(parents=True, exist_ok=True)
    img.save(out_png)
    return True


def main() -> None:
    base = Path(".")
    csv_path = base / "resultados.csv"
    report_path = base / "relatorio_analise_desempenho_criptografico.pdf"

    by_algo, iterations = load_results(csv_path)
    table_data = build_table(by_algo, iterations)
    sys_info = get_system_info()

    styles = getSampleStyleSheet()
    normal = styles["Normal"]
    normal.fontSize = 10
    title = styles["Title"]
    code_style = ParagraphStyle("Code", parent=normal, fontName="Courier", leading=11, fontSize=8)

    story = []
    story.append(Paragraph("Vinicius Trevisan - Data: 28/04/2026", normal))
    story.append(Spacer(1, 0.3 * cm))
    story.append(Paragraph("Atividade Pratica - Analise de desempenho criptografico", title))
    story.append(Spacer(1, 0.3 * cm))
    story.append(
        Paragraph(
            "Texto cifrado no experimento: "
            "\"RSA: algoritmo dos professores do MIT: Rivest, Shamir e Adleman\".",
            normal,
        )
    )
    story.append(
        Paragraph(
            "Cada iteracao registrou o tempo total (incluindo geracao da chave e cifragem). "
            "Foram feitas 5 iteracoes para cada algoritmo.",
            normal,
        )
    )
    story.append(Spacer(1, 0.4 * cm))

    t = Table(table_data, repeatRows=1)
    t.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, 0), colors.lightgrey),
                ("GRID", (0, 0), (-1, -1), 0.5, colors.grey),
                ("ALIGN", (1, 1), (-1, -1), "RIGHT"),
                ("FONTNAME", (0, 0), (-1, 0), "Helvetica-Bold"),
                ("FONTSIZE", (0, 0), (-1, -1), 9),
                ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
            ]
        )
    )
    story.append(Paragraph("Resultados e media de tempo por algoritmo", styles["Heading2"]))
    story.append(t)
    story.append(Spacer(1, 0.4 * cm))

    story.append(Paragraph("Configuracao do sistema utilizado", styles["Heading2"]))
    sys_table = Table([["Item", "Valor"]] + sys_info, colWidths=[5 * cm, 11 * cm])
    sys_table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, 0), colors.lightgrey),
                ("GRID", (0, 0), (-1, -1), 0.5, colors.grey),
                ("FONTNAME", (0, 0), (-1, 0), "Helvetica-Bold"),
                ("FONTSIZE", (0, 0), (-1, -1), 9),
                ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
            ]
        )
    )
    story.append(sys_table)

    story.append(PageBreak())
    story.append(Paragraph("Registro das iteracoes (printscreen)", styles["Heading2"]))
    for i in range(1, 6):
        txt_path = base / "runs" / f"iteracao_{i}.txt"
        png_path = base / "runs" / f"printscreen_iteracao_{i}.png"
        if txt_path.exists():
            created = txt_to_png(txt_path, png_path)
            if created and png_path.exists():
                story.append(Paragraph(f"Iteracao {i}", styles["Heading3"]))
                story.append(Image(str(png_path), width=17 * cm, height=4 * cm))
                story.append(Spacer(1, 0.2 * cm))

    story.append(PageBreak())
    story.append(Paragraph("Codigo fonte utilizado (Python)", styles["Heading2"]))
    code = (base / "benchmark_crypto.py").read_text(encoding="utf-8")
    story.append(Preformatted(code, code_style))

    story.append(PageBreak())
    story.append(Paragraph("Referencias", styles["Heading2"]))
    refs = [
        "Python Software Foundation. Python 3.11 Documentation.",
        "PyCryptodome Documentation: https://pycryptodome.readthedocs.io/",
        "NIST FIPS 197 - Advanced Encryption Standard (AES).",
        "PKCS #1: RSA Cryptography Specifications.",
        "GitHub Copilot (IA) como apoio para estruturacao do experimento e do relatorio.",
    ]
    for r in refs:
        story.append(Paragraph(f"- {r}", normal))

    doc = SimpleDocTemplate(
        str(report_path),
        pagesize=A4,
        leftMargin=1.5 * cm,
        rightMargin=1.5 * cm,
        topMargin=1.5 * cm,
        bottomMargin=1.5 * cm,
    )
    doc.build(story)
    print(f"Relatorio gerado em: {report_path.resolve()}")


if __name__ == "__main__":
    main()
