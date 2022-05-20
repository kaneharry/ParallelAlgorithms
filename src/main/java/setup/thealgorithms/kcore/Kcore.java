package setup.thealgorithms.kcore;

import com.aparapi.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kcore {
    List<String> listOfNameOfVertex = new ArrayList<>();
    Map<String, Integer> listOfDegreeOfVertex = new HashMap<>();
    Map<String, List<String>> listOfAdjacentVertex = new HashMap<>();

    public Kcore() {
    }

    public Kcore(List<String> listOfNameOfVertex, Map<String, Integer> listOfDegreeOfVertex, Map<String, List<String>> listOfAdjacentVertex) {
        this.listOfNameOfVertex = listOfNameOfVertex;
        this.listOfDegreeOfVertex = listOfDegreeOfVertex;
        this.listOfAdjacentVertex = listOfAdjacentVertex;
    }

    public List<String> getListOfNameOfVertex() {
        return listOfNameOfVertex;
    }

    public void setListOfNameOfVertex(List<String> listOfNameOfVertex) {
        this.listOfNameOfVertex = listOfNameOfVertex;
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

    public void compute() {
        int totalVisitedVertex = 0;
        int currentDegree = 0;
        while (totalVisitedVertex<listOfNameOfVertex.size()) {
            List<String> listOfNameOfVertexWithCurrentDegree = new ArrayList<>();
            for (String nameOfVertex : listOfNameOfVertex
                 ) {
                if(listOfDegreeOfVertex.get(nameOfVertex)==currentDegree) {
                    listOfNameOfVertexWithCurrentDegree.add(nameOfVertex);
                }
            }
            if (listOfNameOfVertexWithCurrentDegree.size()>0) {
                Range range = Range.create(listOfNameOfVertex.size());
                KcoreKernel kcoreKernel = new KcoreKernel(
                                                    listOfDegreeOfVertex,
                                                    listOfAdjacentVertex,
                                                    listOfNameOfVertexWithCurrentDegree,
                                                    currentDegree
                                                    );
                kcoreKernel.execute(range);
                listOfDegreeOfVertex = kcoreKernel.getListOfDegreeOfVertex();
                totalVisitedVertex += kcoreKernel.getTotalVisitedVertexWithCurrentDegree();
                kcoreKernel.dispose();
                listOfNameOfVertexWithCurrentDegree.clear();
            }
            currentDegree++;
        }
    }
}
