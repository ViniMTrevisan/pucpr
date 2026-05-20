public class Designer extends Funcionario implements Calculavel {
    private double bonus;

    public Designer(String nome, double salarioBase, double bonus) {
        super(nome, salarioBase);
        this.bonus = bonus;
    }

    @Override
    public double calcularSalarioFinal() {
        return salarioBase + bonus;
    }

    @Override
    public void exibirInformacoes() {
        exibirDadosBasicos();
        System.out.println("Bonus: " + bonus);
        System.out.println("Salario final: " + calcularSalarioFinal());
    }
}
