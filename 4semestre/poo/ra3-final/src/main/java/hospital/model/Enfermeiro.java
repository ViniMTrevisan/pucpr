package hospital.model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Enfermeiro extends Funcionario {
    private static final long serialVersionUID = 1L;
    private static final AtomicInteger proximoId = new AtomicInteger(1);

    private final int id;
    private String coren;
    private String turno;
    private int nivel;

    public Enfermeiro(String nome, String endereco, LocalDate dataNascimento,
                      String matricula, double salario, double cargaHoraria,
                      String coren, String turno, int nivel) {
        super(nome, endereco, dataNascimento, matricula, salario, cargaHoraria);
        this.id = proximoId.getAndIncrement();
        this.coren = coren;
        this.turno = turno;
        this.nivel = nivel;
    }

    public int getId() { return id; }

    public String getCoren() { return coren; }
    public void setCoren(String coren) { 
        if (coren == null) throw new NullPointerException
        ("coren não pode ser nulo"); 
        this.coren = coren; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { 
        if (turno == null) throw new NullPointerException
        ("turno não pode ser nulo"); 
        this.turno = turno; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { 
        if (nivel < 0) throw new IllegalArgumentException
        ("nivel inválido"); 
        this.nivel = nivel; }

    public void administrarMedicamento(String medicamento) {
        System.out.println("Enfermeiro " + getNome() + " administrou: " + medicamento);
    }

    public static void setProximoId(int id) { 
        if (id <= 0) throw new IllegalArgumentException("id deve ser maior que zero");
        proximoId.set(id);
    }

    @Override
    public String toString() {
        return id + " - " + getNome();
    }
}
