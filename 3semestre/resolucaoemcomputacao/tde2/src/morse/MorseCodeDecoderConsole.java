package morse;

import java.util.Scanner;

/**
 * Versão em Console do Decodificador de Código Morse
 * @author Seu Nome
 * @version 1.0
 */
public class MorseCodeDecoderConsole {

    public static void main(String[] args) {
        MorseTreeConsole morseTree = new MorseTreeConsole();
        morseTree.buildTree();

        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("  DECODIFICADOR DE CÓDIGO MORSE - TDE 2");
        System.out.println("========================================\n");

        // Imprime a árvore
        System.out.println("Estrutura da Árvore Binária:");
        morseTree.printTree();
        System.out.println();

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Decodificar código morse");
            System.out.println("2. Ver árvore novamente");
            System.out.println("3. Ver tabela de código morse");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    System.out.println("\nDigite o código morse (use . para ponto e - para traço)");
                    System.out.println("Separe cada letra com ESPAÇO");
                    System.out.println("Exemplo: .... . .-.. .-.. ---");
                    System.out.print("Código morse: ");
                    String morseCode = scanner.nextLine();

                    String decoded = morseTree.decode(morseCode);
                    System.out.println("\n--- RESULTADO ---");
                    System.out.println("Código morse: " + morseCode);
                    System.out.println("Decodificado: " + decoded);
                    break;

                case 2:
                    System.out.println("\nEstrutura da Árvore Binária:");
                    morseTree.printTree();
                    break;

                case 3:
                    morseTree.printMorseTable();
                    break;

                case 4:
                    continuar = false;
                    System.out.println("\nEncerrando o programa...");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
            }
        }

        scanner.close();
    }
}
