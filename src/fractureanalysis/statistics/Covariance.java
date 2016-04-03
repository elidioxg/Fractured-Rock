package fractureanalysis.statistics;

import java.util.ArrayList;

public class Covariance {
    
    /**
     * 
     * @param array1
     * @param array2
     * @return
     * @throws Exception 
     */
    public static double covariance(ArrayList<Double> array1, 
            ArrayList<Double> array2) throws Exception {
        double result = 0.;
        if(array1.size()!=array2.size()){
            throw new Exception("The arrays must have same size");
        } else {            
            double avg1 = Average.arithmeticAverage(array1);
            double avg2 = Average.arithmeticAverage(array2);
            for(int i = 0; i <array1.size()-1; i++){
                result += (array1.get(i) - avg1)*(array2.get(i) - avg2);
            }
        }               
        return result/array1.size();
    }    
    
    /**     
     * @param array1
     * @param array2
     * @param average1
     * @param average2
     * @return
     * @throws Exception 
     */
    public static double covariance(ArrayList<Double> array1, ArrayList<Double> array2,
            double average1, double average2) throws Exception {
        double result = 0.;
        if(array1.size()!=array2.size()){
            throw new Exception("The arrays must have same size");
        } else {
            for(int i = 0; i <array1.size()-1; i++){
                result += (array1.get(i) - average1)*(array2.get(i) - average2);
            }
        }               
        return result/array1.size();
    }
}
