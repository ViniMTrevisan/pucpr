public class Arena {
    public static void main(String[] args) {
        PersonagemBase guerreiro = new GuerreiroEspada("Guerreiro", 100);
        PersonagemBase mago = new Mago("Mago", 100);

        System.out.println("=== Status Inicial ===");
        guerreiro.mostrarStatus();
        mago.mostrarStatus();

        System.out.println("=== Rodada Unica ===");
        guerreiro.atacar(mago);
        mago.atacar(guerreiro);

        CouracaGolpeEspecial couracaGuerreiro = (CouracaGolpeEspecial) guerreiro;
        CouracaGolpeEspecial couracaMago = (CouracaGolpeEspecial) mago;

        couracaGuerreiro.atacarGolpeEspecial(mago);
        couracaMago.atacarGolpeEspecial(guerreiro);
    }
}
