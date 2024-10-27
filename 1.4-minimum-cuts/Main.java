import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        final String FILE_NAME = "graph.txt";

        // TODO: Create Data Structures for graph
        // TODO: Create FileReader and read in file

        ArrayList<int[]> graphValues = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            // Build Graph values arraylist
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split("\\t");
                int[] values = new int[strings.length];
                for (int i=0; i<strings.length; i++) {
                    values[i] = Integer.parseInt(strings[i]);
                }
                graphValues.add(values);
            }
            reader.close();
        } catch (IOException e) { throw new RuntimeException(e); }

        // Calculate base trials and minimum cut size
        int trials = (int) (graphValues.size() * Math.log(graphValues.size()));
        System.out.println("trials = " + trials);
        int mincuts = graphValues.size();
        System.out.println("mincuts = " + mincuts);

        // Run Trials
        for (int i=0; i<trials; i++) {

            Graph g = new Graph(graphValues);

            // Handle graph merging
//                System.out.println("--------------Starting Graph-------------");
//                g.printVertices();
//                g.printEdges();
            g.mergeAll();
            System.out.println("Trial " + (i+1) + ": " + g.numEdges() + " mincuts.");
            if (g.numEdges() < mincuts) {
                mincuts = g.numEdges();
            }
        }

        System.out.println("mincuts = " + mincuts);

    }
}