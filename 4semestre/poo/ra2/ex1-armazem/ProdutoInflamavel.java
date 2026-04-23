public class ProdutoInflamavel extends Item {
    private int grauPericulosidade;
    private static final double ACRESCIMO_SEGURO = 0.15;

    public ProdutoInflamavel(String nome, String codigo, double precoBase, int grauPericulosidade) {
        super(nome, codigo, precoBase);
        this.grauPericulosidade = grauPericulosidade;
    }

    @Override
    public double calcularPrecoFinal() {
        return getPrecoBase() * (1 + ACRESCIMO_SEGURO);
    }

    @Override
    public String getDescricaoEspecifica() {
        return "Inflamavel (grau de periculosidade: " + grauPericulosidade + ")";
    }
}

