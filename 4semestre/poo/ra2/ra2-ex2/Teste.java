public class Teste {
    public static void main(String[] args) {
        Veiculo veiculo = new Veiculo("Yamaha", 2020);
        Carro carro = new Carro("Toyota", 2022, 4);
        CarroEletrico carroEletrico = new CarroEletrico("Tesla", 2024, 4, 600);

        veiculo.exibirDados();
        System.out.println();
        carro.exibirDados();
        System.out.println();
        carroEletrico.exibirDados();
    }
}
