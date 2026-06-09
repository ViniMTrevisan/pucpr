public class Mago extends PersonagemBase implements CouracaGolpeEspecial {
    public Mago(String nome, int nivelVida) {
        super(nome, nivelVida);
    }

    @Override
    public void atacar(PersonagemBase inimigo) {
        String nomeGolpe = "Abracadabra";
        int dano = 5;
        System.out.println(nome + " aplicou " + nomeGolpe);
        inimigo.sofrerDano(dano);
    }

    @Override
    public void atacarGolpeEspecial(PersonagemBase inimigo) {
        String nomeGolpe = "Feitiço de Merlin";
        int dano = 50;
        System.out.println(nome + " aplicou " + nomeGolpe);
        inimigo.sofrerDano(dano);
    }
}
