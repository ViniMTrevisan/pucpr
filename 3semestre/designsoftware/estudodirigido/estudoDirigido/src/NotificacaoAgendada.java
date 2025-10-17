public class NotificacaoAgendada extends Notificacao {
    private String horario;

    public NotificacaoAgendada(MensagemSender sender, String horario) {
        super(sender);
        this.horario = horario;
    }

    @Override
    public void notificar(String destinatario, String mensagem) {
        System.out.println("⏰ ========== NOTIFICAÇÃO AGENDADA ==========");
        System.out.println("   Agendada para: " + horario);
        sender.enviarMensagem(destinatario, mensagem);
    }
}