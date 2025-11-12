package morse;

/**
 * Classe que representa um nó da árvore de código Morse
 */
public class MorseNodeConsole {
    private String character;
    private MorseNodeConsole left;  // Ponto (.)
    private MorseNodeConsole right; // Traço (-)

    public MorseNodeConsole(String character) {
        this.character = character;
        this.left = null;
        this.right = null;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public MorseNodeConsole getLeft() {
        return left;
    }

    public void setLeft(MorseNodeConsole left) {
        this.left = left;
    }

    public MorseNodeConsole getRight() {
        return right;
    }

    public void setRight(MorseNodeConsole right) {
        this.right = right;
    }
}
