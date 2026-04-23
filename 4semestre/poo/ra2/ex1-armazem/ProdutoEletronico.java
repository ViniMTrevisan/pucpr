public class ProdutoEletronico extends Item {
    private int garantia;
    private static final double TAXA_IMPORTACAO = 0.10;

    public ProdutoEletronico(String nome, String codigo, double precoBase, int garantia) {
        super(nome, codigo, precoBase);
        this.garantia = garantia;
    }

    @Override
    public double calcularPrecoFinal() {
        return getPrecoBase() * (1 + TAXA_IMPORTACAO);
    }

    @Override
    public String getDescricaoEspecifica() {
        return "Eletronico (garantia: " + garantia + " meses)";
    }
}

