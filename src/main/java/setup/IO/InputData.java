package setup.IO;

import setup.init.Edge;
import setup.init.Vertex;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputData {
    List<Edge> listOfEdge = new ArrayList<>();

    public InputData() {
    }

    public InputData(List<Edge> listOfEdge) {
        this.listOfEdge = listOfEdge;
    }

    public List<Edge> getListOfEdge() {
        return listOfEdge;
    }

    public void setListOfEdge(List<Edge> listOfEdge) {
        this.listOfEdge = listOfEdge;
    }

    public void readInputData(String nameInputFile) {
        String URL_INPUT = "src/main/java/input/"+nameInputFile;
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(URL_INPUT);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = bufferedReader.readLine();
            while(line != null) {
                line = bufferedReader.readLine();
                if(line != null) {
                    String[] words = line.split("\t");
                    listOfEdge.add(new Edge(words[0],words[1],Integer.valueOf(words[2]),Integer.valueOf(words[3])));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
                fileInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(InputData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<String> getListOfNameOfVertex() {
        List<String> listOfNameOfVertex = new ArrayList<>();
        for (Edge edge : listOfEdge
        ) {
            if (!listOfNameOfVertex.contains(edge.getStartNode())) {
                listOfNameOfVertex.add(edge.getStartNode());
            }
            if (!listOfNameOfVertex.contains(edge.getEndNode())) {
                listOfNameOfVertex.add(edge.getEndNode());
            }
        }
        return listOfNameOfVertex;
    }

    public Map<String, Integer> getListOfDegreeOfVertex() {
        Map<String, Integer> listOfDegreeOfVertex = new HashMap<>();
        List<String> listOfNameOfVertex = getListOfNameOfVertex();
        for (String nameOfVertex : listOfNameOfVertex
        ) {
            int degreeOfVertex = 0;
            for (Edge edge : listOfEdge
            ) {
                if (nameOfVertex.equals(edge.getStartNode()) || nameOfVertex.equals(edge.getEndNode())) {
                    degreeOfVertex++;
                }
            }
            listOfDegreeOfVertex.put(nameOfVertex,degreeOfVertex);
        }
        return listOfDegreeOfVertex;
    }

    public Map<String, List<String>> getListOfAdjacentVertex() {
        Map<String, List<String>> listOfAdjacentVertex = new HashMap<>();
        List<String> listOfNameOfVertex = getListOfNameOfVertex();
        for (String nameOfVertex : listOfNameOfVertex
             ) {
            List<String> listOfAdjacentVertexOfVertex = new ArrayList<>();
            for (Edge edge : listOfEdge
            ) {
                if (nameOfVertex.equals(edge.getStartNode()) && !listOfAdjacentVertexOfVertex.contains(edge.getEndNode())) {
                    listOfAdjacentVertexOfVertex.add(edge.getEndNode());
                }
                if (nameOfVertex.equals(edge.getEndNode()) && !listOfAdjacentVertexOfVertex.contains(edge.getStartNode())) {
                    listOfAdjacentVertexOfVertex.add(edge.getStartNode());
                }
            }
            listOfAdjacentVertex.put(nameOfVertex,listOfAdjacentVertexOfVertex);
        }
        return listOfAdjacentVertex;
    }

    public Map<String, Integer> getListOfReachabilityOfVertex() {
        Map<String, Integer> listOfReachabilityOfVertex = new HashMap<>();
        List<String> listOfNameOfVertex = getListOfNameOfVertex();
        for (String nameOfVertex : listOfNameOfVertex
        ) {
            Queue<String> queueOfNameOfVertex = new LinkedList<>();
            List<String> listOfNameOfVisitedVertex = new ArrayList<>();
            queueOfNameOfVertex.add(nameOfVertex);
            listOfNameOfVisitedVertex.add(nameOfVertex);
            while (!queueOfNameOfVertex.isEmpty()) {
                String nameOfCurrentVertex = queueOfNameOfVertex.poll();
                for (Edge edge : listOfEdge
                ) {
                    if (nameOfCurrentVertex.equals(edge.getStartNode()) && !listOfNameOfVisitedVertex.contains(edge.getEndNode())) {
                        listOfNameOfVisitedVertex.add(edge.getEndNode());
                        queueOfNameOfVertex.add(edge.getEndNode());
                    }
                    if (edge.getDirection()==0 && nameOfCurrentVertex.equals(edge.getEndNode()) && !listOfNameOfVisitedVertex.contains(edge.getStartNode())) {
                        listOfNameOfVisitedVertex.add(edge.getStartNode());
                        queueOfNameOfVertex.add(edge.getStartNode());
                    }
                }
            }
            listOfReachabilityOfVertex.put(nameOfVertex,listOfNameOfVisitedVertex.size());
        }
        return listOfReachabilityOfVertex;
    }

    public Map<String, List<String>> getListOfReachabilityVertex() {
        Map<String, List<String>> listOfReachabilityVertex = new HashMap<>();
        List<String> listOfNameOfVertex = getListOfNameOfVertex();
        for (String nameOfVertex : listOfNameOfVertex
             ) {
            Queue<String> queueOfNameOfVertex = new LinkedList<>();
            List<String> listOfNameOfVisitedVertex = new ArrayList<>();
            queueOfNameOfVertex.add(nameOfVertex);
            listOfNameOfVisitedVertex.add(nameOfVertex);
            while (!queueOfNameOfVertex.isEmpty()) {
                String nameOfCurrentVertex = queueOfNameOfVertex.poll();
                for (Edge edge : listOfEdge
                ) {
                    if (nameOfCurrentVertex.equals(edge.getEndNode()) && !listOfNameOfVisitedVertex.contains(edge.getStartNode())) {
                        listOfNameOfVisitedVertex.add(edge.getStartNode());
                        queueOfNameOfVertex.add(edge.getStartNode());
                    }
                    if (edge.getDirection()==0 && nameOfCurrentVertex.equals(edge.getStartNode()) && !listOfNameOfVisitedVertex.contains(edge.getEndNode())) {
                        listOfNameOfVisitedVertex.add(edge.getEndNode());
                        queueOfNameOfVertex.add(edge.getEndNode());
                    }
                }
            }
            listOfReachabilityVertex.put(nameOfVertex, listOfNameOfVisitedVertex);
        }
        return listOfReachabilityVertex;
    }

    public List<Vertex> getListOfVertex() {
        List<Vertex> listOfVertex = new ArrayList<>();
        List<String> listOfNameOfVertex = getListOfNameOfVertex();
        Map<String, Integer> listOfDegreeOfVertex = getListOfDegreeOfVertex();
        Map<String, Integer> listOfReachabilityOfVertex = getListOfReachabilityOfVertex();
        for (String nameOfVertex : listOfNameOfVertex
             ) {
            listOfVertex.add(new Vertex(nameOfVertex, listOfDegreeOfVertex.get(nameOfVertex), listOfReachabilityOfVertex.get(nameOfVertex)));
        }
        return listOfVertex;
    }
}
