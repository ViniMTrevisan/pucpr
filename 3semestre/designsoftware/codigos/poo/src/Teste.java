public class Teste {
    public static void main(String[] args) {
        Cliente vini = new Cliente("Vinicius", "123.456.789-12");
        Cliente ana = new  Cliente("Ana", "123.789.456-00");

        IBancoGeral banco = new BancoItau();
        System.out.println("Abrindo contas...");
        ContaAbstrata poupancaVini = banco.abrirConta
                (vini, "1234", 100, "poupanca");
        ContaAbstrata contaCorrenteAna = banco.abrirConta
                (ana, "1234", 400, "conta corrente");
        System.out.println("Contas abertas!");

        if (poupancaVini instanceof Poupanca poupanca) {
            System.out.println("Uma conta poupança! Atribuindo limite...");
            poupanca.obterLimite();

            System.out.println("Operações: ");
            poupanca.depositar(300);
            System.out.println("Novo depósito na conta de Vini! Valor atual: " + poupanca.getSaldo());

            poupancaVini.sacar(150);
            System.out.println("Sacaram da sua conta, Vini! Valor atual: " + poupanca.getSaldo());
        }

        if  (contaCorrenteAna instanceof ContaCorrente contaCorrente) {
            System.out.println("Uma conta corrente! Atribuindo limite...");
            contaCorrente.setLimChequeEspecial(300);
            contaCorrente.obterLimite();

            System.out.println("Operações: ");
            contaCorrente.depositar(150);
            System.out.println("Novo depósito na conta de Ana! Valor atual: " + contaCorrente.getSaldo());

            contaCorrente.sacar(400);
            System.out.println("Sacaram da sua conta, Ana. Valor atual: " + contaCorrente.getSaldo());
        }

    }
}
