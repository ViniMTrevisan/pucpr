package hospital.model;

import java.time.LocalDate;

public class Medico extends Funcionario {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;

    private int id;
    private String crm;
    private String especialidade;
    private boolean plantao;

    public Medico(String nome, String endereco, LocalDate dataNascimento,
                  String matricula, double salario, double cargaHoraria,
                  String crm, String especialidade, boolean plantao) {
        super(nome, endereco, dataNascimento, matricula, salario, cargaHoraria);
        this.id = proximoId++;
        this.crm = crm;
        this.especialidade = especialidade;
        this.plantao = plantao;
    }

    public int getId() { return id; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public boolean isPlantao() { return plantao; }
    public void setPlantao(boolean plantao) { this.plantao = plantao; }

    public static void setProximoId(int id) { proximoId = id; }

    @Override
    public String toString() {
        return id + " - Dr(a). " + getNome();
    }
}
