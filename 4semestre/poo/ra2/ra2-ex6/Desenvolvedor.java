public class Desenvolvedor extends Funcionario implements Calculavel {
    private int horasExtras;

    public Desenvolvedor(String nome, double salarioBase, int horasExtras) {
        super(nome, salarioBase);
        this.horasExtras = horasExtras;
    }

    @Override
    public double calcularSalarioFinal() {
        return salarioBase + (horasExtras * 20);
    }

    @Override
    public void exibirInformacoes() {
        exibirDadosBasicos();
        System.out.println("Horas extras: " + horasExtras);
        System.out.println("Salario final: " + calcularSalarioFinal());
    }
}
