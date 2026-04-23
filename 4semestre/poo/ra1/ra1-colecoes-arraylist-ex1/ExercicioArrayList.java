import java.util.ArrayList;

public class ExercicioArrayList {
    public static void main(String[] args) {
        ArrayList<String> frutas = new ArrayList<>();

        frutas.add("Banana");
        frutas.add("Maca");
        frutas.add("Laranja");
        frutas.add("Uva");

        frutas.add(1, "P\u00EAssego");

        for (String fruta : frutas) {
            System.out.println(fruta);
        }

        frutas.clear();
        System.out.println("Quantidade de frutas apos remover tudo: " + frutas.size());
    }
}

