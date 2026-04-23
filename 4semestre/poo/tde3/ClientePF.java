public class ClientePF extends Cliente {

    public String cpf;
    public double saldoDevedor;

    public void obterEmprestimo(double valor) {
        super.saldo += valor;
        this.saldoDevedor += valor;
    }
}
