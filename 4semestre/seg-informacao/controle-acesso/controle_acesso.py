# Guilherme Reis, Nicolas Lobo, Vinicius Trevisan - 06/04/2026

import os
import stat
from datetime import datetime

ARQUIVO = "permissao.txt"


def main() -> None:
    arquivo_existe = os.path.exists(ARQUIVO)

    if arquivo_existe:
        os.chmod(ARQUIVO, stat.S_IRWXU)
        print(f"Arquivo '{ARQUIVO}' encontrado: permissão alterada para 700 (rwx do proprietário).")
    else:
        print(f"Arquivo '{ARQUIVO}' não existe: ele será criado.")

    agora = datetime.now()
    data_atual = agora.strftime("%d/%m/%Y")
    hora_atual = agora.strftime("%H:%M:%S")

    with open(ARQUIVO, "a", encoding="utf-8") as arquivo:
        arquivo.write(f"Data: {data_atual} | Hora: {hora_atual}\n")

    os.chmod(ARQUIVO, stat.S_IRUSR)
    print(f"Registro incluído em '{ARQUIVO}'.")
    print(f"Permissão final de '{ARQUIVO}': 400 (somente leitura do proprietário).")


if __name__ == "__main__":
    main()
