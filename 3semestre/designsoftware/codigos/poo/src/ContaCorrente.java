public class ContaCorrente extends ContaAbstrata {

    private double limChequeEspecial;

    public ContaCorrente(String numeroConta, double saldo, Cliente cliente) {
        super(numeroConta, saldo, cliente);
    }

    @Override
    public double obterLimite() {
        return getSaldo() + limChequeEspecial;
    }

    public double getLimChequeEspecial() {
        return limChequeEspecial;
    }

    public void setLimChequeEspecial(double limChequeEspecial) {
        this.limChequeEspecial = limChequeEspecial;
    }

    @Override
    public String toString() {
        String infoPai = super.toString();
        String infoFilho = String.format("%nLimite Cheque Especial: R$ %.2f", this.limChequeEspecial);

        return infoPai + infoFilho;
    }
}

