"""
Atividade Prática 02 — Autenticação (MFA com Firebase/Gmail)

Grupo:
- Guilherme Reis Carvalho
- Nicolas Lobo
- Vinicius Trevisan
"""

import os
import random
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText


def gerar_codigo_mfa(tamanho: int = 6) -> str:
    return "".join(str(random.randint(0, 9)) for _ in range(tamanho))


def enviar_email_com_codigo(
    smtp_server: str,
    smtp_port: int,
    username: str,
    app_password: str,
    sender_email: str,
    receiver_email: str,
) -> str:
    codigo = gerar_codigo_mfa()

    subject = "Seu código MFA"
    body = (
        "Olá!\n\n"
        "Seu código de verificação MFA é:\n"
        f"{codigo}\n\n"
        "Se você não solicitou este código, ignore esta mensagem.\n"
    )

    message = MIMEMultipart()
    message["From"] = sender_email
    message["To"] = receiver_email
    message["Subject"] = subject
    message.attach(MIMEText(body, "plain"))

    server = smtplib.SMTP(smtp_server, smtp_port)
    server.starttls()
    server.login(username, app_password)
    server.sendmail(sender_email, receiver_email, message.as_string())
    server.quit()

    return codigo


if __name__ == "__main__":
    smtp_server = "smtp.gmail.com"
    smtp_port = 587

    username = os.getenv("GMAIL_USERNAME")
    app_password = os.getenv("GMAIL_APP_PASSWORD")
    sender_email = os.getenv("SENDER_EMAIL")
    receiver_email = os.getenv("RECEIVER_EMAIL")

    missing = [
        key
        for key, value in {
            "GMAIL_USERNAME": username,
            "GMAIL_APP_PASSWORD": app_password,
            "SENDER_EMAIL": sender_email,
            "RECEIVER_EMAIL": receiver_email,
        }.items()
        if not value
    ]

    if missing:
        raise ValueError(
            "Defina as variáveis de ambiente ausentes: " + ", ".join(missing)
        )

    codigo_gerado = enviar_email_com_codigo(
        smtp_server=smtp_server,
        smtp_port=smtp_port,
        username=username,
        app_password=app_password,
        sender_email=sender_email,
        receiver_email=receiver_email,
    )

    print(f"E-mail enviado com sucesso. Código MFA gerado: {codigo_gerado}")
