public class ProgramaPrincipal {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Ana Souza", "ana@email.com");
        Cliente cliente2 = new Cliente("Bruno Lima", "bruno@email.com");

        Produto produto1 = new Produto("Teclado", 120.00);
        Produto produto2 = new Produto("Mouse", 80.00);
        Produto produto3 = new Produto("Monitor", 900.00);
        Produto produto4 = new Produto("Headset", 250.00);
        Produto produto5 = new Produto("Webcam", 300.00);
        Produto produto6 = new Produto("Mousepad", 35.00);
        Produto produto7 = new Produto("Cadeira", 1300.00);
        Produto produto8 = new Produto("Notebook", 4200.00);

        Pedido pedido1 = new Pedido(cliente1);
        pedido1.adicionarProduto(produto1, 1);
        pedido1.adicionarProduto(produto2, 2);
        pedido1.adicionarProduto(produto3, 1);
        pedido1.adicionarProduto(produto4, 1);
        pedido1.adicionarProduto(produto5, 3);

        Pedido pedido2 = new Pedido(cliente2);
        pedido2.adicionarProduto(produto1, 2);
        pedido2.adicionarProduto(produto2, 1);
        pedido2.adicionarProduto(produto3, 2);
        pedido2.adicionarProduto(produto4, 1);
        pedido2.adicionarProduto(produto5, 1);
        pedido2.adicionarProduto(produto6, 4);
        pedido2.adicionarProduto(produto7, 1);
        pedido2.adicionarProduto(produto8, 1);

        System.out.println("Resumo do pedido 1:");
        System.out.println(pedido1.gerarResumo());
        System.out.println("Resumo de um produto do pedido 1:");
        System.out.println(pedido1.gerarResumoProduto(2));
        System.out.println("---------------------------");

        System.out.println("Resumo do pedido 2:");
        System.out.println(pedido2.gerarResumo());
        System.out.println("Resumo de um produto do pedido 2:");
        System.out.println(pedido2.gerarResumoProduto(6));
    }
}
