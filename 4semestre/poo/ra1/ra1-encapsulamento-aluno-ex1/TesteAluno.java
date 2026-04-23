public class TesteAluno {
    public static void main(String[] args) {
        Aluno aluno1 = new Aluno("2026001", "Mariana Costa", "Rua A, 100", 3500.00);
        Aluno aluno2 = new Aluno("2026002", "Felipe Santos", "Rua B, 200", 2200.00);
        Aluno aluno3 = new Aluno("2026003", "Camila Rocha", "Rua C, 300", 4100.00);

        aluno1.matricular();
        aluno3.matricular();

        Aluno[] alunos = {aluno1, aluno2, aluno3};
        for (Aluno aluno : alunos) {
            System.out.println("RA: " + aluno.getRa() + " | Nome: " + aluno.getNome() + " | Status: " + aluno.getStatus());
        }
    }
}

