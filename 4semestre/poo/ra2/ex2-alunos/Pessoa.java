public class Pessoa {
    private String nome;
    private String endereco;
    private String numeroCelular;

    public Pessoa(String nome, String endereco, String numeroCelular) {
        this.nome = nome;
        this.endereco = endereco;
        this.numeroCelular = numeroCelular;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public void mostrarInformacoes() {
        System.out.println("Nome: " + getNome());
        System.out.println("Endereco: " + getEndereco());
        System.out.println("Numero de celular: " + getNumeroCelular());
    }
}
