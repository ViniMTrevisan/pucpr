class Herois {
    String nome;
    Herois proximo;

    public Herois(String nome) {
        this.nome = nome;
        this.proximo = null;
    }
}

public class FilaEncadeada {
    private Herois frente;
    private Herois tras;

    public FilaEncadeada() {
        frente = null;
        tras = null;
    }

    public void adicionar(String nome) {
        Herois novo = new Herois(nome);
        if (tras == null) {
            frente = novo;
            tras = novo;
        } else {
            tras.proximo = novo;
            tras = novo;
        }
        System.out.println(nome + " entrou na fila.");
    }

    public void atender() {
        if (frente == null) {
            System.out.println("Não há heróis esperando.");
        } else {
            System.out.println("Superman está atendendo " + frente.nome);
            frente = frente.proximo;
            if (frente == null) {
                tras = null;
            }
        }
    }

    public void mostrar() {
        if (frente == null) {
            System.out.println("Fila vazia.");
        } else {
            System.out.print("Heróis na fila: ");
            Herois atual = frente;
            while (atual != null) {
                System.out.print(atual.nome + " ");
                atual = atual.proximo;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        FilaEncadeada fila = new FilaEncadeada();

        fila.adicionar("Batman");
        fila.adicionar("Flash");
        fila.adicionar("Mulher-Maravilha");
        fila.mostrar();

        fila.atender();
        fila.mostrar();

        fila.atender();
        fila.atender();
    }
}