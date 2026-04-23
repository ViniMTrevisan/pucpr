# AuxNet — CLAUDE.md

## Projeto

**Nome:** AuxNet
**Equipe:** Equipe Píton
**Disciplina:** Clínica de Tecnologia da Informação e Comunicação (CTIC) — 5º período · 2026/S1
**Instituição:** PUCPR — Bacharelado em Engenharia de Software
**Orientador:** Prof. Fábio Garcez Bettio

**Integrantes:**
| Nome | RA |
|---|---|
| Amanda da Silva Freire | 40101883 |
| Fabio Augusto Gortz | 40072852 |
| Guilherme Gonçalves Marafon | 40058657 |
| Guilherme Reis Carvalho | 40093122 |
| Valentina Lago Raad | 40058322 |
| Vinicius Meier Trevisan | 40100664 |

## Cenário

Casa de temporada (modelo Airbnb) gerenciada remotamente pelo proprietário. A infraestrutura resolve três problemas concretos:
1. Compartilhar guia da casa com hóspedes (senhas, regras, instruções) sem presença física
2. Monitorar condições ambientais do imóvel
3. Permitir acesso seguro do proprietário ao servidor de qualquer lugar, sem custo recorrente

**Perfis de usuário:**
- **Proprietário** — administrador, nível técnico avançado, acesso remoto via Tailscale
- **Hóspedes** — usuários finais sem conhecimento técnico, até 4 simultâneos, até 8 dispositivos na rede

## Inventário de Hardware

| Tipo | Equipamento | IP | Estado |
|---|---|---|---|
| Servidor | Raspberry Pi 3 Model B | 192.168.1.10 (fixo) | Funcional |
| Desktop | Samsung Galaxy Book4 (Windows 11 Home 23H2) | DHCP | Funcional |
| IoT | ESP32 DevKit V1 + DHT11 | 192.168.1.40 (fixo) | Funcional |
| Mobile | Apple iPhone 17 Pro (iOS 18) | 192.168.1.30 | Funcional |
| Roteador principal | TP-Link TL-R470T+ | 192.168.1.1 | Funcional |
| Access Point | D-Link DI-524 (modo AP) | 192.168.1.2 | Funcional |
| Cabo | Ethernet Cat5e 2m (RJ45) | — | A adquirir |

**WAN:** hotspot de smartphone (4G)

## Rede

| Campo | Valor |
|---|---|
| Subnet | 192.168.1.0/24 |
| Gateway | 192.168.1.1 (TP-Link TL-R470T+) |
| DHCP | 192.168.1.100–192.168.1.200 |
| SSID | AuxNet |
| Banda | 2.4 GHz (802.11g) |
| Segurança | WPA2 |
| Canal | 6 |

O Raspberry Pi e o ESP32 usam IPs estáticos fora da faixa DHCP. O D-Link DI-524 opera em modo ponto de acesso (sem DHCP próprio, sem roteamento).

## Serviços

### Samba 4.17 — NAS (porta 445)
- Hospedado no Raspberry Pi (192.168.1.10)
- Compartilha `/home/auxnet/compartilhado` com o guia da casa
- Autenticação obrigatória com usuário e senha (`smbpasswd`)
- Acessível pelo Galaxy Book4 via `\\192.168.1.10\compartilhado` e pelo iPhone via app Arquivos
- Acesso restrito à rede local (sem exposição à internet)

### Nginx 1.22 — Servidor Web (porta 80)
- Hospedado no Raspberry Pi (192.168.1.10)
- Serve página institucional da casa de temporada
- Config em `/etc/nginx/sites-available/auxnet` com link em `sites-enabled`
- Criticidade secundária — falha não impede NAS nem ESP32

### Tailscale — VPN Mesh (WireGuard)
- Instalado no Raspberry Pi e no Galaxy Book4 (e opcionalmente iPhone)
- Permite acesso remoto do proprietário ao NAS e servidor web sem port forwarding
- Túnel criptografado ponto a ponto
- Autenticação via painel tailscale.com

### ESP32 — Servidor HTTP Embarcado (porta 80)
- IP fixo: 192.168.1.40
- Firmware em C/Arduino com biblioteca `WebServer.h` e `DHT sensor library`
- Lê temperatura (0–50°C ±2°C) e umidade (20–90% ±5%) via DHT11
- Serve página HTML com leituras em tempo real diretamente na rede local
- Componente **autônomo e independente** — nenhuma integração com Raspberry Pi
- Gravado via Arduino IDE no Galaxy Book4 (placa: "ESP32 Dev Module")

## Sistema Operacional

| Dispositivo | SO | Justificativa |
|---|---|---|
| Raspberry Pi 3 Model B | Raspberry Pi OS Lite — Debian 12 Bookworm 64-bit, kernel 6.1 | Mantido pela Raspberry Pi Foundation, <200MB RAM em idle, sem GUI, repositórios estáveis |
| Samsung Galaxy Book4 | Windows 11 Home 23H2 | Já instalado, SMB nativo no Explorer, suporte oficial ao Tailscale |

**Virtualização:** nenhuma. Samba e Nginx rodam nativamente no OS — 1GB de RAM do Raspberry Pi não comporta hypervisor.

## Decisões Técnicas

- **Samba vs OpenMediaVault:** OpenMediaVault descartado por substituir o OS base, inviabilizando coexistência com Nginx no hardware disponível.
- **Nginx vs Apache:** Apache descartado por modelo de threads com maior consumo de RAM. Nginx consome <5MB em idle.
- **ESP32 autônomo vs integrado ao Raspberry Pi:** Integração descartada — eliminaria broker de mensagens, scripts de polling e dependências desnecessárias. ESP32 serve sua própria página HTML diretamente.
- **Tailscale vs port forwarding / DDNS:** Tailscale não requer configuração no roteador, instala com um único script e usa WireGuard como base criptográfica.
- **Ubuntu Server 22.04 vs Raspberry Pi OS Lite:** Ubuntu descartado por consumir ~500MB de RAM em idle vs <200MB do Raspberry Pi OS Lite.

## Limitações Conhecidas

- Raspberry Pi 3 Model B: Ethernet 100Mbps compartilhada com barramento USB — possível gargalo em uso intensivo (aceitável para o escopo)
- D-Link DI-524: Wi-Fi 802.11g (54Mbps, 2.4GHz apenas) — suficiente para transferência de texto e navegação leve
- Acesso externo depende da disponibilidade do hotspot de smartphone como WAN

## Plano de Implementação (Ordem de Etapas)

1. Aquisição de materiais (Fabio Gortz)
2. Gravação do Raspberry Pi OS Lite no microSD (Vinicius)
3. Primeira inicialização e configuração básica do Raspberry Pi (Vinicius)
4. Configuração do TP-Link TL-R470T+ como gateway (Amanda)
5. Configuração do D-Link DI-524 em modo AP (Amanda)
6. IP fixo no Raspberry Pi via `/etc/dhcpcd.conf` (Amanda)
7. `apt update && apt upgrade` + instalação de `samba` e `nginx` (Valentina)
8. Criação da pasta e configuração do Samba com autenticação (Valentina)
9. Criação dos arquivos do guia da casa no NAS (Guilherme Marafon)
10. Configuração e publicação da página no Nginx (Guilherme Reis)
11. Setup Arduino IDE + bibliotecas ESP32 no Galaxy Book4 (Guilherme Reis)
12. Desenvolvimento e gravação do firmware no ESP32 (Guilherme Reis)
13. Testes de integração da rede local com evidências (Guilherme Marafon)
14. Instalação e autenticação do Tailscale no Raspberry Pi (Valentina)
15. Instalação e autenticação do Tailscale no Galaxy Book4 (Fabio Gortz)
16. Teste final de acesso remoto via Tailscale fora da rede local (Amanda)

## Principais Riscos

| Risco | Impacto | Probabilidade | Contingência |
|---|---|---|---|
| microSD corrompido na gravação | Alto | Baixa | Manter cartão reserva; verificar hash SHA256 da imagem |
| Incompatibilidade Wi-Fi 802.11g com dispositivos modernos | Médio | Média | Substituir D-Link ou usar hotspot como AP alternativo |
| RAM do Raspberry Pi esgotada com Samba + Nginx simultâneos | Alto | Baixa | Monitorar via `free -h`; reduzir conexões máximas no smb.conf |
| Falha na configuração do Samba | Alto | Média | Recomeçar com smb.conf mínimo funcional documentado |
| ESP32 não conecta ao Wi-Fi após gravação do firmware | Médio | Média | Regravar firmware com credenciais corrigidas |
| Hotspot indisponível durante autenticação do Tailscale | Alto | Baixa | Usar hotspot de outro integrante como WAN temporária |
| Limite de dados do hotspot atingido | Médio | Média | Baixar pacotes previamente; usar hotspot alternativo |

## Arquivos de Configuração Relevantes

- `/etc/samba/smb.conf` — configuração do compartilhamento Samba
- `/etc/dhcpcd.conf` — IP fixo do Raspberry Pi (192.168.1.10)
- `/etc/nginx/sites-available/auxnet` — config do site institucional
- `/home/auxnet/compartilhado/` — pasta do NAS com guia da casa

## Critério de Serviço Crítico

O **Samba (NAS)** é o serviço de maior criticidade. Sem ele, hóspedes não conseguem a senha do Wi-Fi nem as instruções do imóvel no check-in — equivale a deixar o hóspede sem orientação alguma na chegada, com o proprietário ausente fisicamente.
