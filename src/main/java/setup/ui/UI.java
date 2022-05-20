package setup.ui;

import setup.IO.InputData;
import setup.IO.OutputData;
import setup.helper.MapComparator;
import setup.init.Vertex;
import setup.thealgorithms.kcore.Kcore;
import setup.thealgorithms.rcore.Rcore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class UI {
    public static void main(String[] args) throws IOException {
        List<String> listAlgorithm = new ArrayList<>(Arrays.asList("1","K core","2","R core"));
        List<String> listRunMode = new ArrayList<>(Arrays.asList("1","GPU","2","CPU"));
        String chooseAlgorithm;
        String chooseRunMode;
        String nameOfInputFile;
        String resultText="";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("---Parallel Algorithms---");
            System.out.println("1. K core\n2. R core");
            System.out.println("Execute the command by entering its number!");
            System.out.print("Type command:");
            chooseAlgorithm = br.readLine();
        } while (!listAlgorithm.contains(chooseAlgorithm));

        do {
            System.out.println("Choose run mode:");
            System.out.println("1. GPU\n2. CPU");
            System.out.print("Type command:");
            chooseRunMode = br.readLine();
        } while (!listRunMode.contains(chooseRunMode));

        if (chooseAlgorithm.equals("1") || chooseAlgorithm.equals("K core")) {
            chooseAlgorithm = "K core";
        } else if (chooseAlgorithm.equals("2") || chooseAlgorithm.equals("R core")) {
            chooseAlgorithm = "R core";
        }
        if (chooseRunMode.equals("1") || chooseRunMode.equals("GPU")) {
            chooseRunMode = "GPU";
        } else if (chooseRunMode.equals("2") || chooseRunMode.equals("CPU")) {
            chooseRunMode = "CPU";
        }

        System.out.println("Network file (Press enter = 'net.txt' : String):");
        System.out.print("Type command:");
        nameOfInputFile = br.readLine();

        InputData inputData = new InputData();
        inputData.readInputData(nameOfInputFile);

        Kcore kcore = kcore = new Kcore(inputData.getListOfNameOfVertex(),
                inputData.getListOfDegreeOfVertex(),
                inputData.getListOfAdjacentVertex()
        );
        Rcore rcore = rcore = new Rcore(inputData.getListOfNameOfVertex(),
                inputData.getListOfReachabilityOfVertex(),
                inputData.getListOfReachabilityVertex()
        );

        if (chooseRunMode.equals("GPU")) {
            System.setProperty("com.aparapi", "GPU");
        } else if (chooseRunMode.equals("CPU")) {
            System.setProperty("com.aparapi", "JTP");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String timeStart = simpleDateFormat.format(new Date());
        System.out.println("time start: " + timeStart);

        if (chooseAlgorithm.equals("K core")) {
            kcore.compute();
//            Map<String, Integer> resultKCore = MapComparator.sortByValue(kcore.getListOfDegreeOfVertex());
//            for (Map.Entry<String, Integer> entry : resultKCore.entrySet()
//            ) {
//                System.out.println(entry.getKey() + " "+ entry.getValue());
//            }

        } else if (chooseAlgorithm.equals("R core")) {
            rcore.compute();
//            Map<String, Integer> resultRCore = MapComparator.sortByValue(rcore.getListOfReachabilityOfVertex());
//            for (Map.Entry<String, Integer> entry : resultMap0.entrySet()
//            ) {
//                System.out.println(entry.getKey() + " "+ entry.getValue());
//            }
        }

        String timeProcess = simpleDateFormat.format(new Date());
        System.out.println("time end: " + timeProcess);

        OutputData outputData = new OutputData(inputData.getListOfVertex());
        if (chooseAlgorithm.equals("K core")) {
            outputData.updateResultKCore(kcore.getListOfDegreeOfVertex());
        } else if (chooseAlgorithm.equals("R core")) {
            outputData.updateResultRCore(rcore.getListOfReachabilityOfVertex());
        }
        outputData.writeOutputData(nameOfInputFile, chooseAlgorithm, chooseRunMode);
    }
}
