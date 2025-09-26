import java.util.*;

public class TreeDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();

        System.out.println("Escolha modo:\n1) Não balanceada\n2) Balanceada (AVL)");
        int mode = 0;
        while (mode != 1 && mode != 2) {
            System.out.print("Modo (1/2): ");
            try { mode = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { mode = 0; }
        }

        List<Integer> initial = new ArrayList<>();
        System.out.println("Gerando 20 números inteiros aleatórios (0-99):");
        while (initial.size() < 20) {
            int v = rnd.nextInt(100);
            initial.add(v);
        }
        System.out.println(initial);

        if (mode == 1) runUnbalanced(sc, initial);
        else runAVL(sc, initial);
    }

    private static void runUnbalanced(Scanner sc, List<Integer> initial) {
        UnbalancedBST tree = new UnbalancedBST();
        for (int v : initial) tree.insert(v);

        System.out.println("Árvore não balanceada - inicial:");
        TreePrinter.toLines(tree.getRoot()).forEach(System.out::println);

        List<Integer> removals = chooseNumbers(sc, tree, 5, "remover");
        for (int r : removals) tree.remove(r);

        List<Integer> additions = new ArrayList<>();
        System.out.println("Inserindo 10 novos números (digitados ou aleatórios). Para número aleatório, entre 'r'.");
        while (additions.size() < 10) {
            System.out.print("Valor " + (additions.size()+1) + ": ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("r")) additions.add(new Random().nextInt(100));
            else {
                try { additions.add(Integer.parseInt(line)); } catch (Exception e) { System.out.println("Entrada inválida"); }
            }
        }
        for (int a : additions) tree.insert(a);

        System.out.println("Árvore não balanceada - final:");
        TreePrinter.toLines(tree.getRoot()).forEach(System.out::println);
    }

    private static void runAVL(Scanner sc, List<Integer> initial) {
        AVLTree tree = new AVLTree();
        for (int v : initial) tree.insert(v);

        System.out.println("Árvore AVL - inicial:");
        TreePrinter.toLines(tree.getRoot()).forEach(System.out::println);

        List<Integer> removals = chooseNumbers(sc, tree, 5, "remover");
        for (int r : removals) tree.remove(r);

        List<Integer> additions = new ArrayList<>();
        System.out.println("Inserindo 10 novos números (digitados ou aleatórios). Para número aleatório, entre 'r'.");
        while (additions.size() < 10) {
            System.out.print("Valor " + (additions.size()+1) + ": ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("r")) additions.add(new Random().nextInt(100));
            else {
                try { additions.add(Integer.parseInt(line)); } catch (Exception e) { System.out.println("Entrada inválida"); }
            }
        }
        for (int a : additions) tree.insert(a);

        System.out.println("Árvore AVL - final:");
        TreePrinter.toLines(tree.getRoot()).forEach(System.out::println);
    }

    private static List<Integer> chooseNumbers(Scanner sc, Object tree, int count, String action) {
        List<Integer> chosen = new ArrayList<>();
        System.out.println("Escolha " + count + " números para " + action + " (digite 'l' para listar os valores atuais). Se o valor não existir, será ignorado.");
        while (chosen.size() < count) {
            System.out.print("Número " + (chosen.size()+1) + ": ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("l")) {
                if (tree instanceof UnbalancedBST) System.out.println("Valores atuais (in-order): " + inorderUnbalanced(((UnbalancedBST)tree).getRoot()));
                else System.out.println("Valores atuais (in-order): " + inorderAVL(((AVLTree)tree).getRoot()));
                continue;
            }
            try {
                int v = Integer.parseInt(line);
                boolean exists = tree instanceof UnbalancedBST ? ((UnbalancedBST)tree).contains(v) : ((AVLTree)tree).contains(v);
                if (!exists) System.out.println("Valor não existe na árvore; ignorado.");
                else chosen.add(v);
            } catch (Exception e) {
                System.out.println("Entrada inválida");
            }
        }
        return chosen;
    }

    private static List<Integer> inorderUnbalanced(UnbalancedBST.Node root) {
        List<Integer> out = new ArrayList<>();
        inorderU(root, out);
        return out;
    }

    private static void inorderU(UnbalancedBST.Node node, List<Integer> out) {
        if (node == null) return;
        inorderU(node.left, out);
        out.add(node.val);
        inorderU(node.right, out);
    }

    private static List<Integer> inorderAVL(AVLTree.Node root) {
        List<Integer> out = new ArrayList<>();
        inorderA(root, out);
        return out;
    }

    private static void inorderA(AVLTree.Node node, List<Integer> out) {
        if (node == null) return;
        inorderA(node.left, out);
        out.add(node.val);
        inorderA(node.right, out);
    }
}
