package hospital.model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String endereco;
    private LocalDate dataNascimento;

    public Pessoa(String nome, String endereco, LocalDate dataNascimento) {
        this.nome = nome;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
}
