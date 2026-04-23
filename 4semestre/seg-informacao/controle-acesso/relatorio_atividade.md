Guilherme Reis, Nicolas Lobo, Vinicius Trevisan - 06/04/2026

## Relatorio de Execucao - Controle de Acesso / Permissoes do Linux em Python

### 1. Objetivo
Implementar um programa em Python que:
- Registra data e hora de cada execucao.
- Armazena os registros incrementalmente no arquivo `permissao.txt`.
- Controla as permissoes do arquivo com `os.chmod` usando constantes da biblioteca `stat`.

### 2. Arquivo fonte entregue
Arquivo: `controle_acesso.py`

Implementacao realizada:
1. Verifica se `permissao.txt` existe com `os.path.exists`.
2. Se existir, altera permissao para `stat.S_IRWXU` (700) antes da escrita.
3. Obtem data e hora do sistema com `datetime.now()`.
4. Abre `permissao.txt` em modo append (`"a"`) para escrita incremental.
5. Grava `Data: DD/MM/AAAA | Hora: HH:MM:SS` com quebra de linha.
6. Ao final, altera a permissao para `stat.S_IRUSR` (400), somente leitura do proprietario.

### 3. Evidencias de execucao
Comandos executados:

```bash
python3 controle_acesso.py
python3 controle_acesso.py
ls -l permissao.txt
stat -f '%OLp %Sp' permissao.txt
cat permissao.txt
```

Saida observada:

```text
Arquivo 'permissao.txt' nao existe: ele sera criado.
Registro incluido em 'permissao.txt'.
Permissao final de 'permissao.txt': 400 (somente leitura do proprietario).

Arquivo 'permissao.txt' encontrado: permissao alterada para 700 (rwx do proprietario).
Registro incluido em 'permissao.txt'.
Permissao final de 'permissao.txt': 400 (somente leitura do proprietario).

-r--------@ 1 vinitrevisan  staff  68 ... permissao.txt
400 -r--------

Data: 06/04/2026 | Hora: 11:34:01
Data: 06/04/2026 | Hora: 11:34:02
```

### 4. Resultado
Os requisitos da especificacao foram atendidos:
- Escrita incremental de data/hora em `permissao.txt`.
- Verificacao de existencia do arquivo em toda execucao.
- Alteracao de permissao para escrita antes de gravar (quando o arquivo ja existe).
- Permissao final do arquivo configurada como somente leitura do proprietario (400).
