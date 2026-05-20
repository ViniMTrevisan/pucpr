package testepreprova;

public class Disciplina {
    private Professor professor; 
    private Aluno aluno; 
    private String nome; 

    public Disciplina(Professor professor, Aluno aluno, String nome) {
        this.professor = professor;
        this.aluno = aluno;
        this.nome = nome; 
    }

    public void exibirInformacoes() {
        System.out.println("Disciplina: " + nome);
        System.out.println("Professor: " + professor.getProfessorNome() + "ID: " + professor.getId());
        System.out.println("Aluno: " + aluno.getAluno() + "Matricula: " + aluno.getMatriculaAluno());
    }
}
