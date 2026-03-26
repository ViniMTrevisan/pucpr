#!/usr/bin/env python3
import argparse
import hashlib
from pathlib import Path


def calcular_hash(arquivo: Path, algoritmo: str) -> str:
    h = hashlib.new(algoritmo.lower())
    with arquivo.open('rb') as f:
        for chunk in iter(lambda: f.read(8192), b''):
            h.update(chunk)
    return h.hexdigest()


def main() -> int:
    parser = argparse.ArgumentParser(description='Gera ou valida hash de um arquivo.')
    parser.add_argument('arquivo', help='Caminho do arquivo')
    parser.add_argument(
        '-a', '--algoritmo', default='sha256',
        choices=sorted(hashlib.algorithms_guaranteed),
        help='Algoritmo de hash (padrao: sha256)'
    )
    parser.add_argument(
        '-c', '--check',
        help='Hash esperado para validacao. Se omitido, apenas calcula.'
    )
    args = parser.parse_args()

    arquivo = Path(args.arquivo)
    if not arquivo.is_file():
        print(f'Erro: arquivo nao encontrado: {arquivo}')
        return 2

    digest = calcular_hash(arquivo, args.algoritmo)

    if args.check:
        ok = digest.lower() == args.check.lower()
        print('VALIDO' if ok else 'INVALIDO')
        print(f'calculado={digest}')
        print(f'esperado={args.check}')
        return 0 if ok else 1

    print(digest)
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
