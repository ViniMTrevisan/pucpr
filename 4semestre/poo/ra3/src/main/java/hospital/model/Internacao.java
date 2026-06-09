package hospital.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Internacao implements Pagavel, Serializable {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;
    private static final double DIARIA = 350.0;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int id;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String diagnostico;
    private String nomePaciente;
    private double desconto;

    public Internacao(LocalDate dataEntrada, LocalDate dataSaida,
                      String diagnostico, String nomePaciente) {
        this.id = proximoId++;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.diagnostico = diagnostico;
        this.nomePaciente = nomePaciente;
        this.desconto = 0;
    }

    public long getDias() {
        if (dataSaida == null || dataEntrada == null) return 0;
        return ChronoUnit.DAYS.between(dataEntrada, dataSaida);
    }

    @Override
    public double calcularTotal() {
        double total = getDias() * DIARIA;
        return total - (total * desconto / 100.0);
    }

    @Override
    public String gerarFatura() {
        return "Fatura Internação #" + id + " | Paciente: " + nomePaciente +
               " | Dias: " + getDias() + " | Total: R$" + String.format("%.2f", calcularTotal());
    }

    @Override
    public void aplicarDesconto(double percentual) {
        this.desconto = percentual;
    }

    @Override
    public String emitirRecibo() {
        return "Recibo Internação #" + id + " | Entrada: " + dataEntrada.format(FMT) +
               " | Saída: " + (dataSaida != null ? dataSaida.format(FMT) : "em curso") +
               " | Total: R$" + String.format("%.2f", calcularTotal());
    }

    public int getId() { return id; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }

    public double getDesconto() { return desconto; }
    public void setDesconto(double desconto) { this.desconto = desconto; }

    public static void setProximoId(int id) { proximoId = id; }
}
