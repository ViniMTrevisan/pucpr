public class Main {
    public static void main(String[] args) {
        System.out.println("Demonstração Bridge: ");

        // Criando as implementações
        MensagemSender emailSender = new EmailSender();
        MensagemSender smsSender = new SMSSender();
        MensagemSender pushSender = new PushSender();

        // Exemplo 1: Notificação Urgente via Email
        Notificacao notif1 = new NotificacaoUrgente(emailSender);
        notif1.notificar("joao@empresa.com", "Servidor principal fora do ar!");

        // Exemplo 2: Notificação Urgente via SMS
        Notificacao notif2 = new NotificacaoUrgente(smsSender);
        notif2.notificar("+55 11 99999-9999", "Código de verificação: 123456");


        // Exemplo 3: Notificação Normal via Push
        Notificacao notif3 = new NotificacaoNormal(pushSender);
        notif3.notificar("user_12345", "Você tem uma nova mensagem");

        // Exemplo 4: Notificação Agendada via Email
        Notificacao notif4 = new NotificacaoAgendada(emailSender, "15/10/2025 às 10:00");
        notif4.notificar("maria@empresa.com", "Reunião de equipe amanhã");

        // Exemplo 5: Notificação Agendada via SMS
        Notificacao notif5 = new NotificacaoAgendada(smsSender, "20/10/2025 às 14:30");
        notif5.notificar("+55 21 88888-8888", "Lembrete: Consulta médica");
    }
}