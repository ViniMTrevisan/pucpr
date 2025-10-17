public class SMSSender implements MensagemSender {
    @Override
    public void enviarMensagem(String destinatario, String mensagem) {
        System.out.println("📱 SMS enviado para: " + destinatario);
        System.out.println("   Conteúdo: " + mensagem);
        System.out.println("   Via: Operadora GSM\n");
    }
}