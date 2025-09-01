import java.util.ArrayDeque;
import java.util.Deque;

public class PilhaPortais {

    private Deque<String> portais;

    public PilhaPortais() {
        portais = new ArrayDeque<>();
    }

    public void abrirPortal(String nome) {
        portais.push(nome);
        System.out.println("Portal \"" + nome + "\" aberto.");
    }

    public void fecharPortal() {
        if (portais.isEmpty()) {
            System.out.println("Nenhum portal para fechar!");
        } else {
            String portal = portais.pop(); // remove do topo
            System.out.println("Portal \"" + portal + "\" fechado.");
        }
    }


    public static void main(String[] args) {
        PilhaPortais pilha = new PilhaPortais();

        pilha.abrirPortal("Portal de Nova York");
        pilha.abrirPortal("Portal de Curitiba");
        pilha.abrirPortal("Portal de São Paulo");

        pilha.fecharPortal();
        pilha.fecharPortal();
        pilha.fecharPortal();
    }
}