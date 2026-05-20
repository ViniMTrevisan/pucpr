package testepreprova2;

public class Main {
    public static void main(String[] args) {
        Cliente cli1 = new Cliente("Vinicius Trevisan", "vinimtrevisan@gmail.com"); 
        Cliente cli2 = new Cliente("Ana Julia", "anajgunha@gmail.com"); 

        Produto p1 = new Produto("Computador", 3555);
        Produto p2 = new Produto("Celular", 2000);

        Pedido ped1 = new Pedido(cli1, p1, 1);
        Pedido ped2 = new Pedido(cli2, p2, 2); 
    
        ped1.exibirInformacoes();
        ped2.exibirInformacoes();
    }
}
