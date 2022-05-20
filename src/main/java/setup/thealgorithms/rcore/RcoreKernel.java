package setup.thealgorithms.rcore;

import com.aparapi.Kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RcoreKernel extends Kernel {
    Map<String, Integer> listOfReachabilityOfVertex = new HashMap<>();
    Map<String, List<String>> listOfReachabilityVertex = new HashMap<>();
    List<String> listOfNameOfVertexWithCurrentReachability = new ArrayList<>();
    int currentReachability;

    public RcoreKernel() {
    }

    public RcoreKernel(Map<String, Integer> listOfReachabilityOfVertex, Map<String, List<String>> listOfReachabilityVertex, List<String> listOfNameOfVertexWithCurrentReachability, int currentReachability) {
        this.listOfReachabilityOfVertex = listOfReachabilityOfVertex;
        this.listOfReachabilityVertex = listOfReachabilityVertex;
        this.listOfNameOfVertexWithCurrentReachability = listOfNameOfVertexWithCurrentReachability;
        this.currentReachability = currentReachability;
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

    public List<String> getListOfNameOfVertexWithCurrentReachability() {
        return listOfNameOfVertexWithCurrentReachability;
    }

    public void setListOfNameOfVertexWithCurrentReachability(List<String> listOfNameOfVertexWithCurrentReachability) {
        this.listOfNameOfVertexWithCurrentReachability = listOfNameOfVertexWithCurrentReachability;
    }

    public int getCurrentReachability() {
        return currentReachability;
    }

    public void setCurrentReachability(int currentReachability) {
        this.currentReachability = currentReachability;
    }


    public void run() {
        int gid = getGlobalId();
        if (gid < listOfNameOfVertexWithCurrentReachability.size()) {
            String nameOfVertexWithCurrentDegree = listOfNameOfVertexWithCurrentReachability.get(gid);
            List<String> listOfReachabilityVertexByVertex = listOfReachabilityVertex.get(nameOfVertexWithCurrentDegree);
            for (String reachabilityVertexByVertex : listOfReachabilityVertexByVertex
            ) {
                int reachabbilityOfReachabilityVertexByVertex = listOfReachabilityOfVertex.get(reachabilityVertexByVertex);
                if (reachabbilityOfReachabilityVertexByVertex>currentReachability) {
                    reachabbilityOfReachabilityVertexByVertex--;
                    listOfReachabilityOfVertex.replace(reachabilityVertexByVertex, reachabbilityOfReachabilityVertexByVertex);
                    if(reachabbilityOfReachabilityVertexByVertex==currentReachability) {
                        listOfNameOfVertexWithCurrentReachability.add(reachabilityVertexByVertex);
                    }
                    if (reachabbilityOfReachabilityVertexByVertex<currentReachability) {
                        reachabbilityOfReachabilityVertexByVertex++;
                        listOfReachabilityOfVertex.replace(reachabilityVertexByVertex, reachabbilityOfReachabilityVertexByVertex);
                    }
                }
            }
        }
    }

    public int getTotalVisitedVertexWithCurrentReachability() {
        return listOfNameOfVertexWithCurrentReachability.size();
    }
}
