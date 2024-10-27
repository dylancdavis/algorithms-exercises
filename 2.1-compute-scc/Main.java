import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Main {

    static boolean[] explored;
    static boolean[] counted;
    static ArrayList<Integer>[] gRev;
    static ArrayList<Integer>[] g;
    static ArrayList<Integer>[] gFin;
    static int[] finishingTimes;
    static int finishingTime = 1;

    static Stack<Integer> toExplore = new Stack<>();
    static int currentSCCSize;
    static int[] largestSizes = {0,0,0,0,0};

    public static void main(String[] args) {

        final String FILE_NAME = "SCC.txt";

        try {
            BufferedReader max = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            int datasetSize = 0;

            // Determine dataset size.
            while ((line = max.readLine()) != null) {
                if (line.isEmpty())
                    continue;
                String[] sArr = line.split("\s");
                int firstNum = Integer.valueOf(sArr[0]);
                int secondNum = Integer.valueOf(sArr[1]);
                if (firstNum > datasetSize)
                    datasetSize = firstNum;
                if (secondNum > datasetSize)
                    datasetSize = secondNum;
            }
            max.close();

            System.out.println("datasetSize = " + datasetSize);

            // Initialize Arrays
            g = new ArrayList[datasetSize];
            gRev = new ArrayList[datasetSize];
            gFin = new ArrayList[datasetSize];
            finishingTimes = new int[datasetSize];

            for (int i=0;i<g.length;i++) {
                g[i] = new ArrayList<>();
                gRev[i] = new ArrayList<>();
                gFin[i] = new ArrayList<>();
            }
            for (int i=0;i<finishingTimes.length;i++) {
                finishingTimes[i] = 0;
            }

            // Populate graph and reverse graph
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty())
                    continue;
                String[] sArr = line.split("\s");
                int firstNum = Integer.valueOf(sArr[0]);
                int secondNum = Integer.valueOf(sArr[1]);
                g[firstNum-1].add(secondNum);
                gRev[secondNum-1].add(firstNum);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        System.out.println("Arrays.toString(g) = " + Arrays.toString(g));
//        System.out.println("Arrays.toString(gRev) = " + Arrays.toString(gRev));

        System.out.println("g length = " + g.length);
        System.out.println("gRev.length = " + gRev.length);

        // Initialize the explored marker array
        explored = new boolean[gRev.length];
        for (int i=0;i<explored.length;i++) {
            explored[i] = false;
        }

        System.out.println("Boolean array populated");

        System.out.println("gRev last = " + gRev[gRev.length-1]);
        System.out.println("g last = " + g[g.length-1]);

        // First pass. Start with m.
        toExplore.push(gRev.length);

        for (int i=gRev.length;i>0;i--) {
            if (!explored[i-1]) {
                toExplore.push(i);
                while (!toExplore.empty()) {
                    dfsFinish();
                }
            }
        }

        // Check that nodes are explored.
        boolean allExplored = true;
        for (int i=0;i<explored.length;i++) {
            if (!explored[i]) {
                throw new RuntimeException("Unexplored node " + i);
            }
        }

//        System.out.println("finishingTimes = " + Arrays.toString(finishingTimes));

        // Populate graph with finishing times
        for (int i=0;i<g.length;i++) {
            int newIndex = finishingTimes[i]-1;
            for (Integer n : g[i]) {
                gFin[newIndex].add(finishingTimes[n-1]);
            }
        }

//        System.out.println("gFin = " + Arrays.toString(gFin));

        // Reset explored
        for (int i=0;i<explored.length;i++) {
            explored[i] = false;
        }

        int numSCCs = 0;

        // Initialize counting
        counted = new boolean[gFin.length];
        for (int i=0;i<counted.length;i++) {
            counted[i] = false;
        }

        // Find SCCs
        for (int i=gFin.length;i>0;i--) {
            if (!explored[i-1]) {
                numSCCs++;
                toExplore.push(i);
                currentSCCSize = 0;
                while (!toExplore.empty()) {
                    dfs();
                }
                addToSizes(currentSCCSize);
            }
        }

        System.out.println("numSCCs = " + numSCCs);
        System.out.println("largestSizes = " + Arrays.toString(largestSizes));
    }

    public static void dfsFinish() {
        int i = toExplore.peek();

        if (finishingTimes[i-1] != 0) {
            toExplore.pop();
            return;
        }

        if (!explored[i-1]) {
            explored[i-1] = true;
        }
        boolean childrenExplored = true;
        for (Integer n : gRev[i-1]) {
            if (!explored[n-1]) {
                toExplore.push(n);
                childrenExplored = false;
            }
        }
        if (childrenExplored) {
            toExplore.pop();
            finishingTimes[i-1] = finishingTime;
            System.out.println("finishingTime of "+i+": " + finishingTime);
            finishingTime++;
        }
    }

    public static void dfs() {
        int i = toExplore.peek();

        if (!explored[i-1]) {
            explored[i-1] = true;
        }

        boolean childrenExplored = true;
        for (Integer n : gFin[i-1]) {
            if (!explored[n-1]) {
                toExplore.push(n);
                childrenExplored = false;
            }
        }
        if (childrenExplored) {
            if (!counted[i-1]) {
                currentSCCSize++;
                counted[i-1] = true;
            }
            toExplore.pop();
        }
    }

    public static void addToSizes(int n) {
        for (int i=0;i<largestSizes.length;i++) {
            if (n > largestSizes[i]) {
                addToSizes(largestSizes[i]);
                largestSizes[i] = n;
                return;
            }
        }
        return;
    }


}