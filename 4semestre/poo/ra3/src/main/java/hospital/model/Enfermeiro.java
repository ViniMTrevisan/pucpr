package hospital.model;

import java.time.LocalDate;

public class Enfermeiro extends Funcionario {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;

    private int id;
    private String coren;
    private String turno;
    private int nivel;

    public Enfermeiro(String nome, String endereco, LocalDate dataNascimento,
                      String matricula, double salario, double cargaHoraria,
                      String coren, String turno, int nivel) {
        super(nome, endereco, dataNascimento, matricula, salario, cargaHoraria);
        this.id = proximoId++;
        this.coren = coren;
        this.turno = turno;
        this.nivel = nivel;
    }

    public int getId() { return id; }

    public String getCoren() { return coren; }
    public void setCoren(String coren) { this.coren = coren; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public void administrarMedicamento(String medicamento) {
        System.out.println("Enfermeiro " + getNome() + " administrou: " + medicamento);
    }

    public static void setProximoId(int id) { proximoId = id; }

    @Override
    public String toString() {
        return id + " - " + getNome();
    }
}
