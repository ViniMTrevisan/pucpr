import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String nome;
    private String periodo;
    private List<Aluno> alunos;

    public Turma(String nome, String periodo) {
        this.nome = nome;
        this.periodo = periodo;
        this.alunos = new ArrayList<>();
    }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public void listarAlunos() {
        System.out.println("Turma: " + nome + " | Periodo: " + periodo);

        for (Aluno aluno : alunos) {
            System.out.println("Nome: " + aluno.getNome() + " | Matricula: " + aluno.getMatricula());
        }
    }
}
