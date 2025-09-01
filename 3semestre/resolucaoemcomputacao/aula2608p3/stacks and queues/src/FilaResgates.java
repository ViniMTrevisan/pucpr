import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FilaResgates {
    private Queue<Integer> fila;

    public FilaResgates() {
        fila = new LinkedList<>();
    }

    public void adicionarPedido(int pedido) {
        fila.add(pedido);
        System.out.println("Pedido " + pedido + " adicionado à fila.");
    }

    public void atenderPedido() {
        if (fila.isEmpty()) {
            System.out.println("Não há pedidos na fila!");
        } else {
            int pedido = fila.poll();
            System.out.println("Atendendo pedido " + pedido);
        }
    }

    public void mostrarFila() {
        if (fila.isEmpty()) {
            System.out.println("Fila vazia.");
        } else {
            System.out.println("Pedidos na fila: " + fila);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FilaResgates fila = new FilaResgates();

        int opcao;
        do {
            System.out.println("\n--- Liga dos Heróis ---");
            System.out.println("1 - Inserir novo pedido");
            System.out.println("2 - Atender próximo pedido");
            System.out.println("3 - Mostrar fila");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Número do pedido: ");
                    int pedido = sc.nextInt();
                    fila.adicionarPedido(pedido);
                    break;
                case 2:
                    fila.atenderPedido();
                    break;
                case 3:
                    fila.mostrarFila();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        sc.close();
    }
}