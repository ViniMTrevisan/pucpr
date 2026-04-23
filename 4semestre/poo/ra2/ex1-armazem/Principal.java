import java.time.LocalDate;
import java.util.ArrayList;

public class Principal {
    public static void main(String[] args) {
        ArrayList<Item> itens = new ArrayList<>();

        itens.add(new ProdutoEletronico("Smartphone", "EL100", 2200.00, 12));
        itens.add(new ProdutoPerecivel("Queijo", "PE100", 35.00, LocalDate.now().plusDays(5)));
        itens.add(new ProdutoInflamavel("Tinta Solvente", "IN100", 90.00, 3));
        itens.add(new ProdutoPerecivel("Biscoito", "PE101", 6.99, LocalDate.now().plusDays(30)));

        System.out.println("=== Estoque Multi-Produtos ===");
        for (Item item : itens) {
            System.out.println(item);
        }
    }
}
