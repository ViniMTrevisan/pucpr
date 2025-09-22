import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Teste Flood Fill ===");
        
        Ponto pontoInicial = new Ponto(25, 25);
        Color novaCor = Color.GREEN;
        
        System.out.println("Teste 1: Fila");
        Fila fila = new Fila();
        FloodFillerMelhorado floodFillerBFS = new FloodFillerMelhorado();
        floodFillerBFS.preencher("input.png", "out/resultado_fila.png", pontoInicial, novaCor, fila);
        
        System.out.println("Teste 2: Pilha");
        Pilha pilha = new Pilha();
        FloodFillerMelhorado floodFillerDFS = new FloodFillerMelhorado();
        floodFillerDFS.preencher("input.png", "out/resultado_pilha.png", pontoInicial, novaCor, pilha);
    }
}