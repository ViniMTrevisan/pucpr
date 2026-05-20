import java.util.ArrayList;
import java.util.List;

public class FolhaPagamento {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(new Assalariado("Ana", 4500.00));
        funcionarios.add(new Assalariado("Carlos", 5200.00));
        funcionarios.add(new Horista("Beatriz", 160, 30.00));
        funcionarios.add(new Horista("Diego", 120, 35.50));

        for (Funcionario funcionario : funcionarios) {
            funcionario.exibirDados();
        }
    }
}
