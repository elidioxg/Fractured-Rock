package fractureanalysis.statistics;

import java.util.ArrayList;

public class SampleAmplitude {
    
    /**     
     * @param array
     * @return Difference(Maximum Value - Minimum Value)
     */
    
    public static double getAmplitude(ArrayList<Double> array){
        double minValue=0.;
        double maxValue=0.;
        for(int i = 0; i<array.size(); i++){
            if(i ==0){
                minValue = array.get(i);
                maxValue = array.get(i);
            } else {
                if(minValue>array.get(i)){
                    minValue = array.get(i);
                }
                if(maxValue<array.get(i)){
                    maxValue = array.get(i);
                }
            }
            
        }
        return (maxValue - minValue);
    }
    
}
