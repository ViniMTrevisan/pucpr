public class FilaCircular {
    private int[] fila;
    private int frente;
    private int tras;
    private int tamanho;
    private int capacidade;

    public FilaCircular(int capacidade) {
        this.capacidade = capacidade;
        fila = new int[capacidade];
        frente = 0;
        tras = 0;
        tamanho = 0;
    }

    public void adicionar(int pedido) {
        if (tamanho == capacidade) {
            System.out.println("Fila cheia! Não é possível adicionar o pedido " + pedido);
        } else {
            fila[tras] = pedido;
            tras = (tras + 1) % capacidade;
            tamanho++;
            System.out.println("Pedido " + pedido + " adicionado.");
        }
    }

    public void atender() {
        if (tamanho == 0) {
            System.out.println("Não há pedidos para atender!");
        } else {
            int pedido = fila[frente];
            frente = (frente + 1) % capacidade;
            tamanho--;
            System.out.println("Atendendo pedido " + pedido);
        }
    }

    public void mostrar() {
        if (tamanho == 0) {
            System.out.println("Fila vazia.");
        } else {
            System.out.print("Pedidos na fila: ");
            for (int i = 0; i < tamanho; i++) {
                int index = (frente + i) % capacidade;
                System.out.print(fila[index] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        FilaCircular fila = new FilaCircular(3);

        fila.adicionar(101);
        fila.adicionar(102);
        fila.adicionar(103);
        fila.mostrar();

        fila.adicionar(104);

        fila.atender();
        fila.adicionar(104);
        fila.mostrar();

        fila.atender();
        fila.atender();
        fila.atender();
    }
}