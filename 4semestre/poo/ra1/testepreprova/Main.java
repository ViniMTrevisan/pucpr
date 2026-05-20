package testepreprova;

public class Main {
    public static void main(String[] args) {
        Professor prof = new Professor("Vinicius Trevisan", 138);
        Aluno aluno = new Aluno("Ana Julia", 12); 
        Disciplina disc = new Disciplina(prof, aluno, "POO"); 

        disc.exibirInformacoes();
    }
}
