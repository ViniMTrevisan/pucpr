public class Motor {
    private static final int RPM_MINIMO = 0;
    private static final int RPM_MAXIMO = 8000;
    private static final int RPM_MARCHA_LENTA = 1000;

    private boolean ligado;
    private int rpm;

    public Motor() {
        this.ligado = false;
        this.rpm = RPM_MINIMO;
    }

    public boolean isLigado() {
        return ligado;
    }

    public int getRpm() {
        return rpm;
    }

    public void ligar() {
        if (!ligado) {
            ligado = true;
            rpm = RPM_MARCHA_LENTA;
        }
    }

    public void desligar() {
        ligado = false;
        rpm = RPM_MINIMO;
    }

    public void acelerar(int nivelAceleracao) {
        if (!ligado) {
            return;
        }

        rpm = rpm + (nivelAceleracao * 1000);

        if (rpm >= RPM_MAXIMO) {
            rpm = RPM_MAXIMO;
            return;
        }

        if (rpm <= RPM_MINIMO) {
            desligar();
        }
    }
}
