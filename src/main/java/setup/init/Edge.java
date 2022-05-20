package setup.init;

public class Edge {
    private String startNode;
    private String endNode;
    private int weight;
    private int direction;

    public Edge() {
    }

    public Edge(String startNode, String endNode, int weight, int direction) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
        this.direction = direction;
    }

    public String getStartNode() {
        return startNode;
    }

    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "thealgorithms.Edge{" +
                "startNode='" + startNode + '\'' +
                ", endNode='" + endNode + '\'' +
                ", weight=" + weight +
                ", direction=" + direction +
                '}';
    }
}
