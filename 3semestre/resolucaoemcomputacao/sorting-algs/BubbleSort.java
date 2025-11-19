import java.util.Scanner;
import java.util.Arrays;

public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Quantos elementos? ");
        int n;
        try {
            n = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Entrada inválida. Encerrando.");
            sc.close();
            return;
        }

        int[] arr = new int[n];
        System.out.println("Digite os " + n + " inteiros separados por espaço ou enter:");
        for (int i = 0; i < n; i++) {
            if (sc.hasNextInt()) {
                arr[i] = sc.nextInt();
            } else {
                System.out.println("Número insuficiente ou inválido. Encerrando.");
                sc.close();
                return;
            }
        }

        System.out.println("Vetor antes: " + Arrays.toString(arr));

        bubbleSort(arr);

        System.out.println("Vetor depois: " + Arrays.toString(arr));

        sc.close();
    }
}
