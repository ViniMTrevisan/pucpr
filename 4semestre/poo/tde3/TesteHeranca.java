public class TesteHeranca {

    public static void main(String[] args) {

        ClientePF clienteFisico = new ClientePF();
        clienteFisico.agencia = "1234";
        clienteFisico.contaCorrente = "0123456-7";
        clienteFisico.saldo = 150.50;
        clienteFisico.cpf = "123.456.789-10";
        clienteFisico.sacarContaCorrente(50.00);
        clienteFisico.obterEmprestimo(5000.00);
        clienteFisico.depositarContaCorrente(100.00);

        ClientePJ clienteJuridico = new ClientePJ();
        clienteJuridico.agencia = "5432";
        clienteJuridico.contaCorrente = "9876543-7";
        clienteJuridico.saldo = 6000.00;
        clienteJuridico.cnpj = "12.345.678/0001-00";
        clienteJuridico.limite = 2000.00;
        clienteJuridico.sacarContaCorrente(50.00);
        clienteJuridico.depositarContaCorrente(200.00);
        double limite = clienteJuridico.obterLimite();
    }
}
