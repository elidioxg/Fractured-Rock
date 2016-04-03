package fractureanalysis.statistics;

import java.util.ArrayList;

public class Variance {
    /**
     * 
     * @param array
     * @param average
     * @return 
     */
    public static double variance(ArrayList<Double> array, double average){
        double result = 0.;
        for(int i = 0; i <array.size()-1; i++){
            result += Math.pow(array.get(i) - average, 2);
        }
        return result/array.size();
    }
    /**
     * 
     * @param array
     * @return 
     */
    public static double variance(ArrayList<Double> array){
        double result = 0.;
        Average avg = new Average();
        double averageValue = avg.arithmeticAverage(array);
        for(int i = 0; i <array.size()-1; i++){
            result += Math.pow(array.get(i) - averageValue, 2);
        }
        return result/array.size();
    }
    
}
