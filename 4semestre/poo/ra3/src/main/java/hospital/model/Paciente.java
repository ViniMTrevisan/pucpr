package hospital.model;

import java.time.LocalDate;

public class Paciente extends Pessoa {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;

    private int id;
    private TipoSanguineo tipoSanguineo;
    private String convenio;
    private String cpf;

    public Paciente(String nome, String endereco, LocalDate dataNascimento,
                    TipoSanguineo tipoSanguineo, String convenio, String cpf) {
        super(nome, endereco, dataNascimento);
        this.id = proximoId++;
        this.tipoSanguineo = tipoSanguineo;
        this.convenio = convenio;
        this.cpf = cpf;
    }

    public int getId() { return id; }

    public TipoSanguineo getTipoSanguineo() { return tipoSanguineo; }
    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) { this.tipoSanguineo = tipoSanguineo; }

    public String getConvenio() { return convenio; }
    public void setConvenio(String convenio) { this.convenio = convenio; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public static void setProximoId(int id) { proximoId = id; }

    @Override
    public String toString() {
        return id + " - " + getNome();
    }
}
