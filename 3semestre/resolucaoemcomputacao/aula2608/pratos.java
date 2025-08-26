import java.util.Stack;

public class pratos {
    public static void main(String[] args) {
        Stack<String> pilha = new Stack<>();
        int limitePilha = 5;

        try {
            for (int i = 1; i <= 7; i++) {
                if (pilha.size() >= limitePilha) {
                    throw new StackOverflowError("A pilha está cheia! Não dá para colocar mais pratos.");
                }
                pilha.push("Prato " + i);
                System.out.println("Adicionado: Prato " + i);
            }

            System.out.println("\nRemovendo pratos da pilha:");
            while (!pilha.isEmpty()) {
                String prato = pilha.pop();
                System.out.println("Retirado: " + prato);
            }
        } catch (StackOverflowError e) {
            System.out.println(e.getMessage());
        }
    }
}