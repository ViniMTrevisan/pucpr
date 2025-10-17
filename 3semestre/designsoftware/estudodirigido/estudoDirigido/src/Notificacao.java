public abstract class Notificacao {
    protected MensagemSender sender; // Bridge para a implementação

    public Notificacao(MensagemSender sender) {
        this.sender = sender;
    }

    public abstract void notificar(String destinatario, String mensagem);
}