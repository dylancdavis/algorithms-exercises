import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static Integer[] leastCosts;
    public static boolean[] isInX;
    public static ArrayList<Integer[]>[] g;

    public static final int[] verticesToFind = {7,37,59,82,99,115,133,165,188,197};

    public static void main(String[] args) {

        final String FILE_NAME = "dijkstraData.txt";
        final int SIZE = 200;
        final int MAX = 1000000;

        // Initializations
        leastCosts = new Integer[SIZE];
        leastCosts[0] = 0;
        for (int i=1;i<leastCosts.length;i++) { leastCosts[i] = MAX; }

        isInX = new boolean[SIZE];
        isInX[0] = true;
        for (int i = 1; i< isInX.length; i++) { isInX[i] = false; }

        g = new ArrayList[SIZE];
        for (int i=0;i<g.length;i++) { g[i] = new ArrayList<>(); }

        // Read File
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

            // Build Graph
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] pairs = line.split("\t|\s");
                int vertex = Integer.parseInt(pairs[0]);
                //System.out.println("Vertex " + vertex + ":");
                for (int i=1;i<pairs.length;i++) {
                    String[] pairItems = pairs[i].split(",");
                    Integer[] pairToAdd = new Integer[2];
                    pairToAdd[0] = Integer.valueOf(pairItems[0]);
                    pairToAdd[1] = Integer.valueOf(pairItems[1]);
                    //System.out.print("  " + Arrays.toString(pairToAdd));
                    g[vertex-1].add(pairToAdd);
                }
            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("verticesRemain() = " + verticesRemain());

        // Loop while another vertex exists
        while (verticesRemain()) {

            // Find greedy criterion
            Integer[] tailCostPair = null;
            int currentGreedyCriterion = MAX;

            for (int i=0;i<g.length;i++) {
                if (isInX[i]) {
                    Integer[] cheapestPair = null;
                    for (Integer[] currentPair : g[i]) {
                        if (!isInX[currentPair[0]-1]) {
                            if (cheapestPair == null || currentPair[1] < cheapestPair[1])
                                cheapestPair = currentPair;
                        }
                    }
                    if (cheapestPair == null) {
                        continue;
                    }
                    int thisCost = leastCosts[i] + cheapestPair[1];
                    if (thisCost < currentGreedyCriterion) {
                        currentGreedyCriterion = thisCost;
                        tailCostPair = cheapestPair;
                    }
                }
            }

            // Use greedy criterion to update cost
            leastCosts[tailCostPair[0]-1] = currentGreedyCriterion;
            // And to update what's in X
            isInX[tailCostPair[0]-1] = true;
        }

        System.out.println("leastCosts = " + Arrays.toString(leastCosts));

        for (int i=0;i<verticesToFind.length;i++) {
            int vertex = verticesToFind[i];
            System.out.print(leastCosts[vertex-1]+",");
        }

        // Source is first vertex
        // TODO loop through edges, finding min, linear scan
        // Guess we'll just loop through all in X
        // Ignore those with tail in X
        // And any with tail in V-X, compute cost + leastCost[i-1]

    }

    public static boolean verticesRemain() {
        for (int i = 0; i< isInX.length; i++) {
            if (!isInX[i])
                return true;
        }
        return false;
    }

}