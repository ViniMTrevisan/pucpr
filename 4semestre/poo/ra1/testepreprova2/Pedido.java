package testepreprova2;

public class Pedido {
    private Cliente cliente; 
    private Produto produto; 
    private int quantidade; 

    public Pedido(Cliente cliente, Produto produto, int quantidade) {
        this.cliente = cliente; 
        this.produto = produto; 
        this.quantidade = quantidade; 
    }

    public void exibirInformacoes(){ 
        System.out.println("Cliente: " + cliente.getNome() + " | Email: " + cliente.getEmail());
        System.out.println("Produto: " + produto.getNome() + " | Preco: " + produto.getPreco());
        System.out.println("Quantidade: " + quantidade);
    }
}
