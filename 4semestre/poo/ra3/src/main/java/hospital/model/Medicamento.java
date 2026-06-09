package hospital.model;

import java.io.Serializable;

public class Medicamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigoAnvisa;
    private String nome;
    private int estoque;

    public Medicamento(String codigoAnvisa, String nome, int estoque) {
        this.codigoAnvisa = codigoAnvisa;
        this.nome = nome;
        this.estoque = estoque;
    }

    public boolean verificarEstoque() {
        return estoque > 0;
    }

    public void atualizarEstoque(int quantidade) {
        if (quantidade < 0 && Math.abs(quantidade) > estoque) {
            throw new IllegalArgumentException("Estoque insuficiente.");
        }
        this.estoque += quantidade;
    }

    public String getCodigoAnvisa() { return codigoAnvisa; }
    public void setCodigoAnvisa(String codigoAnvisa) { this.codigoAnvisa = codigoAnvisa; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    @Override
    public String toString() {
        return codigoAnvisa + " - " + nome;
    }
}
