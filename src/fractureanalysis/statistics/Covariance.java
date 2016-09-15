package fractureanalysis.statistics;

import fractureanalysis.Vectors.Vector;
import java.util.ArrayList;

public class Covariance {
    
    /**
     * 
     * @param vector1
     * @param vector2
     * @return
     * @throws Exception 
     */
    public static double covariance(Vector vector1, Vector vector2) throws Exception {
        double result = 0.;
        if(vector1.size()!=vector2.size()){
            throw new Exception("The arrays must have same size");
        } else {            
            double avg1 = Average.arithmeticAverage(vector1);
            double avg2 = Average.arithmeticAverage(vector2);
            for(int i = 0; i <vector1.size(); i++){
                result += (vector1.get(i).doubleValue() -
                        avg1)*(vector2.get(i).doubleValue()- avg2);
            }
        }               
        return result/vector1.size();
    }    
    
}
