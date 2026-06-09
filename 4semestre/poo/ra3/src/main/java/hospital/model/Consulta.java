package hospital.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consulta implements Pagavel, Serializable {
    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int id;
    private LocalDateTime dataHora;
    private double valor;
    private double desconto;
    private String nomePaciente;
    private String nomeMedico;

    public Consulta(LocalDateTime dataHora, double valor, String nomePaciente, String nomeMedico) {
        this.id = proximoId++;
        this.dataHora = dataHora;
        this.valor = valor;
        this.desconto = 0;
        this.nomePaciente = nomePaciente;
        this.nomeMedico = nomeMedico;
    }

    @Override
    public double calcularTotal() {
        return valor - (valor * desconto / 100.0);
    }

    @Override
    public String gerarFatura() {
        return "Fatura #" + id + " | Paciente: " + nomePaciente +
               " | Total: R$" + String.format("%.2f", calcularTotal());
    }

    @Override
    public void aplicarDesconto(double percentual) {
        this.desconto = percentual;
    }

    @Override
    public String emitirRecibo() {
        return "Recibo Consulta #" + id + " | Data: " +
               dataHora.format(FMT) + " | Valor: R$" + String.format("%.2f", calcularTotal());
    }

    public int getId() { return id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public double getDesconto() { return desconto; }
    public void setDesconto(double desconto) { this.desconto = desconto; }

    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }

    public String getNomeMedico() { return nomeMedico; }
    public void setNomeMedico(String nomeMedico) { this.nomeMedico = nomeMedico; }

    public static void setProximoId(int id) { proximoId = id; }
}
