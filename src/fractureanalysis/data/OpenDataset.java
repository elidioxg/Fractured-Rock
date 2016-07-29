package fractureanalysis.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenDataset {

    public static ArrayList<Double> openCSVFileToDouble(String fileName, String separator,
            int column, boolean hasHeader) {
        BufferedReader br = null;
        ArrayList<Double> values = new ArrayList<>();
        String line = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            if (hasHeader) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                String[] lineValues = line.split(separator);
                values.add(Double.valueOf(lineValues[column]));
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return values;
    }

    public ArrayList[][] openCSVFileToArray(String fileName, String separator,
            int indexId, int indexSp, int indexAp) {
        ArrayList[][] array = null;
        BufferedReader br = null;
        List<Double> values = new ArrayList<>();
        String line = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            int countLines = 0;
            while (br.readLine() != null) {
                countLines++;
            }
            array = new ArrayList[countLines][];
            br.reset();
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] lineValues = line.split(separator);
                array[count][0] = new ArrayList();
                array[count][0].add(lineValues[indexId]);
                array[count][1] = new ArrayList();
                array[count][1].add(lineValues[indexSp]);
                array[count][2] = new ArrayList();
                array[count][2].add(lineValues[indexAp]);
                count++;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return array;
    }        

}
