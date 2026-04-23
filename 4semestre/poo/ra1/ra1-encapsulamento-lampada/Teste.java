public class Teste {
    public static void main(String[] args) {
        Lampada lampada = new Lampada();

        System.out.println("Estado inicial: " + (lampada.estaLigada() ? "Ligada" : "Desligada"));

        lampada.ligar();
        System.out.println("Apos ligar: " + (lampada.estaLigada() ? "Ligada" : "Desligada"));

        lampada.desligar();
        System.out.println("Apos desligar: " + (lampada.estaLigada() ? "Ligada" : "Desligada"));
    }
}

