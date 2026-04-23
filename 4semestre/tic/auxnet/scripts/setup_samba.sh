#!/usr/bin/env bash
# =============================================================================
# AuxNet — Setup do Samba (servidor NAS)
# =============================================================================
#
# Projeto:  AuxNet (Equipe Píton — PUCPR CTIC 2026/S1)
# Hardware: Raspberry Pi 3 Model B (192.168.1.10)
#
# O que este script faz:
#   1. Instala o pacote samba
#   2. Cria o usuário do sistema "auxnet"
#   3. Cria a pasta compartilhada /home/auxnet/compartilhado
#   4. Define permissões corretas na pasta
#   5. Cria o usuário Samba "auxnet" com senha
#   6. Copia o smb.conf do repositório para /etc/samba/smb.conf
#   7. Valida a configuração com testparm
#   8. Habilita e inicia o serviço smbd
#
# Como usar (do diretório raiz do repositório):
#   sudo bash scripts/setup_samba.sh
#
# Requisitos:
#   - setup_rede.sh já executado (IP fixo configurado)
#   - Arquivo samba/smb.conf presente no repositório
# =============================================================================

set -euo pipefail

# ── Verificação de root ───────────────────────────────────────────────────────
if [[ "${EUID}" -ne 0 ]]; then
    echo "[ERRO] Execute como root: sudo bash scripts/setup_samba.sh"
    exit 1
fi

# ── Verificação do diretório de trabalho ─────────────────────────────────────
# O script precisa encontrar samba/smb.conf no repositório.
# Verifica se o arquivo existe antes de prosseguir para evitar
# uma configuração incompleta (Samba instalado sem smb.conf correto).
REPO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SMB_CONF_REPO="${REPO_DIR}/samba/smb.conf"

if [[ ! -f "${SMB_CONF_REPO}" ]]; then
    echo "[ERRO] Arquivo não encontrado: ${SMB_CONF_REPO}"
    echo "       Execute este script a partir do diretório raiz do repositório."
    exit 1
fi

echo "=== AuxNet — Configuração do Samba ==="
echo ""

# ── Variáveis de configuração ─────────────────────────────────────────────────
USUARIO_SISTEMA="auxnet"         # Usuário Unix que será dono dos arquivos
PASTA_COMPARTILHADA="/home/auxnet/compartilhado"  # Raiz do NAS
SMB_CONF_DESTINO="/etc/samba/smb.conf"

# ── Passo 1: Instalar o Samba ─────────────────────────────────────────────────
echo "[1/6] Instalando o pacote samba..."

# apt-get update garante que a lista de pacotes está atualizada antes de instalar.
# Em ambientes sem internet, isso falhará — execute após garantir conectividade.
apt-get update -qq  # -qq = modo silencioso (apenas erros)
apt-get install -y samba

echo "   OK — Samba instalado."

# ── Passo 2: Criar usuário do sistema ─────────────────────────────────────────
echo "[2/6] Criando usuário do sistema '${USUARIO_SISTEMA}'..."

# id verifica se o usuário já existe (exit code 0 = existe).
# Evita erro "user already exists" em re-execuções do script.
if id "${USUARIO_SISTEMA}" &>/dev/null; then
    echo "   [AVISO] Usuário '${USUARIO_SISTEMA}' já existe. Pulando criação."
else
    # useradd cria o usuário sem senha de login (--no-create-home seria outra
    # opção, mas queremos o home em /home/auxnet para a pasta compartilhada).
    # --shell /usr/sbin/nologin: impede login SSH com este usuário.
    #   Por que nologin? O usuário "auxnet" é uma conta de serviço — serve
    #   apenas para autenticação Samba e posse dos arquivos. Permitir login
    #   SSH seria uma superfície de ataque desnecessária.
    useradd \
        --create-home \
        --shell /usr/sbin/nologin \
        --comment "AuxNet NAS service account" \
        "${USUARIO_SISTEMA}"
    echo "   OK — Usuário '${USUARIO_SISTEMA}' criado (sem acesso SSH)."
fi

# ── Passo 3: Criar pasta compartilhada ────────────────────────────────────────
echo "[3/6] Criando pasta compartilhada em ${PASTA_COMPARTILHADA}..."

# mkdir -p: cria a pasta e todos os diretórios intermediários necessários.
# Não falha se a pasta já existir.
mkdir -p "${PASTA_COMPARTILHADA}"
echo "   OK — Pasta criada."

# ── Passo 4: Definir permissões ────────────────────────────────────────────────
echo "[4/6] Configurando permissões da pasta compartilhada..."

# Dono: auxnet:auxnet — o serviço Samba acessa os arquivos como este usuário.
# Chmod 750:
#   7 (dono auxnet): leitura + escrita + execução (navegar no diretório)
#   5 (grupo auxnet): leitura + execução (listar e ler arquivos) — hóspedes
#   0 (outros):      sem acesso — nem root via Samba, nem outros processos
#
# Por que 750 e não 755?
#   Com 755, qualquer usuário do sistema (incluindo www-data do Nginx) poderia
#   listar o conteúdo da pasta. Como o guia contém a senha do Wi-Fi, restringir
#   ao grupo "auxnet" é mais seguro — princípio do menor privilégio.
chown -R "${USUARIO_SISTEMA}:${USUARIO_SISTEMA}" "${PASTA_COMPARTILHADA}"
chmod 750 "${PASTA_COMPARTILHADA}"

echo "   OK — Permissões: dono=${USUARIO_SISTEMA}, chmod=750."

# ── Passo 5: Criar usuário Samba ──────────────────────────────────────────────
echo "[5/6] Criando usuário Samba '${USUARIO_SISTEMA}'..."
echo "      (você precisará definir a senha para acesso dos hóspedes)"
echo ""

# smbpasswd cria uma entrada no banco de dados de senhas do Samba (/etc/samba/passdb.tdb).
# -a: adiciona um novo usuário (sem -a, tentaria alterar senha de um existente).
#
# Por que usuário Samba separado do usuário do sistema?
#   O Samba mantém seu próprio banco de senhas (passdb), independente do
#   /etc/shadow do Linux. Isso permite ter senhas diferentes e gerenciar
#   acesso ao NAS sem afetar as contas do sistema operacional.
#   O usuário deve existir no sistema (criado no passo 2) para que o
#   Samba possa mapear as permissões de arquivo Unix corretamente.
smbpasswd -a "${USUARIO_SISTEMA}"

echo "   OK — Usuário Samba '${USUARIO_SISTEMA}' criado."

# ── Passo 6: Instalar smb.conf ────────────────────────────────────────────────
echo "[6/6] Instalando configuração do Samba..."

# Backup do smb.conf original antes de substituir.
# Se algo der errado, o backup permite restaurar o estado anterior.
if [[ -f "${SMB_CONF_DESTINO}" ]]; then
    cp "${SMB_CONF_DESTINO}" "${SMB_CONF_DESTINO}.bak.$(date +%Y%m%d_%H%M%S)"
    echo "   Backup do smb.conf original criado."
fi

cp "${SMB_CONF_REPO}" "${SMB_CONF_DESTINO}"
echo "   OK — smb.conf copiado para ${SMB_CONF_DESTINO}."

# Valida a sintaxe do smb.conf antes de reiniciar o serviço.
# testparm retorna exit code != 0 se houver erro de sintaxe.
# Com set -e, qualquer erro aqui interrompe o script.
echo ""
echo "Validando configuração com testparm..."
testparm --suppress-prompt 2>&1 | grep -E "(Loaded|WARNING|ERROR)" || true
echo "   OK — Configuração válida."

# Habilita smbd para iniciar automaticamente no boot.
# Sem isso, após um reboot do Raspberry Pi, o Samba não estaria disponível
# para os hóspedes até que alguém o iniciasse manualmente.
systemctl enable smbd
systemctl restart smbd

echo ""
echo "=== Samba configurado com sucesso! ==="
echo ""
echo "Verificações:"
echo "  Status do serviço:    systemctl status smbd"
echo "  Listar shares:        smbclient -L 192.168.1.10 -U ${USUARIO_SISTEMA}"
echo "  Testar acesso:        smbclient //192.168.1.10/compartilhado -U ${USUARIO_SISTEMA}"
echo ""
echo "Acesso dos clientes:"
echo "  Windows:  \\\\192.168.1.10\\compartilhado"
echo "  iOS:      smb://192.168.1.10"
echo "  macOS:    smb://192.168.1.10/compartilhado"
echo ""
echo "PRÓXIMO PASSO: execute scripts/setup_nginx.sh"
