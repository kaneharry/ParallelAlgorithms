package setup.IO;

import setup.helper.MapComparator;
import setup.init.Vertex;
import setup.thealgorithms.kcore.Kcore;
import setup.thealgorithms.rcore.Rcore;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputData {
    List<Vertex> listOfVertex = new ArrayList<>();

    public OutputData() {
    }

    public OutputData(List<Vertex> listOfVertex) {
        this.listOfVertex = listOfVertex;
    }

    public void updateResultKCore(Map<String, Integer> listOfDegreeOfVertex) {
        for (Vertex vertex : listOfVertex
             ) {
            vertex.setDegree(listOfDegreeOfVertex.get(vertex.getVertex()));
        }
    }

    public void updateResultRCore(Map<String, Integer> listOfReachabilityOfVertex) {
        for (Vertex vertex : listOfVertex
        ) {
            vertex.setReachability(listOfReachabilityOfVertex.get(vertex.getVertex()));
        }
    }
    public String resultKCore() {
        String resultText ="";
        Map<String, Integer> listOfDegreeOfVertex = new HashMap<>();
        for (Vertex vertex : listOfVertex
        ) {
            listOfDegreeOfVertex.put(vertex.getVertex(), vertex.getDegree());
        }
        Map<String, Integer> resultKCore = MapComparator.sortByValue(listOfDegreeOfVertex);
        for (Map.Entry<String, Integer> entry : resultKCore.entrySet()
        ) {
            resultText = resultText+entry.getKey()+"\t"+entry.getValue()+"\n";
        }
        return resultText;
    }

    public String resultRCore() {
        String resultText ="";
        Map<String, Integer> listOfReachabilityOfVertex = new HashMap<>();
        for (Vertex vertex : listOfVertex
        ) {
            listOfReachabilityOfVertex.put(vertex.getVertex(), vertex.getReachability());
        }
        Map<String, Integer> resultRCore = MapComparator.sortByValue(listOfReachabilityOfVertex);
        for (Map.Entry<String, Integer> entry : resultRCore.entrySet()
        ) {
            resultText = resultText+entry.getKey()+"\t"+entry.getValue()+"\n";
        }
        return resultText;
    }

    public void writeOutputData (String nameInputFile, String chooseAlgorithm, String chooseRunMode) {
        String URL_OUTPUT = "src/main/java/output/[Output "+ chooseAlgorithm+ " "+chooseRunMode +"] " +nameInputFile;
        String text = null;
        if (chooseAlgorithm.equals("K core")) {
            text = "node\tkcore\n"+ resultKCore();
        } else if (chooseAlgorithm.equals("R core")) {
            text = "node\trcore\n"+ resultRCore();
        }
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = new FileOutputStream(URL_OUTPUT);
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(text);
            bufferedWriter.close();
        }
        catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
