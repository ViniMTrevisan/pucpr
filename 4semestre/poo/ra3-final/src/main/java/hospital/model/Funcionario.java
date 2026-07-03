package hospital.model;

import java.time.LocalDate;

public abstract class Funcionario extends Pessoa {
    private static final long serialVersionUID = 1L;

    private String matricula;
    private double salario;
    private double cargaHoraria;

    public Funcionario(String nome, String endereco, LocalDate dataNascimento,
                       String matricula, double salario, double cargaHoraria) {
        super(nome, endereco, dataNascimento);
        this.matricula = matricula;
        this.salario = salario;
        this.cargaHoraria = cargaHoraria;
    }

    public double calcularSalario() {
        return salario + (cargaHoraria * 10.0);
    }

    public String getDados() {
        return "Matrícula: " + matricula + " | Salário: R$" + String.format("%.2f", salario);
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { 
        if (matricula == null) throw new NullPointerException
        ("matricula não pode ser nula"); 
        this.matricula = matricula; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { 
        if (salario < 0) throw new IllegalArgumentException
        ("salario não pode ser negativo"); 
        this.salario = salario; }

    public double getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(double cargaHoraria) { if (cargaHoraria < 0) throw new IllegalArgumentException
        ("cargaHoraria não pode ser negativa"); 
        this.cargaHoraria = cargaHoraria; }
}
