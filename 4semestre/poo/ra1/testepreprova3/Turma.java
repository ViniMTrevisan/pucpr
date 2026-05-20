package testepreprova3;

import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String nome; 
    private String periodo; 
    private List<Aluno> alunos; 

    public Turma(String nome, String periodo) {
        this.nome = nome; 
        this.periodo = periodo;
        this.alunos = new ArrayList<Aluno>();
    }

    public void addAluno(Aluno aluno) {
        alunos.add(aluno); 
    }

    public void listarAlunos(){
        for (Aluno aluno : alunos) {
            System.out.println(
            "Turma: " + nome + 
            " | Periodo " + periodo + 
            " | Aluno " + aluno.getNomeAluno() + " | " + aluno.getMatricula());
        }
    }

}
