/*
 * Copyright (C) 2016 elidioxg
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractureanalysis.statistics;

import java.util.ArrayList;

/**
 *
 * @author elidioxg
 */
public class Frequency {
    /**
     * 
     * @param amplitude
     * @param numberOfSamples
     * @return Value of class interval
     */
    public static double sturgesExpression(Double amplitude, int numberOfSamples){
        return (amplitude/(1+(3.32*Math.log(numberOfSamples))));        
    }
    
    /**
     * Gives a list with properties of class intervals 
     * @param minValue
     * @param maxValue
     * @param classInterval
     * @return 
     */
    public static ArrayList<ClassInterval> classIntervals(double minValue, 
            double maxValue, double classInterval){
        ArrayList<ClassInterval> array = new ArrayList<>();        
        int n = (int)((maxValue-minValue)/classInterval);
        for(int i = 0; i<n ; i++){
            double min = minValue+(i*classInterval);
            ClassInterval ci = new ClassInterval(i, min, min+classInterval);
            array.add(ci);
        }
        return array;
    }
    
    public static ArrayList<ClassInterval> countObsFrequency(
            ArrayList<Double> values, ArrayList<ClassInterval> classes){        
        for(int i=0; i<values.size(); i++){
            for(int j = 0; j<classes.size(); j++){
                if(values.get(i)>classes.get(j).getInferiorValue()){
                    if(values.get(i)<classes.get(j).getSuperiorValue()){
                        classes.get(j).addFrequency();
                    }
                }
            }            
        }
        return classes;
    }
}