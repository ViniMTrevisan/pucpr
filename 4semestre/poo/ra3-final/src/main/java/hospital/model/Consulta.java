package hospital.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Consulta implements Pagavel, Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicInteger proximoId = new AtomicInteger(1);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final int id;
    private LocalDateTime dataHora;
    private double valor;
    private double desconto;
    private String nomePaciente;
    private String nomeMedico;

    public Consulta(LocalDateTime dataHora, double valor, String nomePaciente, String nomeMedico) {
        this.id = proximoId.getAndIncrement();
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
        if (nomePaciente == null || nomePaciente.trim().isEmpty()) throw new IllegalStateException
        ("nomePaciente não definido");
        return "Fatura #" + id + " | Paciente: " + nomePaciente +
               " | Total: R$" + String.format("%.2f", calcularTotal());
    }

    @Override
    public void aplicarDesconto(double percentual) {
        if (percentual < 0 || percentual > 100) throw new IllegalArgumentException("percentual deve estar entre 0 e 100");
        this.desconto = percentual;
    }

    @Override
    public String emitirRecibo() {
        if (dataHora == null) throw new IllegalStateException
        ("dataHora não está definida");
        return "Recibo Consulta #" + id + " | Data: " +
               dataHora.format(FMT) + " | Valor: R$" + String.format("%.2f", calcularTotal());
    }

    public int getId() { return id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { 
        if (dataHora == null) throw new NullPointerException
        ("dataHora não pode ser nulo"); 
        this.dataHora = dataHora; }

    public double getValor() { return valor; }
    public void setValor(double valor) {
        if (valor < 0) throw new IllegalArgumentException
        ("valor não pode ser negativo");
        this.valor = valor;
    }

    public double getDesconto() { return desconto; }
    public void setDesconto(double desconto) {
        if (desconto < 0 || desconto > 100) throw new IllegalArgumentException
        ("desconto deve estar entre 0 e 100");
        this.desconto = desconto;
    }

    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) {
        if (nomePaciente == null || nomePaciente.trim().isEmpty()) throw new IllegalArgumentException
        ("nomePaciente inválido");
        this.nomePaciente = nomePaciente;
    }

    public String getNomeMedico() { return nomeMedico; }
    public void setNomeMedico(String nomeMedico) {
        if (nomeMedico == null || nomeMedico.trim().isEmpty()) throw new IllegalArgumentException
        ("nomeMedico inválido");
        this.nomeMedico = nomeMedico;
    }

    public static void setProximoId(int id) { 
        if (id <= 0) throw new IllegalArgumentException("id deve ser maior que zero");
        proximoId.set(id);
    }
}
