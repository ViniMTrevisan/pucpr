import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    private static final String DATA_DIR = "conjuntosDeDados";
    private static final String[] FILES = {
        "aleatorio_100.csv",
        "aleatorio_1000.csv",
        "aleatorio_10000.csv",
        "crescente_100.csv",
        "crescente_1000.csv",
        "crescente_10000.csv",
        "decrescente_100.csv",
        "decrescente_1000.csv",
        "decrescente_10000.csv"
    };

    public static void main(String[] args) throws Exception {
        String base = System.getProperty("user.dir");
        Path dataDir = Paths.get(base, DATA_DIR);
        if (args.length > 0) dataDir = Paths.get(args[0]);

        System.out.println("Using data directory: " + dataDir.toAbsolutePath());

        List<String[]> results = new ArrayList<>();
        results.add(new String[]{"file","algorithm","nanos"});

        System.out.printf("%-25s %-12s %12s%n", "File", "Algorithm", "Time (ns)");
        System.out.println("---------------------------------------------------------");

        for (String f : FILES) {
            Path p = dataDir.resolve(f);
            if (!Files.exists(p)) {
                System.err.println("File not found: " + p);
                continue;
            }

            int[] data = readCsv(p);

            // Bubble
            int[] arr1 = Sorting.copy(data);
            long t0 = System.nanoTime();
            Sorting.bubbleSort(arr1);
            long t1 = System.nanoTime();
            long bubbleTime = t1 - t0;
            System.out.printf("%-25s %-12s %12d%n", f, "Bubble", bubbleTime);
            results.add(new String[]{f, "Bubble", String.valueOf(bubbleTime)});

            // Insertion
            int[] arr2 = Sorting.copy(data);
            t0 = System.nanoTime();
            Sorting.insertionSort(arr2);
            t1 = System.nanoTime();
            long insertionTime = t1 - t0;
            System.out.printf("%-25s %-12s %12d%n", f, "Insertion", insertionTime);
            results.add(new String[]{f, "Insertion", String.valueOf(insertionTime)});

            // Quick
            int[] arr3 = Sorting.copy(data);
            t0 = System.nanoTime();
            Sorting.quickSort(arr3);
            t1 = System.nanoTime();
            long quickTime = t1 - t0;
            System.out.printf("%-25s %-12s %12d%n", f, "Quick", quickTime);
            results.add(new String[]{f, "Quick", String.valueOf(quickTime)});

            // Basic sanity check (ensure sorted)
            if (!isSorted(arr3)) {
                System.err.println("[Warning] QuickSort result not sorted for " + f);
            }
        }

        // write results.csv
        Path out = Paths.get("results.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(out)) {
            for (String[] r : results) {
                bw.write(String.join(",", r));
                bw.newLine();
            }
        }

        System.out.println("\nResults saved to results.csv");
    }

    private static int[] readCsv(Path p) throws IOException {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(p)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                // support comma-separated values in a line or single value per line
                String[] parts = line.split(",");
                for (String s : parts) {
                    s = s.trim();
                    if (s.isEmpty()) continue;
                    try {
                        list.add(Integer.parseInt(s));
                    } catch (NumberFormatException ex) {
                        // ignore non-integer tokens
                    }
                }
            }
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i-1] > a[i]) return false;
        return true;
    }
}
