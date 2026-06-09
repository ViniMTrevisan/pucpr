public class GuerreiroEspada extends PersonagemBase implements CouracaGolpeEspecial {
    public GuerreiroEspada(String nome, int nivelVida) {
        super(nome, nivelVida);
    }

    @Override
    public void atacar(PersonagemBase inimigo) {
        String nomeGolpe = "Ataque com Espada";
        int dano = 10;
        System.out.println(nome + " aplicou " + nomeGolpe);
        inimigo.sofrerDano(dano);
    }

    @Override
    public void atacarGolpeEspecial(PersonagemBase inimigo) {
        String nomeGolpe = "Golpe de Thor";
        int dano = 30;
        System.out.println(nome + " aplicou " + nomeGolpe);
        inimigo.sofrerDano(dano);
    }
}
