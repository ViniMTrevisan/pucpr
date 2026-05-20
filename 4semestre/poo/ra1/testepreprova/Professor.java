package testepreprova;

public class Professor {
    private String nome;
    private int id; 

    public Professor(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public String getProfessorNome() {
        return nome;
    }
    public int getId() {
        return id; 
    }
}
