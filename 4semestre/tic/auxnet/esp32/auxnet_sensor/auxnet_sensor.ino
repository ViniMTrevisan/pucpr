/**
 * =============================================================================
 * AuxNet — Firmware ESP32 · Monitoramento Ambiental
 * =============================================================================
 *
 * Projeto:    AuxNet (Equipe Píton — PUCPR CTIC 2026/S1)
 * Hardware:   ESP32 DevKit V1 + sensor DHT11
 * Função:     Servidor HTTP autônomo que serve leituras de temperatura e
 *             umidade em tempo real via página HTML na rede local (LAN).
 *
 * Por que o ESP32 é autônomo e não integrado ao Raspberry Pi?
 *   Integrar o ESP32 ao Raspberry Pi exigiria um broker de mensagens (MQTT),
 *   scripts de polling e dependências entre serviços. Qualquer falha no
 *   Raspberry Pi derrubaria o monitoramento ambiental junto. Ao manter o
 *   ESP32 como servidor web independente, cada componente falha de forma
 *   isolada — o NAS e o servidor web do Raspberry Pi continuam funcionando
 *   mesmo que o ESP32 esteja offline, e vice-versa.
 *
 * Bibliotecas necessárias (instalar via Arduino IDE > Library Manager):
 *   - "DHT sensor library" by Adafruit (versão >= 1.4.4)
 *   - "Adafruit Unified Sensor" by Adafruit (dependência do DHT)
 *   Suporte à placa ESP32: instalar via Boards Manager >
 *   URL: https://dl.espressif.com/dl/package_esp32_index.json
 *
 * Placa selecionada no Arduino IDE: "ESP32 Dev Module"
 * =============================================================================
 */

// ---------------------------------------------------------------------------
// Bibliotecas
// ---------------------------------------------------------------------------

// WiFi.h: biblioteca nativa do ESP32 para conexão Wi-Fi.
// Não usar WiFi.h do Arduino padrão — é incompatível com o ESP32.
#include <WiFi.h>

// WebServer.h: servidor HTTP embutido no SDK do ESP32.
// Escolhido em vez de ESPAsyncWebServer por não exigir bibliotecas externas
// adicionais e por ser suficiente para servir páginas estáticas simples.
#include <WebServer.h>

// DHT.h: biblioteca Adafruit para o sensor DHT11/DHT22.
// Abstrai o protocolo single-wire proprietário do sensor, que requer
// temporização precisa de microssegundos — inviável de implementar manualmente.
#include <DHT.h>

// ---------------------------------------------------------------------------
// Configurações — alterar aqui antes de gravar o firmware
// ---------------------------------------------------------------------------

// SSID e senha da rede Wi-Fi da casa de temporada.
// Hardcoded porque o ESP32 não tem interface de configuração em runtime.
// Se a senha mudar, o firmware precisa ser regravado.
const char* WIFI_SSID     = "AuxNet";
const char* WIFI_SENHA    = "PREENCHER_AQUI";  // ← alterar antes de gravar

// IP fixo do ESP32 na rede local.
// Fixo por design: os dispositivos dos hóspedes precisam de um endereço
// previsível para acessar a página de monitoramento. DHCP não é usado
// para servidores — um servidor com IP dinâmico exigiria descoberta de
// serviço (mDNS/Bonjour), aumentando a complexidade para os clientes.
IPAddress IP_FIXO(192, 168, 1, 40);

// Gateway e máscara de sub-rede — devem corresponder ao TP-Link TL-R470T+.
IPAddress GATEWAY(192, 168, 1, 1);
IPAddress MASCARA(255, 255, 255, 0);

// DNS: usando o próprio gateway como DNS primário.
// O TP-Link TL-R470T+ faz forward de queries DNS para a operadora.
IPAddress DNS(192, 168, 1, 1);

// Pino GPIO ao qual o sinal de dados do DHT11 está conectado.
// GPIO 4 foi escolhido por ser um pino de uso geral sem funções
// especiais reservadas (não é SPI, I2C, UART nem boot-strapping).
// Alterar se o hardware for remontado com o sensor em outro pino.
#define PINO_DHT 4

// Tipo de sensor. DHT11 é o sensor de baixo custo usado neste projeto.
// Substituir por DHT22 se o sensor for trocado — o DHT22 tem maior
// precisão (±0.5°C) mas é mais caro. A biblioteca suporta ambos.
#define TIPO_DHT DHT11

// Porta HTTP do servidor web.
// Porta 80 é o padrão HTTP: os hóspedes acessam pelo IP sem precisar
// digitar a porta (http://192.168.1.40 em vez de http://192.168.1.40:8080).
// Se outra porta fosse usada, seria necessário orientar os hóspedes
// a digitá-la manualmente, aumentando a fricção.
#define PORTA_HTTP 80

// Intervalo mínimo entre leituras do DHT11 (em milissegundos).
// O DHT11 precisa de pelo menos 1000ms entre leituras consecutivas
// conforme datasheet. Usar 2000ms dá margem de segurança.
// Leituras mais frequentes retornam NaN (valor inválido).
#define INTERVALO_LEITURA_MS 2000

// ---------------------------------------------------------------------------
// Objetos globais
// ---------------------------------------------------------------------------

// Instância do servidor HTTP na porta definida acima.
WebServer servidor(PORTA_HTTP);

// Instância do sensor DHT no pino e tipo configurados acima.
DHT dht(PINO_DHT, TIPO_DHT);

// Variáveis para armazenar a última leitura válida do sensor.
// Inicializadas com NAN para indicar que ainda não houve leitura.
float ultimaTemperatura = NAN;
float ultimaUmidade     = NAN;

// Timestamp da última leitura (em ms desde o boot).
// Usado para controlar o intervalo mínimo entre leituras.
unsigned long ultimaLeituraMs = 0;

// ---------------------------------------------------------------------------
// Protótipos de funções
// ---------------------------------------------------------------------------
void conectarWiFi();
void lerSensor();
String gerarPaginaHTML();
String gerarRespostaJSON();
void rotaRaiz();
void rotaDados();
void rotaNaoEncontrado();

// ---------------------------------------------------------------------------
// setup() — executado uma única vez ao ligar o ESP32
// ---------------------------------------------------------------------------
void setup() {
  // Monitor serial para debug durante desenvolvimento e configuração.
  // Baud rate 115200 é o padrão do ESP32; configurar o mesmo no Serial Monitor.
  Serial.begin(115200);
  Serial.println("\n=== AuxNet — Sensor Ambiental ===");

  // Inicializa o sensor DHT.
  // Deve ser chamado antes de qualquer leitura.
  dht.begin();

  // Conecta ao Wi-Fi com IP fixo configurado acima.
  conectarWiFi();

  // Registra as rotas do servidor HTTP.
  // Cada rota associa um caminho URL a uma função handler.
  servidor.on("/",       rotaRaiz);    // Página HTML para hóspedes
  servidor.on("/dados",  rotaDados);   // JSON para debug/integração futura
  servidor.onNotFound(rotaNaoEncontrado); // 404 para rotas desconhecidas

  // Inicia o servidor HTTP.
  // A partir deste ponto, o ESP32 começa a aceitar conexões na porta 80.
  servidor.begin();
  Serial.println("Servidor HTTP iniciado em http://" + WiFi.localIP().toString());
}

// ---------------------------------------------------------------------------
// loop() — executado continuamente após setup()
// ---------------------------------------------------------------------------
void loop() {
  // Processa requisições HTTP recebidas.
  // handleClient() verifica se há uma nova conexão, lê o request HTTP,
  // chama o handler correspondente e envia a resposta.
  // Deve ser chamado no loop principal — não usar tasks ou threads
  // separados para evitar concorrência de memória no heap do ESP32.
  servidor.handleClient();

  // Atualiza a leitura do sensor respeitando o intervalo mínimo.
  // A leitura não é feita dentro do handler HTTP para não bloquear
  // o servidor durante a espera do DHT11.
  unsigned long agora = millis();
  if (agora - ultimaLeituraMs >= INTERVALO_LEITURA_MS) {
    lerSensor();
    ultimaLeituraMs = agora;
  }
}

// ---------------------------------------------------------------------------
// conectarWiFi() — configura IP fixo e conecta à rede AuxNet
// ---------------------------------------------------------------------------
void conectarWiFi() {
  // Configura IP fixo ANTES de chamar WiFi.begin().
  // Se WiFi.config() for chamado depois de begin(), o ESP32 pode
  // ignorar a configuração e usar DHCP mesmo assim.
  if (!WiFi.config(IP_FIXO, GATEWAY, MASCARA, DNS)) {
    Serial.println("[ERRO] Falha ao configurar IP fixo. Verifique os endereços.");
  }

  Serial.print("Conectando à rede '" + String(WIFI_SSID) + "'");
  WiFi.begin(WIFI_SSID, WIFI_SENHA);

  // Aguarda conexão com timeout de 30 tentativas (aprox. 15 segundos).
  // Se não conectar, imprime erro e continua — o servidor não será acessível,
  // mas o código não trava em loop infinito bloqueando o serial monitor.
  int tentativas = 0;
  while (WiFi.status() != WL_CONNECTED && tentativas < 30) {
    delay(500);
    Serial.print(".");
    tentativas++;
  }

  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("\nConectado!");
    Serial.println("IP: " + WiFi.localIP().toString());
    // Verificação: o IP obtido deve ser 192.168.1.40 conforme configurado.
    // Se for diferente, o roteador pode ter ignorado o WiFi.config().
  } else {
    Serial.println("\n[ERRO] Não foi possível conectar ao Wi-Fi.");
    Serial.println("Verifique SSID, senha e alcance do sinal.");
    // O ESP32 continuará tentando em background automaticamente.
    // handleClient() ainda funciona — o servidor estará pronto
    // assim que a conexão for estabelecida.
  }
}

// ---------------------------------------------------------------------------
// lerSensor() — lê temperatura e umidade do DHT11
// ---------------------------------------------------------------------------
void lerSensor() {
  float temperatura = dht.readTemperature(); // °C
  float umidade     = dht.readHumidity();    // % UR

  // isnan() verifica se a leitura é inválida (Not a Number).
  // O DHT11 retorna NaN quando:
  //   - o pino está mal conectado
  //   - o intervalo entre leituras é menor que 1000ms
  //   - há interferência elétrica no barramento single-wire
  //   - o sensor está com defeito
  if (isnan(temperatura) || isnan(umidade)) {
    Serial.println("[AVISO] Leitura inválida do DHT11. Mantendo último valor válido.");
    // Mantém os últimos valores válidos em vez de exibir erro na página.
    // Se nunca houve leitura válida, ultimaTemperatura/ultimaUmidade
    // permanecem NAN e a página exibe "Aguardando leitura...".
    return;
  }

  ultimaTemperatura = temperatura;
  ultimaUmidade     = umidade;

  Serial.printf("Leitura OK — Temperatura: %.1f°C | Umidade: %.1f%%\n",
                ultimaTemperatura, ultimaUmidade);
}

// ---------------------------------------------------------------------------
// gerarPaginaHTML() — monta o HTML servido na rota "/"
// ---------------------------------------------------------------------------
String gerarPaginaHTML() {
  // O HTML é gerado como String em vez de ser um arquivo estático
  // porque o ESP32 não tem sistema de arquivos montado por padrão
  // (SPIFFS/LittleFS requereria particionamento adicional do flash).
  // Para uma única página simples, gerar em memória é mais direto.
  //
  // Por que meta-refresh em vez de JavaScript fetch()?
  //   - meta-refresh funciona em QUALQUER browser, incluindo browsers
  //     básicos de TVs, consoles e dispositivos antigos dos hóspedes.
  //   - JS fetch() exigiria uma segunda rota /dados no servidor e
  //     código JS mais complexo no firmware (mais memória flash usada).
  //   - A recarga completa a cada 5s é aceitável para monitoramento
  //     ambiental — temperatura e umidade mudam lentamente.
  //
  // Por que CSS inline e não um arquivo .css separado?
  //   - Mesma razão: sem sistema de arquivos no flash.
  //   - Uma única requisição HTTP traz tudo — página e estilo juntos.
  //   - Reduz latência para os clientes (sem segundo round-trip para o CSS).
  //
  // Por que não usar CDN (Bootstrap, Tailwind)?
  //   - A rede AuxNet não tem acesso à internet garantido para os hóspedes.
  //   - Dependências externas quebrariam a página se a WAN cair.

  String valorTemp = isnan(ultimaTemperatura)
    ? "Aguardando leitura..."
    : String(ultimaTemperatura, 1) + " °C";

  String valorUmid = isnan(ultimaUmidade)
    ? "Aguardando leitura..."
    : String(ultimaUmidade, 1) + " %";

  String html = "<!DOCTYPE html>"
    "<html lang='pt-BR'>"
    "<head>"
      "<meta charset='UTF-8'>"
      // Viewport para dispositivos móveis dos hóspedes (smartphones e tablets).
      "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
      // Atualiza a página inteira a cada 5 segundos.
      // content='5' = intervalo em segundos. Mínimo razoável para o DHT11
      // (que atualiza a cada 2s), com margem para o tempo de resposta HTTP.
      "<meta http-equiv='refresh' content='5'>"
      "<title>AuxNet — Sensor Ambiental</title>"
      "<style>"
        // Reset e tipografia básica.
        "* { box-sizing: border-box; margin: 0; padding: 0; }"
        "body { font-family: Arial, sans-serif; background: #f0f4f8; "
               "display: flex; justify-content: center; align-items: center; "
               "min-height: 100vh; padding: 20px; }"
        ".card { background: white; border-radius: 12px; padding: 32px; "
                "max-width: 400px; width: 100%; text-align: center; "
                "box-shadow: 0 4px 16px rgba(0,0,0,0.1); }"
        "h1 { font-size: 1.4rem; color: #2d3748; margin-bottom: 8px; }"
        ".subtitulo { font-size: 0.85rem; color: #718096; margin-bottom: 32px; }"
        ".leitura { margin: 16px 0; padding: 20px; border-radius: 8px; "
                   "background: #ebf8ff; }"
        ".leitura.umidade { background: #f0fff4; }"
        ".icone { font-size: 2rem; margin-bottom: 8px; }"
        ".label { font-size: 0.8rem; color: #4a5568; text-transform: uppercase; "
                  "letter-spacing: 0.05em; margin-bottom: 4px; }"
        ".valor { font-size: 2rem; font-weight: bold; color: #2d3748; }"
        ".rodape { margin-top: 24px; font-size: 0.75rem; color: #a0aec0; }"
      "</style>"
    "</head>"
    "<body>"
      "<div class='card'>"
        "<h1>Sensor Ambiental</h1>"
        "<p class='subtitulo'>Casa AuxNet &mdash; Atualiza a cada 5 segundos</p>"

        "<div class='leitura'>"
          "<div class='icone'>&#127777;</div>"  // ícone de termômetro (🌡)
          "<div class='label'>Temperatura</div>"
          "<div class='valor'>" + valorTemp + "</div>"
        "</div>"

        "<div class='leitura umidade'>"
          "<div class='icone'>&#128167;</div>"  // ícone de gota (💧)
          "<div class='label'>Umidade Relativa</div>"
          "<div class='valor'>" + valorUmid + "</div>"
        "</div>"

        "<p class='rodape'>"
          "Sensor: DHT11 &nbsp;|&nbsp; IP: 192.168.1.40 &nbsp;|&nbsp; "
          "<a href='/dados'>JSON</a>"
        "</p>"
      "</div>"
    "</body>"
    "</html>";

  return html;
}

// ---------------------------------------------------------------------------
// gerarRespostaJSON() — monta o JSON servido na rota "/dados"
// ---------------------------------------------------------------------------
String gerarRespostaJSON() {
  // Rota auxiliar útil para:
  //   - Debug durante configuração (curl http://192.168.1.40/dados)
  //   - Integração futura com outros sistemas (ex: dashboard no Raspberry Pi)
  // Não é usada pelos hóspedes diretamente.
  String temp = isnan(ultimaTemperatura) ? "null" : String(ultimaTemperatura, 1);
  String umid = isnan(ultimaUmidade)     ? "null" : String(ultimaUmidade, 1);

  return "{\"temperatura\":" + temp + ",\"umidade\":" + umid +
         ",\"unidade_temp\":\"C\",\"unidade_umid\":\"%\","
         "\"sensor\":\"DHT11\",\"ip\":\"192.168.1.40\"}";
}

// ---------------------------------------------------------------------------
// Handlers das rotas HTTP
// ---------------------------------------------------------------------------

// rotaRaiz() — responde a GET /
void rotaRaiz() {
  // Content-Type: text/html informa ao browser como interpretar o corpo.
  // charset=UTF-8 garante que caracteres especiais (°, ã, é) sejam exibidos.
  servidor.send(200, "text/html; charset=UTF-8", gerarPaginaHTML());
}

// rotaDados() — responde a GET /dados
void rotaDados() {
  // Content-Type: application/json informa que o corpo é JSON válido.
  // Sem charset explícito — JSON é sempre UTF-8 por especificação (RFC 8259).
  servidor.send(200, "application/json", gerarRespostaJSON());
}

// rotaNaoEncontrado() — responde a qualquer rota não registrada
void rotaNaoEncontrado() {
  servidor.send(404, "text/plain; charset=UTF-8",
                "404 — Pagina nao encontrada. Acesse: http://192.168.1.40/");
}
