public class Lampada {
    private boolean ligada;

    public Lampada() {
        this.ligada = false;
    }

    public void ligar() {
        this.ligada = true;
    }

    public void desligar() {
        this.ligada = false;
    }

    public boolean estaLigada() {
        return ligada;
    }
}

