import java.util.Scanner;
import java.util.Arrays;

public class QuickSort {

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Lomuto partition (pivot = last element)
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        int tmp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = tmp;
        return i + 1;
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

        if (n > 0) {
            quickSort(arr, 0, n - 1);
        }

        System.out.println("Vetor depois: " + Arrays.toString(arr));

        sc.close();
    }
}
