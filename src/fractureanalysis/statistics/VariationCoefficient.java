package fractureanalysis.statistics;

import fractureanalysis.Vectors.Vector;

public class VariationCoefficient {
    
    /**
     * 
     * @param stdDeviation
     * @param arithmeticAvg
     * @return 
     */
    public static double variationCoefficient(double stdDeviation, double 
            arithmeticAvg){
        return stdDeviation/arithmeticAvg;    
    }
    
    /**
     * 
     * @param vector
     * @return 
     */
    public static double variationCoefficient(Vector vector){        
        double avgValue = Average.arithmeticAverage(vector);        
        double stdDevValue = StdDeviation.stdDeviation(vector);
        return stdDevValue/avgValue;
    }
    
    //TODO: logVariationCoefficient    
}
