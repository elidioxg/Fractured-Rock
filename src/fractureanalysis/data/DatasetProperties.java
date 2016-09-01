package fractureanalysis.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author elidioxg
 */
public class DatasetProperties {

    //TODO: um único procedimento para pegar todas as propriedades
    /**
     *
     * @param filename
     * @param separator
     * @return Number of columns in dataset file
     */
    public static int getColumnsCount(String filename, String separator) {
        BufferedReader br = null;
        ArrayList<Double> values = new ArrayList<>();
        String line = null;
        String[] lineValues = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            line = br.readLine();
            lineValues = line.split(separator);
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
        if (lineValues != null) {
            return lineValues.length;
        } else {
            return 0;
        }
    }

    /**
     *
     * @param filename
     * @param separator
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<String> getHeaders(String filename, String separator)
            throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        final String headerLine = br.readLine();
        final String[] headerValues = headerLine.split(separator);
        ArrayList<String> result = new ArrayList();
        result.addAll(Arrays.asList(headerValues));
        return result;
    }

    public static int getRowCount(String filename, String separator) {
        BufferedReader br = null;
        int count = 0;
        String line = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                count += 1;
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
        return count;
    }
}
