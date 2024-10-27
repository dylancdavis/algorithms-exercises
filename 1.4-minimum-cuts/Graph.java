import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Graph {

    private ArrayList<Vertex> vertices;
    public ArrayList<int[]> edges;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public Graph(ArrayList<Vertex> vertices, ArrayList<int[]> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public Graph(ArrayList<int[]> graphValues) {

        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();

        for (int[] val : graphValues) {
            int valNum = val[0];
            ArrayList<Integer> edges = new ArrayList<>();
            for (int j=1; j<val.length;j++) {
                int edgeNum = val[j];
                edges.add(edgeNum);
                if (edgeNum > valNum)
                    addEdge(new int[]{valNum, edgeNum});
            }
            addVertex(new Vertex(valNum,edges));
        }
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addEdge(int[] e) {
        if (e.length != 2) {
            System.out.println("Expected edge with two numbers, given: " + e.toString());
            return;
        }
        edges.add(e);
    }

    public int[] getRandomEdge() {
        Random rand = new Random();
        return edges.get(rand.nextInt(edges.size()));
    }

    public Vertex getVertexByValue (int value) {
        for (Vertex v : vertices) {
            if (v.getValue() == value) {
                return v;
            }
            if (v.getValue() > value) {
                System.out.println("No vertex with value " + value + " found.");
                break;
            }
        }
        return null;
    }

    public void mergeEdge(int[] e) {
        // Remove matching edges
        for (int i=0; i<edges.size(); i++) {
            if (edges.get(i)[0] == e[0] && edges.get(i)[1] == e[1]) {
                edges.remove(i);
                i--;
            }

        }

        // but there could be duplicates.
        // replace instances in edges
        for (int[] edge : edges) {
            if (edge[0] == e[1]) {
                edge[0] = e[0];
            } else if (edge[1] == e[1]) {
                edge[1] = e[0];
                // May need to swap to preserve order
                if (edge[1] < edge[0]) {
                    int temp = edge[0];
                    edge[0] = edge[1];
                    edge[1] = temp;
                }
            }
        }
//        System.out.println("Merging edge: "  + Arrays.toString(e));
        Vertex v1 = getVertexByValue(e[0]);
        Vertex v2 = getVertexByValue(e[1]);
        v1.merge(v2);
        vertices.remove(v2);
        for (Vertex v : vertices) {
            v.updateEdge(e[1],e[0]);
        }
    }

    public void mergeRandomEdge() {
        mergeEdge(getRandomEdge());
    }

    public void mergeAll() {
        while (vertices.size() > 2) {
            mergeRandomEdge();
        }
    }

    public void printVertices() {
        System.out.println("Graph Vertices:");
        for (Vertex v : vertices) {
            System.out.println(v.toString());
        }
    }

    public void printEdges() {
        System.out.println("Graph Edges:");
        for (int[] e : edges) {
            System.out.println(Arrays.toString(e));
        }
    }

    public int numEdges() {
        return edges.size();
    }

    public int numVertices() {
        return vertices.size();
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<int[]> getEdges() {
        return edges;
    }
}
