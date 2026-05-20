public abstract class Funcionario {
    protected String nome;

    public Funcionario(String nome) {
        this.nome = nome;
    }

    public abstract double calcularPagamento();

    public void exibirDados() {
        System.out.printf("Nome: %s | Pagamento: R$ %.2f%n", nome, calcularPagamento());
    }
}
