import java.util.ArrayList;
import java.util.Locale;

public class Estoque {
    private final ArrayList<Produto> produtos;

    public Estoque() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void cadastrarProduto(String nome, double preco) {
        Produto produto = new Produto(nome, preco);
        adicionarProduto(produto);
    }

    public void alterarPrecoProdutoPorNome(String nomeProduto, double novoPreco) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
                produto.setPreco(novoPreco);
                System.out.println("Preço do produto \"" + produto.getNome() + "\" alterado com sucesso.");
                return;
            }
        }
        System.out.println("Produto não encontrado");
    }

    public void listarProdutosEmEstoque() {
        if (produtos.isEmpty()) {
            System.out.println("Estoque vazio.");
            return;
        }

        for (Produto produto : produtos) {
            String precoFormatado = String.format(Locale.forLanguageTag("pt-BR"), "%.2f", produto.getPreco());
            System.out.println("Nome: " + produto.getNome() + " | Preço: R$ " + precoFormatado);
        }
    }
}
