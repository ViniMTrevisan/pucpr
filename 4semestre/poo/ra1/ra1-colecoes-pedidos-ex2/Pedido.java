import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Cliente cliente;
    private List<ProdutoPedido> produtos;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.produtos = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ProdutoPedido> getProdutos() {
        return produtos;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        produtos.add(new ProdutoPedido(produto, quantidade));
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ProdutoPedido produtoPedido : produtos) {
            total += produtoPedido.calcularSubtotal();
        }
        return total;
    }

    public String gerarResumoProduto(int indice) {
        if (indice < 0 || indice >= produtos.size()) {
            return "Produto do pedido nao encontrado.";
        }

        ProdutoPedido produtoPedido = produtos.get(indice);
        return produtoPedido.gerarResumo();
    }

    public String gerarResumo() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("Cliente: ")
                .append(cliente.getNome())
                .append(" - ")
                .append(cliente.getEmail())
                .append("\n");

        for (ProdutoPedido produtoPedido : produtos) {
            resumo.append(produtoPedido.gerarResumo()).append("\n");
        }

        resumo.append("Valor total: R$ ").append(String.format("%.2f", calcularTotal()));
        return resumo.toString();
    }
}
