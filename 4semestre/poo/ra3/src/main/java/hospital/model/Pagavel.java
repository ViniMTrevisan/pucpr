package hospital.model;

public interface Pagavel {
    double calcularTotal();
    String gerarFatura();
    void aplicarDesconto(double percentual);
    String emitirRecibo();
}
