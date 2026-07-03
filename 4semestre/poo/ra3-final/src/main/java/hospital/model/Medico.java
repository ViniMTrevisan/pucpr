package hospital.model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Medico extends Funcionario {
    private static final long serialVersionUID = 1L;
    private static final AtomicInteger proximoId = new AtomicInteger(1);

    private final int id;
    private String crm;
    private String especialidade;
    private boolean plantao;

    public Medico(String nome, String endereco, LocalDate dataNascimento,
                  String matricula, double salario, double cargaHoraria,
                  String crm, String especialidade, boolean plantao) {
        super(nome, endereco, dataNascimento, matricula, salario, cargaHoraria);
        this.id = proximoId.getAndIncrement();
        this.crm = crm;
        this.especialidade = especialidade;
        this.plantao = plantao;
    }

    public int getId() { return id; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { 
        if (crm == null) throw new NullPointerException("crm não pode ser nulo"); 
        this.crm = crm; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { 
        if (especialidade == null) throw new NullPointerException
        ("especialidade não pode ser nula"); 
        this.especialidade = especialidade; }

    public boolean isPlantao() { return plantao; }
    public void setPlantao(boolean plantao) { this.plantao = plantao; }

    public static void setProximoId(int id) { 
        if (id <= 0) throw new IllegalArgumentException("id deve ser maior que zero");
        proximoId.set(id);
    }

    @Override
    public String toString() {
        return id + " - Dr(a). " + getNome();
    }
}
