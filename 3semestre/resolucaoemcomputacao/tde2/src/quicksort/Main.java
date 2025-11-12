package quicksort;

public class Main {

    public static void main(String[] args) {
        int[] vetor = {1993, 1845, 2008, 1514, 1291, 1507, 1822, 1553, 1974, 1402};

        System.out.print("Vetor original: ");
        exibirVetor(vetor);

        quickSort(vetor, 0, vetor.length - 1);

        System.out.print("\nVetor ordenado: ");
        exibirVetor(vetor);
    }

    public static void quickSort(int[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            System.out.print("\nVetor atual: [");
            for (int i = inicio; i <= fim; i++) {
                System.out.print(vetor[i]);
                if (i < fim) System.out.print(", ");
            }
            System.out.println("]");
            System.out.println("Pivô escolhido: " + vetor[inicio]);

            int posicaoPivo = particiona(vetor, inicio, fim);
            quickSort(vetor, inicio, posicaoPivo - 1);
            quickSort(vetor, posicaoPivo + 1, fim);
        }
    }

    public static int particiona(int[] vetor, int inicio, int fim) {
        int pivo = vetor[inicio];
        int esquerda = inicio + 1;
        int direita = fim;

        while (true) {
            while (esquerda <= direita && vetor[esquerda] <= pivo) {
                esquerda++;
            }
            while (esquerda <= direita && vetor[direita] >= pivo) {
                direita--;
            }
            if (esquerda > direita) {
                break;
            } else {
                // Troca os elementos fora do lugar
                int temp = vetor[esquerda];
                vetor[esquerda] = vetor[direita];
                vetor[direita] = temp;
            }
        }

        int temp = vetor[inicio];
        vetor[inicio] = vetor[direita];
        vetor[direita] = temp;

        return direita;
    }

    public static void exibirVetor(int[] vetor) {
        System.out.print("[");
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i]);
            if (i < vetor.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}