public class EmailSender implements MensagemSender {
    @Override
    public void enviarMensagem(String destinatario, String mensagem) {
        System.out.println("📧 EMAIL enviado para: " + destinatario);
        System.out.println("   Conteúdo: " + mensagem);
        System.out.println("   Protocolo: SMTP\n");
    }
}