public class Carro extends Veiculo {
    private int portas;

    public Carro(String marca, int ano, int portas) {
        super(marca, ano);
        this.portas = portas;
    }

    public int getPortas() {
        return portas;
    }

    public void setPortas(int portas) {
        this.portas = portas;
    }

    @Override
    public void exibirDados() {
        System.out.println("Carro:");
        System.out.println("  Marca:  " + getMarca());
        System.out.println("  Ano:    " + getAno());
        System.out.println("  Portas: " + portas);
    }
}
