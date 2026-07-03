package hospital.model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Paciente extends Pessoa {
    private static final long serialVersionUID = 1L;
    private static final AtomicInteger proximoId = new AtomicInteger(1);

    private final int id;
    private TipoSanguineo tipoSanguineo;
    private String convenio;
    private String cpf;

    public Paciente(String nome, String endereco, LocalDate dataNascimento,
                    TipoSanguineo tipoSanguineo, String convenio, String cpf) {
        super(nome, endereco, dataNascimento);
        this.id = proximoId.getAndIncrement();
        this.tipoSanguineo = tipoSanguineo;
        this.convenio = convenio;
        this.cpf = cpf;
    }

    public int getId() { return id; }

    public TipoSanguineo getTipoSanguineo() { return tipoSanguineo; }
    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) { 
        if (tipoSanguineo == null) throw new NullPointerException
        ("tipoSanguineo não pode ser nulo");
        this.tipoSanguineo = tipoSanguineo; 
    }

    public String getConvenio() { return convenio; }
    public void setConvenio(String convenio) { this.convenio = convenio; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        if (cpf == null) throw new NullPointerException("cpf não pode ser nulo");
        if (cpf.trim().isEmpty()) throw new IllegalArgumentException("cpf inválido");
        this.cpf = cpf;
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
