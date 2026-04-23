import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProdutoPerecivel extends Item {
    private LocalDate dataValidade;

    public ProdutoPerecivel(String nome, String codigo, double precoBase, LocalDate dataValidade) {
        super(nome, codigo, precoBase);
        this.dataValidade = dataValidade;
    }

    @Override
    public double calcularPrecoFinal() {
        long diasParaVencer = ChronoUnit.DAYS.between(LocalDate.now(), dataValidade);
        if (diasParaVencer < 0) {
            return getPrecoBase() * 0.50;
        }
        if (diasParaVencer <= 7) {
            return getPrecoBase() * 0.80;
        }
        return getPrecoBase();
    }

    @Override
    public String getDescricaoEspecifica() {
        return "Perecivel (validade: " + dataValidade + ")";
    }
}

