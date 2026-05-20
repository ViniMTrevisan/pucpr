public class Aluno extends PessoaFisica {
    private String numeroMatricula;
    private int quantidadeDisciplinasMatriculadas;

    public Aluno(
        String nome,
        String endereco,
        String numeroCelular,
        String cpf,
        String numeroMatricula,
        int quantidadeDisciplinasMatriculadas
    ) {
        super(nome, endereco, numeroCelular, cpf);
        this.numeroMatricula = numeroMatricula;
        this.quantidadeDisciplinasMatriculadas = quantidadeDisciplinasMatriculadas;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public int getQuantidadeDisciplinasMatriculadas() {
        return quantidadeDisciplinasMatriculadas;
    }

    public void setQuantidadeDisciplinasMatriculadas(int quantidadeDisciplinasMatriculadas) {
        this.quantidadeDisciplinasMatriculadas = quantidadeDisciplinasMatriculadas;
    }

    @Override
    public void mostrarInformacoes() {
        super.mostrarInformacoes();
        System.out.println("Numero de matricula: " + getNumeroMatricula());
        System.out.println("Quantidade de disciplinas matriculadas: " + getQuantidadeDisciplinasMatriculadas());
    }
}
