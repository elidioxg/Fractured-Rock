package fractureanalysis.statistics;

import fractureanalysis.Vectors.Vector;
import java.util.ArrayList;

public class VariationCoefficient {
    
    /**
     * 
     * @param stdDeviation
     * @param arithmeticAvg
     * @return 
     */
    public static double variationCoefficient(double stdDeviation, double 
            arithmeticAvg){
        return 100*stdDeviation/arithmeticAvg;    
    }
    
    /**
     * 
     * @param vector
     * @return 
     */
    public static double variationCoefficient(Vector vector){        
        double avgValue = Average.arithmeticAverage(vector);        
        double stdDevValue = StdDeviation.stdDeviation(vector);
        return 100*stdDevValue/avgValue;
    }
    
    //TODO: logVariationCoefficient    
}
