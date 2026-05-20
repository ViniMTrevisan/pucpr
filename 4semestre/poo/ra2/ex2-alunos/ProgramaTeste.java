public class ProgramaTeste {
    public static void main(String[] args) {
        Aluno aluno1 = new Aluno(
            "Ana Souza",
            "Rua das Flores, 120",
            "(41) 98888-1111",
            "123.456.789-00",
            "2026001",
            5
        );

        Aluno aluno2 = new Aluno(
            "Bruno Lima",
            "Av. Central, 450",
            "(41) 97777-2222",
            "987.654.321-00",
            "2026002",
            4
        );

        Aluno aluno3 = new Aluno(
            "Carla Mendes",
            "Rua do Sol, 89",
            "(41) 96666-3333",
            "111.222.333-44",
            "2026003",
            6
        );

        Aluno[] alunos = {aluno1, aluno2, aluno3};

        for (int i = 0; i < alunos.length; i++) {
            System.out.println("=== Aluno " + (i + 1) + " ===");
            alunos[i].mostrarInformacoes();
            System.out.println();
        }
    }
}
