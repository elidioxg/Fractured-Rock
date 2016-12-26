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

import fractureanalysis.Vectors.Vector;

public class StdDeviation {
    
    /**
     * Calculate standard deviation for a vector.
     * @param vector
     * @return 
     */
    public static double stdDeviation(Vector vector){        
        double averageValue = Average.arithmeticAverage(vector);
        double sum = 0.;
        for(int i= 0; i<vector.size(); i++){
            sum += Math.pow((averageValue - vector.get(i).doubleValue()), 2);
        }
        double result = Math.sqrt(sum/(vector.size()-1));
        return result;
    }
    
    /**
     * Calculate logarithmic standard deviation for a vector.
     * The variable baseLog usually receives 10 as input. 
     * @param vector
     * @param baseLog 
     * @return 
     */
    public static double logStdDeviation(Vector vector, int baseLog){        
        double avrValue = Average.arithmeticAverage(vector);
        double sum = 0.;
        for(int i = 0; i< vector.size(); i++){
            sum += Math.pow ((Math.log(sum) - Math.log(avrValue) ), 2);
        }
        double sqrtValue = Math.sqrt(sum/(vector.size()-1));
        double result = Math.pow(baseLog, sqrtValue);
        return result;
    }
       
    //TODO: stdDeviation for frequency table            
    
    //TODO: Logaritmic Standard Deviation with frequency tables
   
    /**
     * Arithmetic standard deviation for two samples
     * @param sample1Number
     * @param sample2Number
     * @param sample1StdDeviation
     * @param sample2StdDeviation
     * @return 
     */
    public static double stdDeviationTwoSamples(int sample1Number, int sample2Number,
            double sample1StdDeviation, double sample2StdDeviation){
        double result = 0.;
        result = Math.sqrt(  ( (sample1Number*Math.pow(sample1StdDeviation, 2) ) +
                (sample2Number*Math.pow(sample2StdDeviation, 2))) /
                (sample1Number + sample2Number - 2) );
        return result;
    }
}
