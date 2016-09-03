package fractureanalysis.statistics;

import fractureanalysis.Matrices.Vector;
import java.util.ArrayList;

public class Variance {
    
    /**
     * 
     * @param vector
     * @return 
     */
    public static double variance(Vector vector){
        double result = 0.;
        Average avg = new Average();
        double averageValue = avg.arithmeticAverage(vector);
        for(int i = 0; i <vector.size(); i++){
            result += Math.pow(vector.get(i).doubleValue() - averageValue, 2);
        }
        return result/vector.size();
    }
    
    /**
     * 
     * @param array
     * @param average
     * @return 
     */
    public static double variance(Vector vector, double average){
        double result = 0.;
        for(int i = 0; i <vector.size(); i++){
            result += Math.pow(vector.get(i).doubleValue() - average, 2);
        }
        return result/vector.size();
    }
    
}
