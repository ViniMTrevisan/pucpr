package morse;

/**
 * Classe que representa a árvore binária de código Morse
 */
public class MorseTreeConsole {
    private MorseNodeConsole root;

    public MorseTreeConsole() {
        this.root = new MorseNodeConsole("");
    }

    public MorseNodeConsole getRoot() {
        return root;
    }

    /**
     * Insere um caractere na árvore seguindo o código morse
     * Heurística: ponto (.) vai para esquerda, traço (-) vai para direita
     * @param morseCode código morse do caractere (ex: ".-" para A)
     * @param character caractere a ser inserido
     */
    public void insert(String morseCode, String character) {
        MorseNodeConsole current = root;

        for (int i = 0; i < morseCode.length(); i++) {
            char symbol = morseCode.charAt(i);

            if (symbol == '.') {
                // Ponto vai para a esquerda
                if (current.getLeft() == null) {
                    current.setLeft(new MorseNodeConsole(""));
                }
                current = current.getLeft();
            } else if (symbol == '-') {
                // Traço vai para a direita
                if (current.getRight() == null) {
                    current.setRight(new MorseNodeConsole(""));
                }
                current = current.getRight();
            }
        }

        // Define o caractere no nó folha
        current.setCharacter(character);
    }

    /**
     * Constrói a árvore completa com o alfabeto em código morse
     */
    public void buildTree() {
        // Letras do alfabeto
        insert(".-", "A");
        insert("-...", "B");
        insert("-.-.", "C");
        insert("-..", "D");
        insert(".", "E");
        insert("..-.", "F");
        insert("--.", "G");
        insert("....", "H");
        insert("..", "I");
        insert(".---", "J");
        insert("-.-", "K");
        insert(".-..", "L");
        insert("--", "M");
        insert("-.", "N");
        insert("---", "O");
        insert(".--.", "P");
        insert("--.-", "Q");
        insert(".-.", "R");
        insert("...", "S");
        insert("-", "T");
        insert("..-", "U");
        insert("...-", "V");
        insert(".--", "W");
        insert("-..-", "X");
        insert("-.--", "Y");
        insert("--..", "Z");

        // Números
        insert(".----", "1");
        insert("..---", "2");
        insert("...--", "3");
        insert("....-", "4");
        insert(".....", "5");
        insert("-....", "6");
        insert("--...", "7");
        insert("---..", "8");
        insert("----.", "9");
        insert("-----", "0");
    }

    /**
     * Decodifica um único caractere em código morse
     * @param morseCode código morse do caractere
     * @return caractere decodificado ou "?" se não encontrado
     */
    public String decodeCharacter(String morseCode) {
        MorseNodeConsole current = root;

        for (int i = 0; i < morseCode.length(); i++) {
            char symbol = morseCode.charAt(i);

            if (symbol == '.') {
                current = current.getLeft();
            } else if (symbol == '-') {
                current = current.getRight();
            }

            if (current == null) {
                return "?";
            }
        }

        return current.getCharacter().isEmpty() ? "?" : current.getCharacter();
    }

    /**
     * Decodifica uma palavra ou frase completa em código morse
     * @param morseString string com códigos morse separados por espaço
     * @return palavra ou frase decodificada
     */
    public String decode(String morseString) {
        StringBuilder decoded = new StringBuilder();
        String[] morseCharacters = morseString.trim().split("\\s+");

        for (String morseChar : morseCharacters) {
            if (!morseChar.isEmpty()) {
                decoded.append(decodeCharacter(morseChar));
            }
        }

        return decoded.toString();
    }

    /**
     * Imprime a árvore no console de forma visual
     */
    public void printTree() {
        System.out.println("\n┌─ Raiz (vazia)");
        printTreeHelper(root, "", true, true);
        System.out.println();
    }

    private void printTreeHelper(MorseNodeConsole node, String prefix, boolean isTail, boolean isRoot) {
        if (node != null && !isRoot) {
            String character = node.getCharacter().isEmpty() ? "[ ]" : "[" + node.getCharacter() + "]";
            System.out.println(prefix + (isTail ? "└── " : "├── ") + character);
        }

        if (node != null) {
            String newPrefix = prefix + (isTail ? "    " : "│   ");

            if (node.getLeft() != null) {
                System.out.print(newPrefix + (node.getRight() != null ? "├── " : "└── ") + "(.) ");
                printTreeHelper(node.getLeft(), newPrefix, node.getRight() == null, false);
            }

            if (node.getRight() != null) {
                System.out.print(newPrefix + "└── (-) ");
                printTreeHelper(node.getRight(), newPrefix, true, false);
            }
        }
    }

    /**
     * Imprime a tabela de código morse
     */
    public void printMorseTable() {
        System.out.println("\n========== TABELA DE CÓDIGO MORSE ==========");
        System.out.println("\nLETRAS:");
        System.out.println("A: .-      N: -.      ");
        System.out.println("B: -...    O: ---     ");
        System.out.println("C: -.-.    P: .--.    ");
        System.out.println("D: -..     Q: --.-    ");
        System.out.println("E: .       R: .-.     ");
        System.out.println("F: ..-.    S: ...     ");
        System.out.println("G: --.     T: -       ");
        System.out.println("H: ....    U: ..-     ");
        System.out.println("I: ..      V: ...-    ");
        System.out.println("J: .---    W: .--     ");
        System.out.println("K: -.-     X: -..-    ");
        System.out.println("L: .-..    Y: -.--    ");
        System.out.println("M: --      Z: --..    ");

        System.out.println("\nNÚMEROS:");
        System.out.println("1: .----   6: -....   ");
        System.out.println("2: ..---   7: --...   ");
        System.out.println("3: ...--   8: ---..   ");
        System.out.println("4: ....-   9: ----.   ");
        System.out.println("5: .....   0: -----   ");
        System.out.println("============================================");
    }

    /**
     * Método auxiliar para testes - imprime estrutura simplificada
     */
    public void printSimpleTree() {
        System.out.println("\n=== Árvore Binária de Código Morse ===");
        printSimpleTreeHelper(root, 0);
        System.out.println("=====================================\n");
    }

    private void printSimpleTreeHelper(MorseNodeConsole node, int nivel) {
        if (node != null) {
            // Imprime espaços para representar o nível
            for (int i = 0; i < nivel; i++) {
                System.out.print("  ");
            }

            // Imprime o caractere
            String display = node.getCharacter().isEmpty() ? "[]" : node.getCharacter();
            System.out.println(display);

            // Imprime os filhos
            if (node.getLeft() != null || node.getRight() != null) {
                printSimpleTreeHelper(node.getLeft(), nivel + 1);
                printSimpleTreeHelper(node.getRight(), nivel + 1);
            }
        }
    }
}
