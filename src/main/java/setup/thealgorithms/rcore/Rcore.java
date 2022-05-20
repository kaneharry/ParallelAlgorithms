package setup.thealgorithms.rcore;

import com.aparapi.Range;
import setup.thealgorithms.kcore.KcoreKernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rcore {
    List<String> listOfNameOfVertex = new ArrayList<>();
    Map<String, Integer> listOfReachabilityOfVertex = new HashMap<>();
    Map<String, List<String>> listOfReachabilityVertex = new HashMap<>();

    public Rcore() {
    }

    public Rcore(List<String> listOfNameOfVertex, Map<String, Integer> listOfReachabilityOfVertex, Map<String, List<String>> listOfReachabilityVertex) {
        this.listOfNameOfVertex = listOfNameOfVertex;
        this.listOfReachabilityOfVertex = listOfReachabilityOfVertex;
        this.listOfReachabilityVertex = listOfReachabilityVertex;
    }

    public List<String> getListOfNameOfVertex() {
        return listOfNameOfVertex;
    }

    public void setListOfNameOfVertex(List<String> listOfNameOfVertex) {
        this.listOfNameOfVertex = listOfNameOfVertex;
    }

    public Map<String, Integer> getListOfReachabilityOfVertex() {
        return listOfReachabilityOfVertex;
    }

    public void setListOfReachabilityOfVertex(Map<String, Integer> listOfReachabilityOfVertex) {
        this.listOfReachabilityOfVertex = listOfReachabilityOfVertex;
    }

    public Map<String, List<String>> getListOfReachabilityVertex() {
        return listOfReachabilityVertex;
    }

    public void setListOfReachabilityVertex(Map<String, List<String>> listOfReachabilityVertex) {
        this.listOfReachabilityVertex = listOfReachabilityVertex;
    }

    public void compute() {
        int totalVisitedVertex = 0;
        int currentReachability = 0;
        while (totalVisitedVertex<listOfNameOfVertex.size()) {
            List<String> listOfNameOfVertexWithCurrentReachability = new ArrayList<>();
            for (String nameOfVertex : listOfNameOfVertex
            ) {
                if(listOfReachabilityOfVertex.get(nameOfVertex)==currentReachability) {
                    listOfNameOfVertexWithCurrentReachability.add(nameOfVertex);
                }
            }
            if (listOfNameOfVertexWithCurrentReachability.size()>0) {
                Range range = Range.create(listOfNameOfVertex.size());
                RcoreKernel rcoreKernel = new RcoreKernel(listOfReachabilityOfVertex,
                        listOfReachabilityVertex,
                        listOfNameOfVertexWithCurrentReachability,
                        currentReachability);
                rcoreKernel.execute(range);
                listOfReachabilityOfVertex = rcoreKernel.getListOfReachabilityOfVertex();
                totalVisitedVertex += rcoreKernel.getTotalVisitedVertexWithCurrentReachability();
                rcoreKernel.dispose();
                listOfNameOfVertexWithCurrentReachability.clear();
            }
            currentReachability++;
        }
    }
}
