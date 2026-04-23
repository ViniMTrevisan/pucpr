public class Cliente {

    public String agencia;
    public String contaCorrente;
    public double saldo;

    public void sacarContaCorrente(double valor) {
        this.saldo -= valor;
    }

    public void depositarContaCorrente(double valor) {
        this.saldo += valor;
    }
}
