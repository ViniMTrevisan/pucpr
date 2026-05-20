import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        List<Forma> formas = new ArrayList<>();

        formas.add(new Retangulo(5, 4));
        formas.add(new Circulo(20));

        for (Forma forma : formas) {
            System.out.printf(
                "%s - área: %.2f%n",
                forma.getClass().getSimpleName(),
                forma.calcularArea()
            );
        }
    }
}
