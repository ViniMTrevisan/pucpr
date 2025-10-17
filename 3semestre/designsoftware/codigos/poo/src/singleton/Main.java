package singleton;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Teste do Padrão Singleton ---");
        System.out.println("Tentando obter a instância pela primeira vez.");

        ConfiguracoesApp config1 = ConfiguracoesApp.getInstance();

        // Obtendo a instância novamente em outra parte do "programa"
        System.out.println("\nTentando obter a instância pela segunda vez.");
        ConfiguracoesApp config2 = ConfiguracoesApp.getInstance();

        // Teste: Verificar se as duas referências apontam para o mesmo objeto
        System.out.println("\n--- Verificando a unicidade da instância ---");
        if (config1 == config2) {
            System.out.println("Sucesso! As duas variáveis (config1 e config2) apontam para a MESMA instância.");
            System.out.println("Hash Code de config1: " + config1.hashCode());
            System.out.println("Hash Code de config2: " + config2.hashCode());
        } else {
            System.out.println("Falha! As variáveis apontam para instâncias DIFERENTES.");
        }

        // Usando a instância para acessar as configurações
        System.out.println("\n--- Acessando as configurações globais ---");
        System.out.println("Nome do App: " + config1.getPropriedade("app.nome"));
        System.out.println("Versão do App: " + config1.getPropriedade("app.versao"));
        System.out.println("URL do Banco de Dados: " + config2.getPropriedade("database.url"));
        System.out.println("Chave da API: " + config2.getPropriedade("api.key"));
    }
}