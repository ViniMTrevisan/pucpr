import java.util.List;

public class livromagico {
    private static final List<String> LIVRO = List.of(
        "Farinha + ovo + leite = bolo",
        "Ser ou não ser, eis a questão.",
        "Só sei que nada sei."
    );

    public static void lerPagina(int i) {
        if (i < 0 || i >= LIVRO.size()) {
            System.out.println("Página inexistente.");
            return;
        }
        System.out.println("Lendo página " + i + ": " + LIVRO.get(i));
    }

    public static String copiarPagina(int i) {
        if (i < 0 || i >= LIVRO.size()) {
            return "Página inexistente.";
        }
        return new String(LIVRO.get(i));
    }

    public static void main(String[] args) {
        lerPagina(0);
        lerPagina(1);

        String minhaCopia = copiarPagina(2);
        System.out.println("Cópia original: " + minhaCopia);

        String copiaEditada = minhaCopia.replace("não", "sim");
        System.out.println("Cópia editada: " + copiaEditada);

    }
}