public class PushSender implements MensagemSender {
    @Override
    public void enviarMensagem(String destinatario, String mensagem) {
        System.out.println("🔔 PUSH NOTIFICATION enviada para: " + destinatario);
        System.out.println("   Conteúdo: " + mensagem);
        System.out.println("   Via: Firebase Cloud Messaging\n");
    }
}