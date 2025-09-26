public abstract class ContaAbstrata {
    private String numeroConta;
    private double saldo;
    private Cliente cliente;

    public ContaAbstrata(String numeroConta, double saldo, Cliente cliente) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public abstract double obterLimite();

    public void depositar(double valor) {
        this.saldo += valor;
    }

    public void sacar(double valor) {
        this.saldo -= valor;

    }

    @Override
    public String toString() {
        return "ContaAbstrata{" +
                "numeroConta='" + getNumeroConta() + '\'' +
                ", saldo=" + getSaldo() +
                ", cliente=" + getCliente() +
                '}';
    }
}
