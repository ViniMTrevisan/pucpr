import os
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText


SMTP_SERVER = "smtp.gmail.com"
SMTP_PORT = 587


def getenv_required(name: str) -> str:
    value = os.getenv(name, "").strip()
    if not value:
        raise RuntimeError(f"Variavel obrigatoria ausente: {name}")
    return value


username = getenv_required("GMAIL_USERNAME")
password = getenv_required("GMAIL_APP_PASSWORD")
email_from = os.getenv("EMAIL_FROM", username).strip() or username
email_to = getenv_required("EMAIL_TO")
subject = os.getenv("EMAIL_SUBJECT", "Teste SMTP com autenticacao Gmail")
body = os.getenv(
    "EMAIL_BODY",
    "Mensagem enviada via Python (smtplib) com senha de aplicativo do Gmail.",
)

message = MIMEMultipart()
message["From"] = email_from
message["To"] = email_to
message["Subject"] = subject
message.attach(MIMEText(body, "plain"))

with smtplib.SMTP(SMTP_SERVER, SMTP_PORT, timeout=30) as smtp:
    smtp.starttls()
    smtp.login(username, password)
    smtp.sendmail(email_from, email_to, message.as_string())

print("E-mail enviado com sucesso.")
