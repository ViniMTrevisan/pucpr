import java.util.Scanner;
import java.util.Arrays;

public class InsertionSort {
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
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

        insertionSort(arr);

        System.out.println("Vetor depois: " + Arrays.toString(arr));

        sc.close();
    }
}
