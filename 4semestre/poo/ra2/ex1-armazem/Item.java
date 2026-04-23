public abstract class Item {
    private String nome;
    private String codigo;
    private double precoBase;

    public Item(String nome, String codigo, double precoBase) {
        this.nome = nome;
        this.codigo = codigo;
        this.precoBase = precoBase;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public abstract double calcularPrecoFinal();

    public abstract String getDescricaoEspecifica();

    @Override
    public String toString() {
        return String.format(
            "%s | Nome: %s | Codigo: %s | Preco base: R$ %.2f | Preco final: R$ %.2f",
            getDescricaoEspecifica(), nome, codigo, precoBase, calcularPrecoFinal()
        );
    }
}

