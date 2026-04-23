public class Disciplina {
    private String nomeDisciplina;
    private Professor professor;
    private Aluno alunoMatriculado;

    public Disciplina(String nomeDisciplina, Professor professor, Aluno alunoMatriculado) {
        this.nomeDisciplina = nomeDisciplina;
        this.professor = professor;
        this.alunoMatriculado = alunoMatriculado;
    }

    public void exibirInformacoes() {
        System.out.println("Disciplina: " + nomeDisciplina);
        System.out.println("Professor: " + professor.getNome() + " | ID: " + professor.getIdentificador());
        System.out.println("Aluno matriculado: " + alunoMatriculado.getNome() + " | Matricula: " + alunoMatriculado.getMatricula());
    }
}

