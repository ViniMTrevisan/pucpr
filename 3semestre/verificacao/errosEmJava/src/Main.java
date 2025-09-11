
public class Main {
    public static void main(String[] args) {
        int[] meuArray = {-3, -2, 0, 1, 4};
        int resultado = oddOrPos(meuArray);
        System.out.println(resultado);
    }

    public static int oddOrPos(int[] x) {
        int count = 0;
        for (int i = 0;  i < x.length; i++) {
            if (x[i] > 0 || x[i] % 2 != 0) {
                count++;
            }
        }
        return count;
    }
}