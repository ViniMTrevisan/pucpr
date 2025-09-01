public class PilhaAtaques {
    private String[] pilha;
    private int topo;
    private int capacidade;

    public PilhaAtaques(int capacidade) {
        this.capacidade = capacidade;
        pilha = new String[capacidade];
        topo = -1;
    }
    public void empilhar(String ataque) {
        if (topo == capacidade - 1) {
            System.out.println("A pilha está cheia! Não é possível bloquear mais ataques.");
        } else {
            pilha[++topo] = ataque;
            System.out.println("Ataque \"" + ataque + "\" bloqueado e adicionado à pilha.");
        }
    }

    public void desempilhar() {
        if (topo == -1) {
            System.out.println("A pilha está vazia! Nenhum ataque para contra-atacar.");
        } else {
            String ataque = pilha[topo--];
            System.out.println("Capitão América contra-atacou o ataque \"" + ataque + "\".");
        }
    }

    public static void main(String[] args) {
        PilhaAtaques pilha = new PilhaAtaques(5);

        pilha.empilhar("Soco");
        pilha.empilhar("Chute");
        pilha.empilhar("Explosão de energia");

        pilha.desempilhar();

        pilha.desempilhar();
        pilha.desempilhar();
    }
}