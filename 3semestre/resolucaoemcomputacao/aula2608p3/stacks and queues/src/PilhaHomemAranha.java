class Andar {
    int numero;
    Andar proximo;

    public Andar(int numero) {
        this.numero = numero;
        this.proximo = null;
    }
}

public class PilhaHomemAranha {
    private Andar topo;

    public PilhaHomemAranha() {
        topo = null;
    }

    public void subir(int andar) {
        Andar novo = new Andar(andar);
        novo.proximo = topo;
        topo = novo;
        System.out.println("Homem-Aranha subiu para o andar " + andar);
    }

    public void descer() {
        if (topo == null) {
            System.out.println("Homem-Aranha já está no chão!");
        } else {
            System.out.println("Homem-Aranha desceu do andar " + topo.numero);
            topo = topo.proximo;
        }
    }

    public static void main(String[] args) {
        PilhaHomemAranha pilha = new PilhaHomemAranha();

        pilha.subir(1);
        pilha.subir(2);
        pilha.subir(3);

        pilha.descer();
        pilha.descer();
        pilha.descer();
    }
}