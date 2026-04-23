public class SistemaEstoque {
    public static void main(String[] args) {
        Estoque estoque = new Estoque();

        estoque.cadastrarProduto("Notebook", 4500.00);
        estoque.cadastrarProduto("Mouse", 120.00);
        estoque.cadastrarProduto("Celular", 3000.00);
        estoque.cadastrarProduto("Teclado", 180.00);
        estoque.cadastrarProduto("Monitor", 950.00);
        estoque.cadastrarProduto("Impressora", 700.00);
        estoque.cadastrarProduto("Webcam", 230.00);
        estoque.cadastrarProduto("Headset", 320.00);
        estoque.cadastrarProduto("Cadeira Gamer", 1100.00);
        estoque.cadastrarProduto("HD Externo", 400.00);

        System.out.println("=== Produtos cadastrados ===");
        estoque.listarProdutosEmEstoque();

        System.out.println("\n=== Alterando preço do produto Celular para 5000,00 ===");
        estoque.alterarPrecoProdutoPorNome("Celular", 5000.00);

        System.out.println("\n=== Produtos após alteração ===");
        estoque.listarProdutosEmEstoque();

        System.out.println("\n=== Tentando alterar produto inexistente ===");
        estoque.alterarPrecoProdutoPorNome("Console", 2500.00);
    }
}
