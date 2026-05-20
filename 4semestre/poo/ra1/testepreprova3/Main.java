package testepreprova3;

public class Main {
    public static void main(String[] args) {
        Turma turma1 = new Turma("Turma 1", "Noite"); 
        turma1.addAluno(new Aluno("Vinicius", 1));
        turma1.addAluno(new Aluno("Ana", 2));
        turma1.addAluno(new Aluno("Marcos", 3));
        turma1.addAluno(new Aluno("Viviane", 4));
        turma1.addAluno(new Aluno("Anelise", 5));
        
        Turma turma2 = new Turma("Turma 2", "Manha");
        turma2.addAluno(new Aluno("Guilherme", 9));
        turma2.addAluno(new Aluno("Leozao", 10));
        turma2.addAluno(new Aluno("Bento", 11));
        turma2.addAluno(new Aluno("Guizao", 12));
        turma2.addAluno(new Aluno("Lobo", 13));

        turma1.listarAlunos();
        System.out.println("-------------------------------------------------------");
        turma2.listarAlunos();
    }
}
