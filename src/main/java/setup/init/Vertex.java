package setup.init;

public class Vertex {
    private String vertex;
    private int degree;
    private int reachability;

    public Vertex() {
    }

    public Vertex(String vertex, int degree, int reachability) {
        this.vertex = vertex;
        this.degree = degree;
        this.reachability = reachability;
    }

    public String getVertex() {
        return vertex;
    }

    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getReachability() {
        return reachability;
    }

    public void setReachability(int reachability) {
        this.reachability = reachability;
    }

    @Override
    public String toString() {
        return "thealgorithms.Vertex{" +
                "vertex='" + vertex + '\'' +
                ", degree=" + degree +
                ", reachability=" + reachability +
                '}';
    }
}
