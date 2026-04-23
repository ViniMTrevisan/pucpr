#!/usr/bin/env bash
# =============================================================================
# AuxNet — Setup do Tailscale (VPN mesh para acesso remoto)
# =============================================================================
#
# Projeto:  AuxNet (Equipe Píton — PUCPR CTIC 2026/S1)
# Hardware: Raspberry Pi 3 Model B (192.168.1.10)
#
# Por que Tailscale?
#   O proprietário precisa acessar o NAS e o servidor web remotamente.
#   Alternativas consideradas e descartadas:
#
#   a) Port forwarding no roteador:
#      Exige abrir portas no TP-Link e configurar o endereço WAN do hotspot
#      (que muda a cada reconexão). Exigiria DDNS dinâmico. Expõe os
#      serviços diretamente à internet pública, aumentando a superfície de
#      ataque.
#
#   b) VPN tradicional (OpenVPN, WireGuard manual):
#      Requer configuração manual de certificados, firewall e port forwarding.
#      Complexidade alta para um administrador não especializado em redes.
#
#   c) Tailscale (escolhido):
#      - Baseado em WireGuard (criptografia moderna, auditada).
#      - Instalação em um único comando.
#      - Sem port forwarding — usa relay DERP quando NAT traversal falha.
#      - Sem IP público necessário — funciona com hotspot de smartphone.
#      - Gratuito para até 100 dispositivos (plano pessoal).
#      - Funciona em Raspberry Pi OS (ARM), Windows 11 e iOS.
#
# O que este script faz:
#   1. Verifica conectividade com a internet
#   2. Instala o cliente Tailscale via script oficial
#   3. Habilita o serviço tailscaled
#   4. Instrui o usuário a autenticar manualmente (exige browser)
#
# Como usar:
#   sudo bash scripts/setup_tailscale.sh
#
# ATENÇÃO: requer conexão ativa à internet (hotspot do smartphone).
#          Execute APÓS setup_rede.sh, setup_samba.sh e setup_nginx.sh.
# =============================================================================

set -euo pipefail

# ── Verificação de root ───────────────────────────────────────────────────────
if [[ "${EUID}" -ne 0 ]]; then
    echo "[ERRO] Execute como root: sudo bash scripts/setup_tailscale.sh"
    exit 1
fi

echo "=== AuxNet — Instalação do Tailscale ==="
echo ""

# ── Passo 1: Verificar conectividade com a internet ───────────────────────────
echo "[1/3] Verificando conectividade com a internet..."

# Tenta conectar ao servidor de controle do Tailscale para verificar internet.
# curl --silent --max-time 5 --head: faz um HEAD request (sem baixar o corpo)
# com timeout de 5 segundos. Retorna exit code != 0 se falhar.
#
# Por que verificar antes de instalar?
#   O script de instalação do Tailscale baixa ~50MB de binários.
#   Sem internet, o curl falharia no meio da instalação, deixando o sistema
#   em estado inconsistente (repositório adicionado mas pacote não instalado).
if ! curl --silent --max-time 5 --head https://pkgs.tailscale.com > /dev/null 2>&1; then
    echo "[ERRO] Sem conectividade com a internet."
    echo ""
    echo "O Tailscale precisa de internet para:"
    echo "  - Baixar o pacote de instalação"
    echo "  - Autenticar o dispositivo no servidor de controle"
    echo ""
    echo "Soluções:"
    echo "  1. Verifique se o hotspot do smartphone está ativo e conectado ao TP-Link."
    echo "  2. Teste: curl https://pkgs.tailscale.com"
    echo "  3. Se o hotspot atingiu o limite de dados, use o hotspot de outro integrante."
    exit 1
fi

echo "   OK — Internet disponível."

# ── Passo 2: Instalar o Tailscale ─────────────────────────────────────────────
echo "[2/3] Instalando o Tailscale..."
echo ""

# Por que usar o script oficial (install.sh) em vez de apt-get manual?
#   O script oficial:
#   1. Detecta automaticamente a distribuição e arquitetura (ARM64 para RPi 3).
#   2. Adiciona o repositório APT com a chave GPG correta.
#   3. Instala o pacote tailscale.
#   4. Habilita e inicia o serviço tailscaled.
#   Fazê-lo manualmente exigiria 6-8 comandos adicionais com risco de erro.
#
# Segurança do pipe para sh:
#   Usar curl | sh é conveniente mas implica confiar no servidor do Tailscale.
#   Para uso em produção crítica, baixar o script, verificar o hash SHA256
#   e então executar seria mais seguro. Para o escopo acadêmico deste projeto,
#   o pipe direto é aceitável e seguro considerando a origem confiável.
#
# -fsSL: -f (falha em erro HTTP), -s (silencioso), -S (mostra erros),
#        -L (segue redirecionamentos).
curl -fsSL https://tailscale.com/install.sh | sh

echo ""
echo "   OK — Tailscale instalado."

# Garante que o serviço está habilitado para iniciar no boot.
# O script oficial já faz isso, mas verificar explicitamente é boa prática.
systemctl enable tailscaled
systemctl start tailscaled

# ── Passo 3: Instruções de autenticação ───────────────────────────────────────
echo "[3/3] Autenticação do dispositivo..."
echo ""

# Por que "tailscale up" não é executado automaticamente pelo script?
#   "tailscale up" abre um link de autenticação que precisa ser acessado
#   em um browser (redirecionamento para login.tailscale.com).
#   Em um terminal SSH ou sem interface gráfica, o link deve ser copiado
#   manualmente e aberto no Galaxy Book4 ou smartphone do proprietário.
#   Executar automaticamente interromperia o script esperando input do usuário,
#   quebrando o fluxo de automação.
echo "AÇÃO MANUAL NECESSÁRIA:"
echo ""
echo "Execute o seguinte comando para autenticar este Raspberry Pi:"
echo ""
echo "  sudo tailscale up"
echo ""
echo "O Tailscale exibirá uma URL como:"
echo "  https://login.tailscale.com/a/xxxxxxxxxxxxxxxx"
echo ""
echo "Abra esta URL no navegador do Galaxy Book4 ou do smartphone do proprietário."
echo "Faça login com a conta Tailscale do proprietário."
echo "O Raspberry Pi aparecerá automaticamente na lista de dispositivos."
echo ""
echo "VERIFICAÇÃO após autenticar:"
echo "  tailscale status                → deve mostrar o Raspberry Pi como 'online'"
echo "  tailscale ip                    → exibe o IP Tailscale do dispositivo (100.x.x.x)"
echo "  ping <IP-Tailscale>             → testar do Galaxy Book4 após instalar Tailscale lá"
echo ""
echo "=== Instalação do Tailscale concluída! ==="
echo ""
echo "PRÓXIMO PASSO: instale o Tailscale no Galaxy Book4 (Windows):"
echo "  Acesse https://tailscale.com/download/windows no Samsung Galaxy Book4"
echo "  Instale e faça login com a mesma conta do proprietário."
echo ""
echo "Após ambos os dispositivos estarem autenticados, teste o acesso remoto:"
echo "  1. Desconecte o Galaxy Book4 da rede AuxNet"
echo "  2. Conecte-o a outra rede (ex: dados móveis do smartphone)"
echo "  3. Acesse http://<IP-Tailscale-do-RaspberryPi> no navegador"
echo "  4. Acesse \\\\<IP-Tailscale-do-RaspberryPi>\\compartilhado no Explorer"
