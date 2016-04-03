package fractureanalysis.statistics;

import java.util.ArrayList;

public class Average {
    
    /**
     * Arithmetic Average
     * @param array
     * @return 
     */
    public static double arithmeticAverage(ArrayList<Double> array) {
        double sum = 0.;
        for (int i = 0; i <= array.size() - 1; i++) {
            sum += array.get(i);
        }
        return sum / (array.size());
    }

    //TODO: function for arithmetic average using Frequency Table    
    /**
     * Geometric Average
     * @param array
     * @return 
     */
    public static double geometricAverage(ArrayList<Double> array) {
        double prod = 0.;
        for (int i = 0; i <= array.size() - 1; i++) {
            prod *= array.get(i);
        }        
        return Math.pow(prod, (1.0 / array.size()));
    }

    //TODO: function for geometric average using Frequency Table    
}
