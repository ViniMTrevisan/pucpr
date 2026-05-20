package testepreprova3;


public class Aluno {
    private String nome;
    private int matricula;

    public Aluno(String nome, int matricula) {
        this.nome = nome;
        this.matricula = matricula;  
    }

    public String getNomeAluno(){
        return nome; 
    }

    public String setNomeAluno(String nome){
        this.nome = nome;
        return nome; 
    }

    public int getMatricula(){
        return matricula;
    }

    public int setMatricula(){
        this.matricula = matricula;
        return matricula;
    }

}
