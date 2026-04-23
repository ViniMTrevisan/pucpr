public class Aluno {
    private String ra;
    private String nome;
    private String endereco;
    private double rendaFamiliar;
    private String status;

    public Aluno(String ra, String nome, String endereco, double rendaFamiliar) {
        this.ra = ra;
        this.nome = nome;
        this.endereco = endereco;
        this.rendaFamiliar = rendaFamiliar;
        this.status = "N\u00E3o matriculado";
    }

    public String getRa() {
        return ra;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getRendaFamiliar() {
        return rendaFamiliar;
    }

    public void setRendaFamiliar(double rendaFamiliar) {
        this.rendaFamiliar = rendaFamiliar;
    }

    public String getStatus() {
        return status;
    }

    public void matricular() {
        this.status = "Matriculado";
    }
}

