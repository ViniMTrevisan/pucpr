public abstract class PersonagemBase {
    protected String nome;
    protected int nivelVida;

    public PersonagemBase(String nome, int nivelVida) {
        this.nome = nome;
        this.nivelVida = nivelVida;
    }

    public abstract void atacar(PersonagemBase inimigo);

    public void mostrarStatus() {
        System.out.println(nome + " - Vida: " + nivelVida);
    }

    public void sofrerDano(int nivelDano) {
        nivelVida -= nivelDano;
        System.out.println(nome + " sofreu " + nivelDano + " de dano.");

        if (nivelVida < 0) {
            nivelVida = 0;
        }

        mostrarStatus();

        if (nivelVida == 0) {
            System.out.println("Morreu");
        }
    }
}
