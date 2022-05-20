package setup.thealgorithms.kcore;

import com.aparapi.Kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KcoreKernel extends Kernel {
    Map<String, Integer> listOfDegreeOfVertex = new HashMap<>();
    Map<String, List<String>> listOfAdjacentVertex = new HashMap<>();
    List<String> listOfNameOfVertexWithCurrentDegree = new ArrayList<>();
    int currentDegree;

    public KcoreKernel() {
    }

    public KcoreKernel(Map<String, Integer> listOfDegreeOfVertex, Map<String, List<String>> listOfAdjacentVertex, List<String> listOfNameOfVertexWithCurrentDegree, int currentDegree) {
        this.listOfDegreeOfVertex = listOfDegreeOfVertex;
        this.listOfAdjacentVertex = listOfAdjacentVertex;
        this.listOfNameOfVertexWithCurrentDegree = listOfNameOfVertexWithCurrentDegree;
        this.currentDegree = currentDegree;
    }

    public Map<String, Integer> getListOfDegreeOfVertex() {
        return listOfDegreeOfVertex;
    }

    public void setListOfDegreeOfVertex(Map<String, Integer> listOfDegreeOfVertex) {
        this.listOfDegreeOfVertex = listOfDegreeOfVertex;
    }

    public Map<String, List<String>> getListOfAdjacentVertex() {
        return listOfAdjacentVertex;
    }

    public void setListOfAdjacentVertex(Map<String, List<String>> listOfAdjacentVertex) {
        this.listOfAdjacentVertex = listOfAdjacentVertex;
    }

    public List<String> getListOfNameOfVertexWithCurrentDegree() {
        return listOfNameOfVertexWithCurrentDegree;
    }

    public void setListOfNameOfVertexWithCurrentDegree(List<String> listOfNameOfVertexWithCurrentDegree) {
        this.listOfNameOfVertexWithCurrentDegree = listOfNameOfVertexWithCurrentDegree;
    }

    public int getCurrentDegree() {
        return currentDegree;
    }

    public void setCurrentDegree(int currentDegree) {
        this.currentDegree = currentDegree;
    }

    public void compareAndSet(String nameOfVertex,int oldValue, int newValue) {
        synchronized (this) {
            if(listOfDegreeOfVertex.get(nameOfVertex) == oldValue) {
                listOfDegreeOfVertex.replace(nameOfVertex, newValue);
            }
        }
    }

    public void run() {
        int gid = getGlobalId();
        if (gid < listOfNameOfVertexWithCurrentDegree.size()) {
            String nameOfVertexWithCurrentDegree = listOfNameOfVertexWithCurrentDegree.get(gid);
            List<String> listOfAdjacentVertexByVertex = listOfAdjacentVertex.get(nameOfVertexWithCurrentDegree);
            for (String adjacentVertexByVertex : listOfAdjacentVertexByVertex
                 ) {
                int degreeOfAdjacentVertexByVertex = listOfDegreeOfVertex.get(adjacentVertexByVertex);
                if (degreeOfAdjacentVertexByVertex>currentDegree) {
                    degreeOfAdjacentVertexByVertex--;
                    listOfDegreeOfVertex.replace(adjacentVertexByVertex, degreeOfAdjacentVertexByVertex);
                    if(listOfDegreeOfVertex.get(adjacentVertexByVertex)==currentDegree) {
                        listOfNameOfVertexWithCurrentDegree.add(adjacentVertexByVertex);
                    }
                    degreeOfAdjacentVertexByVertex = listOfDegreeOfVertex.get(adjacentVertexByVertex);
                    if (degreeOfAdjacentVertexByVertex<currentDegree) {
                        degreeOfAdjacentVertexByVertex++;
                        listOfDegreeOfVertex.replace(adjacentVertexByVertex, degreeOfAdjacentVertexByVertex);
                    }
                }
            }
        }
    }

    public int getTotalVisitedVertexWithCurrentDegree() {
        return listOfNameOfVertexWithCurrentDegree.size();
    }
}
