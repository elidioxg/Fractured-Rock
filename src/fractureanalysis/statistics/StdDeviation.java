package fractureanalysis.statistics;

import java.util.ArrayList;

public class StdDeviation {
    
    //Standard Deviation  
    public static double stdDeviation(ArrayList<Double> array){        
        double averageValue = Average.arithmeticAverage(array);
        double sum = 0.;
        for(int i= 0; i<=array.size()-1; i++){
            sum += Math.pow((averageValue - array.get(i)), 2);
        }
        double result = Math.sqrt(sum/(array.size()-1));
        return result;
    }
    
    //TODO: stdDeviation for frequency table
    
    //Logaritmic Standard Deviation 
    public static double logStdDeviation(ArrayList<Double> array, int baseLog){        
        double avrValue = Average.arithmeticAverage(array);
        double sum = 0.;
        for(int i = 0; i<= array.size()-1; i++){
            sum += Math.pow ((Math.log(sum) - Math.log(avrValue) ), 2);
        }
        double sqrtValue = Math.sqrt(sum/(array.size()-1));
        double result = Math.pow(baseLog, sqrtValue);
        return result;
    }
    
    //TODO: Logaritmic Standard Deviation with frequency tables
    
    //Arithmetic standard deviation for two samples
    public static double stdDeviationTwoSamples(int sample1Number, int sample2Number,
            double sample1StdDeviation, double sample2StdDeviation){
        double result = 0.;
        result = Math.sqrt(  ( (sample1Number*Math.pow(sample1StdDeviation, 2) ) +
                (sample2Number*Math.pow(sample2StdDeviation, 2))) /
                (sample1Number + sample2Number - 2) );
        return result;
    }
}
