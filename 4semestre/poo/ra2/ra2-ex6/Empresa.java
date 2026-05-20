import java.util.ArrayList;

public class Empresa {
    public static void main(String[] args) {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Desenvolvedor("Ana", 3000.0, 10));
        funcionarios.add(new Designer("Bruno", 2800.0, 500.0));

        for (Funcionario funcionario : funcionarios) {
            funcionario.exibirInformacoes();
            System.out.println();
        }
    }
}
