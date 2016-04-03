package fractureanalysis.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author elidioxg
 */
public class DatasetProperties {
    /**
     * 
     * @param filename
     * @param separator
     * @return Number of columns in dataset file
     */
    
    public static int getColumnsCount(String filename, String separator){
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
        return lineValues.length;
    }
    
}
