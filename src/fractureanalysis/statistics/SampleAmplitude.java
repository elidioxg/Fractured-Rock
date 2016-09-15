package fractureanalysis.statistics;

import fractureanalysis.Vectors.Vector;

public class SampleAmplitude {
    
    /**     
     * @param vector
     * @return Difference(Maximum Value - Minimum Value)
     */
    
    public static double getAmplitude(Vector vector){
        double minValue=0.;
        double maxValue=0.;
        for(int i = 0; i<vector.size(); i++){
            if(i ==0){
                minValue = vector.get(i).doubleValue();
                maxValue = vector.get(i).doubleValue();
            } else {
                if(minValue>vector.get(i).doubleValue()){
                    minValue = vector.get(i).doubleValue();
                }
                if(maxValue<vector.get(i).doubleValue()){
                    maxValue = vector.get(i).doubleValue();
                }
            }            
        }
        return (maxValue - minValue);
    }
    
}
