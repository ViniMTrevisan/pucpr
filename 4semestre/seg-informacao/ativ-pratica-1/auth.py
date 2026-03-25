import time
from datetime import datetime, timedelta

USERS = {
    "alice": {"nome": "Alice Silva", "senha": "senha123"},
    "bob":   {"nome": "Bob Santos", "senha": "qwerty456"},
    "admin": {"nome": "Administrador", "senha": "admin@2024"},
}

# Estado de bloqueio por usuário
state = {uid: {"failed": 0, "block_count": 0, "block_until": None} for uid in USERS}


def format_time(minutes):
    if minutes < 60:
        return f"{minutes} minuto(s)"
    hours = minutes // 60
    return f"{hours} hora(s)"


def get_remaining(block_until):
    remaining = (block_until - datetime.now()).total_seconds()
    if remaining <= 0:
        return 0
    minutes = int(remaining // 60) + 1
    return minutes


def authenticate(user_id, senha):
    if user_id not in USERS:
        return None  # usuário não existe

    s = state[user_id]

    # Verifica bloqueio ativo
    if s["block_until"] and datetime.now() < s["block_until"]:
        mins = get_remaining(s["block_until"])
        print(f"Acesso bloqueado. Tente novamente em {format_time(mins)}.")
        return False

    # Senha correta
    if senha == USERS[user_id]["senha"]:
        s["failed"] = 0
        return True

    # Senha errada
    s["failed"] += 1

    if s["failed"] >= 3:
        duracao = 5 * (6 ** s["block_count"])
        s["block_until"] = datetime.now() + timedelta(minutes=duracao)
        s["block_count"] += 1
        s["failed"] = 0
        print(f"ID ou senha incorreto, tente novamente mais tarde.")
        print(f"Acesso bloqueado por {format_time(duracao)}.")
        return False

    return False


def main():
    print("=== Sistema de Autenticacao ===\n")

    while True:
        user_id = input("ID do usuario (ou 'sair' para encerrar): ").strip()

        if user_id.lower() == "sair":
            print("Encerrando o sistema.")
            break

        if user_id not in USERS:
            print("ID ou senha incorreto, tente novamente mais tarde.\n")
            continue

        s = state[user_id]

        # Verifica bloqueio antes de pedir senha
        if s["block_until"] and datetime.now() < s["block_until"]:
            mins = get_remaining(s["block_until"])
            print(f"Acesso bloqueado. Tente novamente em {format_time(mins)}.\n")
            continue

        senha = input("Senha: ").strip()

        resultado = authenticate(user_id, senha)

        if resultado is True:
            print(f"Bem vindo, {USERS[user_id]['nome']}!\n")
            break
        elif resultado is False:
            s = state[user_id]
            # Mensagem de tentativas restantes (se não estiver bloqueado)
            if not (s["block_until"] and datetime.now() < s["block_until"]):
                restantes = 3 - s["failed"]
                print(f"ID ou senha incorreto, tente novamente mais tarde. "
                      f"({restantes} tentativa(s) restante(s))\n")
        # resultado None já imprimiu a mensagem acima


if __name__ == "__main__":
    main()
