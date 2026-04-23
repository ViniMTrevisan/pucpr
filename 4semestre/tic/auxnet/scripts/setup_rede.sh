#!/usr/bin/env bash
# =============================================================================
# AuxNet — Setup da Rede (IP fixo, hostname, timezone, locale)
# =============================================================================
#
# Projeto:  AuxNet (Equipe Píton — PUCPR CTIC 2026/S1)
# Hardware: Raspberry Pi 3 Model B
# Executa:  Como root no Raspberry Pi, após a primeira inicialização
#
# O que este script faz:
#   1. Configura IP fixo 192.168.1.10 via dhcpcd.conf
#   2. Define o hostname como "auxnet"
#   3. Configura timezone para America/Sao_Paulo
#   4. Configura locale para pt_BR.UTF-8
#
# Como usar:
#   sudo bash setup_rede.sh
#
# ATENÇÃO: reinicialização necessária ao final para aplicar todas as mudanças.
# =============================================================================

# ── Configuração de segurança do bash ────────────────────────────────────────
# -e: encerra o script imediatamente se qualquer comando falhar.
#     Evita que erros silenciosos causem estado inconsistente no sistema.
# -u: trata variáveis não definidas como erro (evita bugs por typos).
# -o pipefail: propaga erros dentro de pipes (cmd1 | cmd2 falha se cmd1 falhar).
set -euo pipefail

# ── Verificação de root ───────────────────────────────────────────────────────
# Modificar /etc/dhcpcd.conf, /etc/hostname e instalar pacotes requer root.
# EUID=0 é o ID do usuário root. Verificar antes de começar evita falhas
# no meio da execução com estado parcialmente modificado.
if [[ "${EUID}" -ne 0 ]]; then
    echo "[ERRO] Este script deve ser executado como root."
    echo "       Use: sudo bash setup_rede.sh"
    exit 1
fi

echo "=== AuxNet — Configuração de Rede ==="
echo ""

# ── Variáveis de configuração ─────────────────────────────────────────────────
# Centralizar as configurações no topo facilita ajustes sem precisar
# procurar valores espalhados pelo script.

# Interface de rede cabeada do Raspberry Pi 3 Model B.
# "eth0" é o nome padrão no Raspberry Pi OS Lite.
# Em sistemas com systemd-networkd, pode ser "enp3s0" ou similar.
INTERFACE="eth0"

# IP fixo do servidor — fora da faixa DHCP (100–200) para nunca colidir
# com endereços atribuídos automaticamente pelo TP-Link.
IP_FIXO="192.168.1.10"

# Gateway: IP do roteador principal (TP-Link TL-R470T+).
GATEWAY="192.168.1.1"

# DNS: usando o gateway como DNS forwarder.
# O TP-Link repassa queries DNS para os servidores da operadora.
DNS="192.168.1.1"

# Hostname da máquina — aparece no prompt e em logs do sistema.
HOSTNAME_NOVO="auxnet"

# Timezone do Paraná/Brasil.
TIMEZONE="America/Sao_Paulo"

# Locale em português do Brasil com codificação UTF-8.
LOCALE="pt_BR.UTF-8"

# ── Passo 1: Configurar IP fixo via dhcpcd.conf ───────────────────────────────
echo "[1/4] Configurando IP fixo ${IP_FIXO} na interface ${INTERFACE}..."

# Por que dhcpcd.conf e não systemd-networkd?
#   O Raspberry Pi OS Lite usa dhcpcd como gerenciador de DHCP por padrão.
#   Alterar para systemd-networkd exigiria desabilitar o dhcpcd e configurar
#   arquivos .network — passos adicionais desnecessários para o projeto.
#   dhcpcd.conf é o caminho de menor resistência no Raspberry Pi OS.

# Verifica se a configuração estática já existe para evitar duplicatas.
# grep -q retorna 0 (encontrou) ou 1 (não encontrou), sem imprimir nada.
if grep -q "interface ${INTERFACE}" /etc/dhcpcd.conf; then
    echo "   [AVISO] Configuração de IP fixo já existe em /etc/dhcpcd.conf."
    echo "           Pulando para evitar duplicata. Verifique o arquivo manualmente."
else
    # Appenda (>>) a configuração no final do arquivo dhcpcd.conf.
    # Usar >> em vez de > evita apagar configurações existentes do sistema.
    cat >> /etc/dhcpcd.conf <<EOF

# --- AuxNet: IP fixo do Raspberry Pi (adicionado por setup_rede.sh) ---
interface ${INTERFACE}
static ip_address=${IP_FIXO}/24
static routers=${GATEWAY}
static domain_name_servers=${DNS}
EOF
    echo "   OK — IP fixo ${IP_FIXO}/24 configurado em /etc/dhcpcd.conf."
fi

# ── Passo 2: Configurar hostname ──────────────────────────────────────────────
echo "[2/4] Configurando hostname para '${HOSTNAME_NOVO}'..."

# /etc/hostname contém somente o nome da máquina, sem domínio.
echo "${HOSTNAME_NOVO}" > /etc/hostname

# /etc/hosts deve associar o novo hostname a 127.0.1.1 (loopback local).
# Sem isso, comandos como sudo mostram aviso "unable to resolve host auxnet".
# sed -i edita o arquivo in-place. O padrão substitui qualquer linha que
# começa com "127.0.1.1" pelo novo valor.
if grep -q "127.0.1.1" /etc/hosts; then
    sed -i "s/127\.0\.1\.1.*/127.0.1.1\t${HOSTNAME_NOVO}/" /etc/hosts
else
    echo "127.0.1.1	${HOSTNAME_NOVO}" >> /etc/hosts
fi

echo "   OK — Hostname configurado para '${HOSTNAME_NOVO}'."

# ── Passo 3: Configurar timezone ──────────────────────────────────────────────
echo "[3/4] Configurando timezone para ${TIMEZONE}..."

# timedatectl é a ferramenta padrão do systemd para timezone.
# Altera o link simbólico /etc/localtime e atualiza /etc/timezone.
# Necessário para que logs do sistema (syslog, nginx, samba) mostrem
# horário correto de Brasília, facilitando correlação de eventos.
timedatectl set-timezone "${TIMEZONE}"

echo "   OK — Timezone configurado para ${TIMEZONE}."

# ── Passo 4: Configurar locale ────────────────────────────────────────────────
echo "[4/4] Configurando locale para ${LOCALE}..."

# locale-gen gera os dados binários de locale a partir das definições
# em /usr/share/i18n/locales/. Sem isso, pt_BR.UTF-8 não está disponível
# mesmo que seja configurado.
#
# Por que pt_BR.UTF-8?
#   - UTF-8 suporta caracteres especiais do português (ã, é, ç, etc.)
#   - Necessário para que nomes de arquivos em português no NAS sejam
#     exibidos corretamente no terminal do Raspberry Pi.
locale-gen "${LOCALE}"

# update-locale atualiza /etc/default/locale com as variáveis de ambiente.
# LANG: locale padrão para mensagens do sistema, formatos de data/número.
# LC_ALL: sobrescreve todas as variáveis LC_* individualmente.
update-locale LANG="${LOCALE}" LC_ALL="${LOCALE}"

echo "   OK — Locale configurado para ${LOCALE}."

# ── Resumo final ──────────────────────────────────────────────────────────────
echo ""
echo "=== Configuração concluída! ==="
echo ""
echo "Resumo:"
echo "  IP fixo:   ${IP_FIXO}/24 (interface ${INTERFACE})"
echo "  Gateway:   ${GATEWAY}"
echo "  Hostname:  ${HOSTNAME_NOVO}"
echo "  Timezone:  ${TIMEZONE}"
echo "  Locale:    ${LOCALE}"
echo ""
echo "PRÓXIMO PASSO: reinicialize o Raspberry Pi para aplicar todas as mudanças."
echo "  sudo reboot"
echo ""
echo "Após reiniciar, verifique:"
echo "  ip addr show ${INTERFACE}   → deve mostrar ${IP_FIXO}/24"
echo "  hostname                   → deve mostrar ${HOSTNAME_NOVO}"
echo "  date                       → deve mostrar horário de Brasília"
