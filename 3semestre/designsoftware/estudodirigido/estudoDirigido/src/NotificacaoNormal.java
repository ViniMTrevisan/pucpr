public class NotificacaoNormal extends Notificacao {
    public NotificacaoNormal(MensagemSender sender) {
        super(sender);
    }

    @Override
    public void notificar(String destinatario, String mensagem) {
        System.out.println("📬 ========== NOTIFICAÇÃO NORMAL ==========");
        sender.enviarMensagem(destinatario, mensagem);
    }
}