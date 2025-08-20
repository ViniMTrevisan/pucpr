package aula1908;

import java.util.ArrayList;
import java.util.List;

public class Personagem {
    private String nome;
    private String sexo;
    private double altura;
    private List<String> habilidades;

    public Personagem(String nome, String sexo, double altura, List<String> habilidades) {
        this.nome = nome;
        this.sexo = sexo;
        this.altura = altura;
        this.habilidades = habilidades;
    }

    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public double getAltura() {
        return altura;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }

    @Override
    public String toString() {
        return "Personagem{" +
                "nome='" + nome + '\'' +
                ", sexo='" + sexo + '\'' +
                ", altura=" + altura +
                ", habilidades='" + habilidades + '\'' +
                '}';
    }
}
