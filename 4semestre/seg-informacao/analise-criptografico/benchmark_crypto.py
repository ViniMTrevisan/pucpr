import argparse
import csv
import os
from pathlib import Path
from time import perf_counter

from Crypto.Cipher import AES, PKCS1_OAEP
from Crypto.PublicKey import RSA
from Crypto.Random import get_random_bytes
from Crypto.Util.Padding import pad

TEXT = "RSA: algoritmo dos professores do MIT: Rivest, Shamir e Adleman"

ALGORITHMS = [
    ("RSA-1024", "RSA", 1024),
    ("RSA-2048", "RSA", 2048),
    ("RSA-4096", "RSA", 4096),
    ("RSA-8192", "RSA", 8192),
    ("AES-128", "AES", 128),
    ("AES-256", "AES", 256),
]


def run_rsa(bits: int) -> tuple[float, int]:
    start = perf_counter()
    key = RSA.generate(bits)
    cipher = PKCS1_OAEP.new(key.publickey())
    ciphertext = cipher.encrypt(TEXT.encode("utf-8"))
    elapsed_ms = (perf_counter() - start) * 1000
    return elapsed_ms, len(ciphertext)


def run_aes(bits: int) -> tuple[float, int]:
    start = perf_counter()
    key = get_random_bytes(bits // 8)
    iv = get_random_bytes(16)
    cipher = AES.new(key, AES.MODE_CBC, iv=iv)
    ciphertext = cipher.encrypt(pad(TEXT.encode("utf-8"), AES.block_size))
    elapsed_ms = (perf_counter() - start) * 1000
    return elapsed_ms, len(ciphertext)


def main() -> None:
    parser = argparse.ArgumentParser(description="Executa uma iteracao do experimento cripto.")
    parser.add_argument("--iteration", type=int, required=True, help="Numero da iteracao (1-5).")
    parser.add_argument("--csv", default="resultados.csv", help="Arquivo CSV de saida.")
    args = parser.parse_args()

    out_path = Path(args.csv)
    out_path.parent.mkdir(parents=True, exist_ok=True)

    file_exists = out_path.exists()
    with out_path.open("a", newline="", encoding="utf-8") as f:
        writer = csv.writer(f)
        if not file_exists:
            writer.writerow(["iteracao", "algoritmo", "tipo", "bits", "tempo_ms", "tamanho_cifra_bytes"])

        print(f"=== Iteracao {args.iteration} ===")
        for name, kind, bits in ALGORITHMS:
            if kind == "RSA":
                elapsed_ms, cipher_len = run_rsa(bits)
            else:
                elapsed_ms, cipher_len = run_aes(bits)

            writer.writerow([args.iteration, name, kind, bits, round(elapsed_ms, 3), cipher_len])
            print(f"{name:8} | {elapsed_ms:10.3f} ms | cifra: {cipher_len} bytes")

    print(f"\nResultado registrado em: {os.path.abspath(out_path)}")


if __name__ == "__main__":
    main()
