public class Main {
    public static void main(String[] args) {
        ContaBancaria conta1 = new ContaBancaria("001", 1000.0);
        ContaBancaria conta2 = new ContaBancaria("002", 500.0);

        System.out.println("=== TESTE CONTA 1 ===");
        System.out.println("Conta: " + conta1.getNumeroConta());
        System.out.println("Saldo antes: R$ " + conta1.getSaldo());
        conta1.depositar(200.0);
        conta1.sacar(150.0);
        System.out.println("Saldo depois: R$ " + conta1.getSaldo());

        System.out.println();

        System.out.println("=== TESTE CONTA 2 ===");
        System.out.println("Conta: " + conta2.getNumeroConta());
        System.out.println("Saldo antes: R$ " + conta2.getSaldo());
        conta2.depositar(300.0);
        conta2.sacar(100.0);
        System.out.println("Saldo depois: R$ " + conta2.getSaldo());
    }
}
