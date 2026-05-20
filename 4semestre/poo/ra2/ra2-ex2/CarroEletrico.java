public class CarroEletrico extends Carro {
    private int autonomiaBateria;

    public CarroEletrico(String marca, int ano, int portas, int autonomiaBateria) {
        super(marca, ano, portas);
        this.autonomiaBateria = autonomiaBateria;
    }

    public int getAutonomiaBateria() {
        return autonomiaBateria;
    }

    public void setAutonomiaBateria(int autonomiaBateria) {
        this.autonomiaBateria = autonomiaBateria;
    }

    @Override
    public void exibirDados() {
        System.out.println("Carro Elétrico:");
        System.out.println("  Marca:             " + getMarca());
        System.out.println("  Ano:               " + getAno());
        System.out.println("  Portas:            " + getPortas());
        System.out.println("  Autonomia bateria: " + autonomiaBateria + " km");
    }
}
