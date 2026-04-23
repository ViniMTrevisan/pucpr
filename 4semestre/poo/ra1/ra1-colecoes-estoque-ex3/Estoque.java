import java.util.ArrayList;

public class Estoque {
    private ArrayList<Produto> produtos;

    public Estoque() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void alterarPrecoProduto(String nomeProduto, double novoPreco) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
                produto.setPreco(novoPreco);
                System.out.println("Preco alterado com sucesso para: " + produto.getNome());
                return;
            }
        }
        System.out.println("Produto n\u00E3o encontrado");
    }

    public void listarProdutosEmEstoque() {
        if (produtos.isEmpty()) {
            System.out.println("Estoque vazio.");
            return;
        }

        for (Produto produto : produtos) {
            System.out.printf("Nome: %s | Preco: R$ %.2f%n", produto.getNome(), produto.getPreco());
        }
    }
}

