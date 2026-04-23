#!/usr/bin/env bash
# =============================================================================
# AuxNet — Setup do Nginx (servidor web institucional)
# =============================================================================
#
# Projeto:  AuxNet (Equipe Píton — PUCPR CTIC 2026/S1)
# Hardware: Raspberry Pi 3 Model B (192.168.1.10)
#
# O que este script faz:
#   1. Instala o pacote nginx
#   2. Cria o diretório /var/www/auxnet com permissões corretas
#   3. Copia index.html do repositório para o diretório do site
#   4. Instala a configuração do site (auxnet.conf)
#   5. Habilita o site e remove o site padrão do Nginx
#   6. Valida a configuração com nginx -t
#   7. Habilita e reinicia o serviço nginx
#
# Como usar (do diretório raiz do repositório):
#   sudo bash scripts/setup_nginx.sh
#
# Requisitos:
#   - setup_rede.sh já executado
#   - Arquivos html/index.html e nginx/auxnet.conf presentes no repositório
# =============================================================================

set -euo pipefail

# ── Verificação de root ───────────────────────────────────────────────────────
if [[ "${EUID}" -ne 0 ]]; then
    echo "[ERRO] Execute como root: sudo bash scripts/setup_nginx.sh"
    exit 1
fi

# ── Verificação dos arquivos do repositório ───────────────────────────────────
# Verificar todos os arquivos necessários antes de começar evita
# instalação parcial (Nginx instalado mas sem conteúdo ou config).
REPO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
HTML_REPO="${REPO_DIR}/html/index.html"
CONF_REPO="${REPO_DIR}/nginx/auxnet.conf"

ERRO=0
if [[ ! -f "${HTML_REPO}" ]]; then
    echo "[ERRO] Arquivo não encontrado: ${HTML_REPO}"
    ERRO=1
fi
if [[ ! -f "${CONF_REPO}" ]]; then
    echo "[ERRO] Arquivo não encontrado: ${CONF_REPO}"
    ERRO=1
fi
if [[ "${ERRO}" -eq 1 ]]; then
    echo "       Execute este script a partir do diretório raiz do repositório."
    exit 1
fi

echo "=== AuxNet — Configuração do Nginx ==="
echo ""

# ── Variáveis de configuração ─────────────────────────────────────────────────
WWW_DIR="/var/www/auxnet"                              # Raiz do site
CONF_DESTINO="/etc/nginx/sites-available/auxnet"      # Config do site
CONF_ENABLED="/etc/nginx/sites-enabled/auxnet"        # Symlink para habilitar
CONF_DEFAULT="/etc/nginx/sites-enabled/default"       # Site padrão a remover

# ── Passo 1: Instalar o Nginx ─────────────────────────────────────────────────
echo "[1/5] Instalando o pacote nginx..."

apt-get update -qq
# nginx-full vs nginx-light:
#   nginx-full inclui módulos extras (HTTP/2, SSL, realip, gzip, etc.).
#   nginx-light é mais enxuto mas pode faltar módulos para uso futuro.
#   nginx (meta-pacote) instala nginx-full — adequado para o projeto
#   e garante compatibilidade com qualquer diretiva da configuração.
apt-get install -y nginx

echo "   OK — Nginx instalado."

# ── Passo 2: Criar diretório do site ──────────────────────────────────────────
echo "[2/5] Criando diretório ${WWW_DIR}..."

mkdir -p "${WWW_DIR}"

# Permissões do diretório do site:
# Dono: www-data (usuário que o Nginx usa para servir arquivos).
# Grupo: www-data.
# Chmod 755: permite que o Nginx (www-data) leia e liste os arquivos.
#   O "5" (r-x) para "outros" permite que o processo Nginx acesse
#   o diretório mesmo quando iniciado como root e depois muda para www-data.
chown -R www-data:www-data "${WWW_DIR}"
chmod 755 "${WWW_DIR}"

echo "   OK — Diretório criado com permissões www-data:www-data 755."

# ── Passo 3: Copiar arquivos do site ──────────────────────────────────────────
echo "[3/5] Copiando arquivos do site..."

cp "${HTML_REPO}" "${WWW_DIR}/index.html"

# Ajustar permissões dos arquivos copiados.
# 644: leitura para www-data (dono) e outros processos;
#      escrita somente para www-data (ou root ao atualizar via Tailscale).
chown www-data:www-data "${WWW_DIR}/index.html"
chmod 644 "${WWW_DIR}/index.html"

echo "   OK — index.html copiado para ${WWW_DIR}."

# ── Passo 4: Instalar configuração do site ────────────────────────────────────
echo "[4/5] Instalando configuração do site..."

# Backup da configuração atual (se existir) antes de substituir.
if [[ -f "${CONF_DESTINO}" ]]; then
    cp "${CONF_DESTINO}" "${CONF_DESTINO}.bak.$(date +%Y%m%d_%H%M%S)"
    echo "   Backup da configuração anterior criado."
fi

cp "${CONF_REPO}" "${CONF_DESTINO}"
echo "   OK — auxnet.conf copiado para ${CONF_DESTINO}."

# Criar symlink em sites-enabled para habilitar o site.
# O Nginx carrega apenas os sites com symlink em sites-enabled.
# Este modelo (sites-available/sites-enabled) permite habilitar e
# desabilitar sites sem deletar a configuração.
if [[ ! -L "${CONF_ENABLED}" ]]; then
    ln -s "${CONF_DESTINO}" "${CONF_ENABLED}"
    echo "   OK — Site habilitado via symlink em sites-enabled."
else
    echo "   [INFO] Symlink já existe em sites-enabled."
fi

# Remover o site padrão do Nginx.
# O site "default" responde a todas as requisições sem server_name correspondente.
# Mantê-lo causaria conflito com o site "auxnet" em algumas configurações.
# Sem o default, o Nginx usa o primeiro server block disponível — que é o auxnet.
if [[ -f "${CONF_DEFAULT}" ]] || [[ -L "${CONF_DEFAULT}" ]]; then
    rm -f "${CONF_DEFAULT}"
    echo "   OK — Site padrão (default) removido de sites-enabled."
fi

# ── Passo 5: Validar e reiniciar o Nginx ──────────────────────────────────────
echo "[5/5] Validando configuração e reiniciando o Nginx..."

# nginx -t testa a sintaxe de todos os arquivos de configuração.
# Falha antes de reiniciar o serviço, evitando downtime por erro de sintaxe.
# Com set -e, qualquer falha aqui interrompe o script.
nginx -t

# Habilita o Nginx para iniciar automaticamente no boot.
systemctl enable nginx

# reload aplica a nova configuração sem interromper conexões existentes.
# restart reiniciaria o processo completamente — usar reload é mais seguro.
# Aqui usamos restart pois é a primeira instalação (não há conexões ativas).
systemctl restart nginx

echo "   OK — Nginx reiniciado com sucesso."

# ── Verificação final ─────────────────────────────────────────────────────────
echo ""
echo "=== Nginx configurado com sucesso! ==="
echo ""
echo "Verificações:"
echo "  Status:      systemctl status nginx"
echo "  Logs de acesso: tail -f /var/log/nginx/auxnet-access.log"
echo "  Logs de erro:   tail -f /var/log/nginx/auxnet-error.log"
echo ""
echo "Acesso ao site:"
echo "  Na rede local:    http://192.168.1.10"
echo "  Via mDNS (Apple): http://auxnet.local"
echo "  Via Tailscale:    http://<IP-Tailscale-do-RaspberryPi>"
echo ""
echo "PRÓXIMO PASSO: execute scripts/setup_tailscale.sh"
