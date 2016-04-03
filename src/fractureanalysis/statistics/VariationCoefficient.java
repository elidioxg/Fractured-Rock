package fractureanalysis.statistics;

import java.util.ArrayList;

public class VariationCoefficient {
    
    public static double variationCoefficient(double stdDeviation, double 
            arithmeticAvg){
        return 100*stdDeviation/arithmeticAvg;    
    }
    
    public static double variationCoefficient(ArrayList<Double> array){        
        double avgValue = Average.arithmeticAverage(array);        
        double stdDevValue = StdDeviation.stdDeviation(array);
        return 100*stdDevValue/avgValue;
    }
    
    //TODO: logVariationCoefficient    
}
