package fractureanalysis.statistics;

import fractureanalysis.Matrices.Vector;
import java.util.ArrayList;

public class QuiSquare {

    /**
     * 
     * @param observedFrequency
     * @param expectedFrequency
     * @return
     * @throws Exception 
     */
    public static double getQuiSquare(Vector observedFrequency,
            Vector expectedFrequency) throws Exception {
        if (observedFrequency.size() != expectedFrequency.size()) {
            throw new Exception("Oberserved Frequency and Expected Frequency Arrays must have same size.");
        }
        double sum = 0.;
        for (int i = 0; i < observedFrequency.size(); i++) {
            sum += (Math.pow(
                    (observedFrequency.get(i).doubleValue() - 
                            expectedFrequency.get(i).doubleValue()), 2)
                    / expectedFrequency.get(i).doubleValue());
        }
        return sum;
    }
    
    /**
     * Qui-Square coefficient
     * @param observedFrequency
     * @param expectedFrequency
     * @return
     * @throws Exception 
     */
    public static double getQuiSquare(ArrayList<Double> observedFrequency,
            ArrayList<Double> expectedFrequency) throws Exception {
        if (observedFrequency.size() != expectedFrequency.size()) {
            throw new Exception("Oberserved Frequency and Expected Frequency Arrays must have same size.");
        }
        double sum = 0.;
        for (int i = 0; i < observedFrequency.size(); i++) {
            sum += (Math.pow(
                    (observedFrequency.get(i) - expectedFrequency.get(i)), 2)
                    / expectedFrequency.get(i));
        }
        return sum;
    }

}
