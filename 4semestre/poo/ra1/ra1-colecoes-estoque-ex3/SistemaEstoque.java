public class SistemaEstoque {
    public static void main(String[] args) {
        Estoque estoque = new Estoque();

        estoque.adicionarProduto(new Produto("Notebook", 4200.00));
        estoque.adicionarProduto(new Produto("Teclado", 150.00));
        estoque.adicionarProduto(new Produto("Celular", 3000.00));
        estoque.adicionarProduto(new Produto("Mouse", 90.00));
        estoque.adicionarProduto(new Produto("Monitor", 1200.00));
        estoque.adicionarProduto(new Produto("Webcam", 280.00));
        estoque.adicionarProduto(new Produto("Fone", 220.00));
        estoque.adicionarProduto(new Produto("Impressora", 700.00));
        estoque.adicionarProduto(new Produto("Cadeira", 950.00));
        estoque.adicionarProduto(new Produto("HD Externo", 450.00));

        System.out.println("=== Lista inicial de produtos ===");
        estoque.listarProdutosEmEstoque();

        System.out.println("\n=== Alterando preco do Celular para 5000.00 ===");
        estoque.alterarPrecoProduto("Celular", 5000.00);

        System.out.println("\n=== Lista apos alteracao ===");
        estoque.listarProdutosEmEstoque();

        System.out.println("\n=== Tentando alterar produto inexistente ===");
        estoque.alterarPrecoProduto("Tablet", 2000.00);
    }
}

