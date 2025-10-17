public class NotificacaoUrgente extends Notificacao {
    public NotificacaoUrgente(MensagemSender sender) {
        super(sender);
    }

    @Override
    public void notificar(String destinatario, String mensagem) {
        System.out.println("🚨 ========== NOTIFICAÇÃO URGENTE ==========");
        String mensagemUrgente = "[URGENTE] " + mensagem.toUpperCase();
        sender.enviarMensagem(destinatario, mensagemUrgente);
    }
}