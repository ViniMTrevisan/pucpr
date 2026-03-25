public class Calculadora {
    private double valor1;
    private double valor2;
    private double resultado;

    public Calculadora(double valor1, double valor2) {
        this.valor1 = valor1;
        this.valor2 = valor2;
        this.resultado = 0;
    }

    public void somar() {
        resultado = valor1 + valor2;
    }

    public void subtrair() {
        resultado = valor1 - valor2;
    }

    public void mostrarResultado() {
        System.out.println("Resultado: " + resultado);
    }

    public static void main(String[] args) {
        Calculadora calculadoraSoma = new Calculadora(10, 4);
        calculadoraSoma.somar();
        System.out.print("Soma de 10.0 + 4.0 -> ");
        calculadoraSoma.mostrarResultado();

        Calculadora calculadoraSubtracao = new Calculadora(10, 4);
        calculadoraSubtracao.subtrair();
        System.out.print("Subtracao de 10.0 - 4.0 -> ");
        calculadoraSubtracao.mostrarResultado();
    }
}
