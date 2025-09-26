public class BancoItau implements IBancoGeral{

    @Override
    public ContaAbstrata abrirConta(
            Cliente cliente, String numeroConta, double saldoInicial, String tipo
    ) {

        if ("Poupanca".equalsIgnoreCase(tipo)) {
            return new Poupanca(numeroConta, saldoInicial, cliente);
        } else {
            return new ContaCorrente(numeroConta, saldoInicial, cliente);
        }
    }

    @Override
    public boolean depositar(ContaAbstrata conta, double valor) {

        if (conta.getNumeroConta() != null && valor > 0) {
            conta.depositar(valor);
            return true;
        }

        return false;
    }

    @Override
    public boolean sacar(ContaAbstrata conta, double valor) {

        if (conta.getNumeroConta() != null && valor <= conta.obterLimite()) {
            conta.sacar(valor);
            return true;
        }
        return false;
    }



}
