import java.util.*;

public class Main {
    static void runExercise(String title, BST tree, List<Integer> opsSequence, List<String> steps) {
        System.out.println("\n=== " + title + " ===");
        for (int i = 0; i < opsSequence.size(); i++) {
            int val = opsSequence.get(i);
            String op = steps.get(i);
            if (op.equals("insert")) {
                System.out.println("Inserir: " + val);
                tree.insert(val);
            } else if (op.equals("remove")) {
                System.out.println("Remover: " + val);
                tree.remove(val);
            }
            tree.printTree();
        }

        System.out.println("\nRespostas finais:");
        System.out.println("Raiz: " + (tree.root != null ? tree.root.key : "null"));
        System.out.println("Pais: " + tree.parents());
        System.out.println("Filhos: " + tree.children());
        System.out.println("Folhas: " + tree.leaves());
        System.out.println("Altura (arestas): " + tree.height());
        System.out.println("Profundidades: " + tree.depths());
    }

    public static void main(String[] args) {

        // Exercise 1
        BST t1 = new BST();
        List<Integer> seq1 = Arrays.asList(50,30,70,20,40,60,80,35,45,75, 20, 25, 70); 
        List<String> steps1 = new ArrayList<>();
        for (int i=0;i<10;i++) steps1.add("insert");
        steps1.add("remove");
        steps1.add("insert");
        steps1.add("remove");
        runExercise("Exercício 1", t1, seq1, steps1);

        // Exercise 2
        BST t2 = new BST();
        List<Integer> seq2 = Arrays.asList(15,10,20,8,12,17,25,6,11,16, 6);
        List<String> steps2 = new ArrayList<>();
        for (int i=0;i<10;i++) steps2.add("insert");
        steps2.add("remove");
        runExercise("Exercício 2", t2, seq2, steps2);

        // Exercise 3
        BST t3 = new BST();
        List<Integer> seq3 = Arrays.asList(40,20,60,10,30,50,70,25,35,65,75, 10, 15,22, 40);
        List<String> steps3 = new ArrayList<>();
        for (int i=0;i<11;i++) steps3.add("insert");
        steps3.add("remove");
        steps3.add("insert"); steps3.add("insert");
        steps3.add("remove");
        runExercise("Exercício 3", t3, seq3, steps3);

        // Exercise 4
        BST t4 = new BST();
        List<Integer> seq4 = Arrays.asList(100,50,150,25,75,125,175,60,90,110,160, 25,75,100,95,100);
        List<String> steps4 = new ArrayList<>();
        for (int i=0;i<11;i++) steps4.add("insert");
        steps4.add("remove"); steps4.add("remove"); steps4.add("remove");
        steps4.add("insert");
        steps4.add("remove");
        runExercise("Exercício 4", t4, seq4, steps4);

        // Exercise 5
        BST t5 = new BST();
        List<Integer> seq5 = Arrays.asList(70,40,90,20,60,80,100,10,30,50,65,85, 10,35,90);
        List<String> steps5 = new ArrayList<>();
        for (int i=0;i<12;i++) steps5.add("insert");
        steps5.add("remove"); steps5.add("insert"); steps5.add("remove");
        runExercise("Exercício 5", t5, seq5, steps5);

        // Exercise 6
        BST t6 = new BST();
        List<Integer> seq6 = Arrays.asList(55,35,75,25,45,65,85,15,95,50,60,70, 25,28,55,45);
        List<String> steps6 = new ArrayList<>();
        for (int i=0;i<12;i++) steps6.add("insert");
        steps6.add("remove"); steps6.add("insert"); steps6.add("remove"); steps6.add("remove");
        runExercise("Exercício 6", t6, seq6, steps6);

        // Exercise 7
        BST t7 = new BST();
        List<Integer> seq7 = Arrays.asList(20,10,30,5,15,25,35,3,13,23,33,37,40, 3,40,12,30);
        List<String> steps7 = new ArrayList<>();
        for (int i=0;i<13;i++) steps7.add("insert");
        steps7.add("remove"); steps7.add("remove");
        steps7.add("insert");
        steps7.add("remove");
        runExercise("Exercício 7", t7, seq7, steps7);

        // Exercise 8
        BST t8 = new BST();
        List<Integer> seq8 = Arrays.asList(90,50,120,30,70,110,150,20,40,60,80,100,130, 20,65,47,49,90);
        List<String> steps8 = new ArrayList<>();
        for (int i=0;i<13;i++) steps8.add("insert");
        steps8.add("remove");
        steps8.add("insert"); steps8.add("insert"); steps8.add("insert");
        steps8.add("remove");
        runExercise("Exercício 8", t8, seq8, steps8);

        // Exercise 9
        BST t9 = new BST();
        List<Integer> seq9 = Arrays.asList(45,25,65,15,35,55,75,10,20,30,40,50,60,70, 10,12,65);
        List<String> steps9 = new ArrayList<>();
        for (int i=0;i<14;i++) steps9.add("insert");
        steps9.add("remove"); steps9.add("insert"); steps9.add("remove");
        runExercise("Exercício 9", t9, seq9, steps9);

        // Exercise 10
        BST t10 = new BST();
        List<Integer> seq10 = Arrays.asList(200,100,300,50,150,250,350,125,175,225,275,325,375,400, 50,90,300);
        List<String> steps10 = new ArrayList<>();
        for (int i=0;i<14;i++) steps10.add("insert");
        steps10.add("remove"); steps10.add("insert"); steps10.add("remove");
        runExercise("Exercício 10", t10, seq10, steps10);

        // Exercise 11
        BST t11 = new BST();
        List<Integer> seq11 = Arrays.asList(70,40,100,20,50,90,120,10,30,45,55,80,95,110,130, 30,35,70,70);
        List<String> steps11 = new ArrayList<>();
        for (int i=0;i<15;i++) steps11.add("insert");
        steps11.add("remove"); steps11.add("insert"); steps11.add("insert");
        steps11.add("remove");
        runExercise("Exercício 11", t11, seq11, steps11);

        // Exercise 12
        BST t12 = new BST();
        List<Integer> seq12 = Arrays.asList(60,30,90,15,45,75,105,10,25,40,50,70,80,100,110, 15,80,110,60,70,85,90);
        List<String> steps12 = new ArrayList<>();
        for (int i=0;i<15;i++) steps12.add("insert");
        steps12.add("remove"); steps12.add("remove"); steps12.add("remove"); steps12.add("remove"); steps12.add("remove");
        steps12.add("insert");
        steps12.add("remove");
        runExercise("Exercício 12", t12, seq12, steps12);

        // Exercise 13
        BST t13 = new BST();
        List<Integer> seq13 = Arrays.asList(85,45,125,25,65,105,145,15,35,55,75,95,115,135,155,50, 25,20,85);
        List<String> steps13 = new ArrayList<>();
        for (int i=0;i<16;i++) steps13.add("insert");
        steps13.add("remove");
        steps13.add("insert");
        steps13.add("remove");
        runExercise("Exercício 13", t13, seq13, steps13);

        // Exercise 14
        BST t14 = new BST();
        List<Integer> seq14 = Arrays.asList(95,55,135,35,75,115,155,25,45,65,85,105,125,145,165,30, 35,65,85,30,32,135);
        List<String> steps14 = new ArrayList<>();
        for (int i=0;i<16;i++) steps14.add("insert");
        steps14.add("remove"); steps14.add("remove"); steps14.add("remove"); steps14.add("remove");
        steps14.add("insert");
        steps14.add("remove");
        runExercise("Exercício 14", t14, seq14, steps14);

        // Exercise 15
        BST t15 = new BST();
        List<Integer> seq15 = Arrays.asList(250,150,350,100,200,300,400,75,125,175,225,275,325,375,425,450,475, 125,130,250);
        List<String> steps15 = new ArrayList<>();
        for (int i=0;i<17;i++) steps15.add("insert");
        steps15.add("remove"); steps15.add("insert"); steps15.add("remove");
        runExercise("Exercício 15", t15, seq15, steps15);
    }
}
