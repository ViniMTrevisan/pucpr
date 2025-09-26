public interface IBancoGeral {

    public ContaAbstrata abrirConta(
            Cliente cliente, String numeroConta, double saldoInicial, String tipo
    );

    public boolean depositar(ContaAbstrata conta, double valor);

    public boolean sacar(ContaAbstrata conta, double valor);

}
