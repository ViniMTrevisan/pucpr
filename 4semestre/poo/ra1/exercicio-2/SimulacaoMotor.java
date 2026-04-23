public class SimulacaoMotor {
    public static void main(String[] args) {
        Motor motor = new Motor();

        System.out.println("Estado inicial -> ligado: " + motor.isLigado() + ", rpm: " + motor.getRpm());

        motor.ligar();
        System.out.println("Após ligar -> ligado: " + motor.isLigado() + ", rpm: " + motor.getRpm());

        motor.acelerar(3);
        System.out.println("Acelerando +3 -> ligado: " + motor.isLigado() + ", rpm: " + motor.getRpm());

        motor.acelerar(10);
        System.out.println("Acelerando +10 (limite) -> ligado: " + motor.isLigado() + ", rpm: " + motor.getRpm());

        motor.acelerar(-5);
        System.out.println("Desacelerando -5 -> ligado: " + motor.isLigado() + ", rpm: " + motor.getRpm());

        motor.acelerar(-10);
        System.out.println("Desacelerando -10 (desliga) -> ligado: " + motor.isLigado() + ", rpm: " + motor.getRpm());
    }
}
