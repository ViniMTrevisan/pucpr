package singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe Singleton para gerenciar as configurações da aplicação.
 * Esta implementação é thread-safe (segura em ambientes com múltiplas threads)
 * e utiliza a inicialização preguiçosa (lazy initialization).
 */
public class ConfiguracoesApp {

    // 1. A única instância da classe (volatile para garantir visibilidade entre threads)
    private static volatile ConfiguracoesApp instance;

    // Mapa para armazenar as configurações (ex: "database.url", "jdbc:...")
    private Map<String, String> propriedades = new HashMap<>();

    // 2. Construtor privado para impedir a criação de instâncias fora da classe
    private ConfiguracoesApp() {
        // Simula o carregamento de configurações de um arquivo ou banco de dados
        System.out.println("Inicializando e carregando configurações...");
        propriedades.put("app.nome", "Meu Sistema Incrível");
        propriedades.put("app.versao", "1.0.2");
        propriedades.put("database.url", "jdbc:mysql://localhost:3306/meubd");
        propriedades.put("api.key", "ChaveSuperSecreta12345");
    }

    /**
     * 3. Método público e estático para obter a instância única da classe.
     * A verificação dupla (Double-Checked Locking) garante a segurança em ambientes
     * concorrentes de forma eficiente.
     *
     * @return A única instância de ConfiguracoesApp.
     */
    public static ConfiguracoesApp getInstance() {
        if (instance == null) { // Primeira verificação (sem bloqueio)
            synchronized (ConfiguracoesApp.class) {
                if (instance == null) { // Segunda verificação (com bloqueio)
                    instance = new ConfiguracoesApp();
                }
            }
        }
        return instance;
    }

    /**
     * Método para obter uma propriedade de configuração específica.
     *
     * @param chave A chave da configuração.
     * @return O valor da configuração ou null se não for encontrada.
     */
    public String getPropriedade(String chave) {
        return propriedades.get(chave);
    }

}