public class SistemaUniversidade {
    public static void main(String[] args) {
        Professor professor = new Professor("Carlos Alberto", "PROF-101");
        Aluno aluno = new Aluno("Beatriz Nunes", "20261234");

        Disciplina disciplina = new Disciplina("Programacao Orientada a Objetos", professor, aluno);
        disciplina.exibirInformacoes();
    }
}
