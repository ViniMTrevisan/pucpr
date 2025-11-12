import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.PrintWriter;

public class HashComparison {
    public static void main(String[] args) throws IOException {
        String inputFilePath = "female_names.txt";
        if (args.length > 0) inputFilePath = args[0];

        List<String> nameList = Files.readAllLines(Paths.get(inputFilePath)).stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());

        System.out.println("Nomes lidos: " + nameList.size() + " do arquivo: " + inputFilePath);

        final int tableCapacity = 32;

        AbstractHashTable table1 = new HashTableHash1(tableCapacity);
        AbstractHashTable table2 = new HashTableHash2(tableCapacity);

        long startTimeNano = System.nanoTime();
        for (String name : nameList) table1.insert(name);
        long insertDurationNanoTable1 = System.nanoTime() - startTimeNano;

        startTimeNano = System.nanoTime();
        for (String name : nameList) table2.insert(name);
        long insertDurationNanoTable2 = System.nanoTime() - startTimeNano;

        startTimeNano = System.nanoTime();
        int foundCountTable1 = 0;
        for (String name : nameList) if (table1.contains(name)) foundCountTable1++;
        long searchDurationNanoTable1 = System.nanoTime() - startTimeNano;

        startTimeNano = System.nanoTime();
        int foundCountTable2 = 0;
        for (String name : nameList) if (table2.contains(name)) foundCountTable2++;
        long searchDurationNanoTable2 = System.nanoTime() - startTimeNano;

        StringBuilder sb = new StringBuilder();
    sb.append("--- Relatório de Comparação de Funções Hash\n");
    sb.append(String.format("Nomes lidos: %d\n", nameList.size()));
    sb.append(String.format("Capacidade da tabela: %d\n\n", tableCapacity));

    sb.append("Tabela 1 (Hash1 - soma dos caracteres):\n");
    sb.append(String.format("  Eventos de colisão: %d\n", table1.getCollisionEvents()));
    sb.append(String.format("  Comparações de colisão (soma dos tamanhos dos buckets ao inserir): %d\n", table1.getCollisionComparisons()));
    sb.append(String.format("  Tempo de inserção (ms): %.3f\n", insertDurationNanoTable1 / 1_000_000.0));
    sb.append(String.format("  Tempo de busca (ms): %.3f\n", searchDurationNanoTable1 / 1_000_000.0));
    sb.append(String.format("  Encontrados nas buscas: %d\n", foundCountTable1));
    sb.append("  Distribuição (índice:quantidade):\n");
        int[] distribution1 = table1.getDistribution();
        for (int i = 0; i < distribution1.length; i++) sb.append(String.format("    %2d: %d\n", i, distribution1[i]));
        sb.append("\n");

    sb.append("Tabela 2 (Hash2 - djb2):\n");
    sb.append(String.format("  Eventos de colisão: %d\n", table2.getCollisionEvents()));
    sb.append(String.format("  Comparações de colisão (soma dos tamanhos dos buckets ao inserir): %d\n", table2.getCollisionComparisons()));
    sb.append(String.format("  Tempo de inserção (ms): %.3f\n", insertDurationNanoTable2 / 1_000_000.0));
    sb.append(String.format("  Tempo de busca (ms): %.3f\n", searchDurationNanoTable2 / 1_000_000.0));
    sb.append(String.format("  Encontrados nas buscas: %d\n", foundCountTable2));
    sb.append("  Distribuição (índice:quantidade):\n");
        int[] distribution2 = table2.getDistribution();
        for (int i = 0; i < distribution2.length; i++) sb.append(String.format("    %2d: %d\n", i, distribution2[i]));
        sb.append("\n");

        String report = sb.toString();
        System.out.println(report);

        String out = "hash_report.txt";
        try (PrintWriter pw = new PrintWriter(out)) {
            pw.print(report);
        }
        System.out.println("Relatório gravado em: " + out);
    }
}
