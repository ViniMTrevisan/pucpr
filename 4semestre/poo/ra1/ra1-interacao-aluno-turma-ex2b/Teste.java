public class Teste {
    public static void main(String[] args) {
        Turma turmaA = new Turma("POO - Turma A", "Noturno");
        Turma turmaB = new Turma("POO - Turma B", "Matutino");

        turmaA.adicionarAluno(new Aluno("Ana Paula", "2026001"));
        turmaA.adicionarAluno(new Aluno("Bruno Lima", "2026002"));
        turmaA.adicionarAluno(new Aluno("Carla Souza", "2026003"));
        turmaA.adicionarAluno(new Aluno("Diego Martins", "2026004"));
        turmaA.adicionarAluno(new Aluno("Elisa Rocha", "2026005"));

        turmaB.adicionarAluno(new Aluno("Felipe Costa", "2026101"));
        turmaB.adicionarAluno(new Aluno("Gabriela Nunes", "2026102"));
        turmaB.adicionarAluno(new Aluno("Henrique Alves", "2026103"));
        turmaB.adicionarAluno(new Aluno("Isabela Campos", "2026104"));
        turmaB.adicionarAluno(new Aluno("Joao Pedro", "2026105"));

        turmaA.listarAlunos();
        System.out.println();
        turmaB.listarAlunos();
    }
}
