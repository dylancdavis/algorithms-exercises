import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Vertex {

    private Integer value;

    private ArrayList<Integer> edges ;

    public Vertex(Integer value, ArrayList<Integer> edges) {
        this.value = value;
        this.edges = edges;
    }

    public void merge(Vertex v) {
        // TODO: write code for merging
        v.removeEdge(this.value); // remove this node for self loop
        this.edges.addAll(v.getEdges()); // add remaining edges
        removeEdge(v.value); // and remove intial merge edge
    }

    public void removeEdge(Integer i) {
        while (this.edges.contains(i)) {
            this.edges.remove(i);
        }
    }

    // Replaces all occurances of one edge in a vertex with another
    // Called when a merge occurs to provide it with new values
    public void updateEdge(Integer prevInt, Integer newInt) {
        while (edges.contains(prevInt)) {
            edges.add(newInt);
            edges.remove(prevInt);
        }
    }

    public Integer getValue() {
        return value;
    }

    public ArrayList<Integer> getEdges() {
        return edges;
    }

    public String toString() {
        return "(" + this.value + ") " + edges.toString();
    }

}
