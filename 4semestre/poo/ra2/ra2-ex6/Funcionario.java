public abstract class Funcionario {
    protected String nome;
    protected double salarioBase;

    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    public void exibirDadosBasicos() {
        System.out.println("Nome: " + nome);
        System.out.println("Salario base: " + salarioBase);
    }

    public abstract void exibirInformacoes();
}
