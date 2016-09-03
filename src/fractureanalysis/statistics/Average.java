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

import fractureanalysis.Matrices.Vector;
import java.util.ArrayList;

public class Average {
    
    /**
     * Arithmetic Average
     * @param vector
     * @return 
     */
    public static double arithmeticAverage(Vector vector) {
        double sum = 0.;
        for (int i = 0; i <= vector.size() - 1; i++) {
            sum += vector.get(i).doubleValue();
        }
        return sum / (vector.size());
    }

    //TODO: function for arithmetic average using Frequency Table    
    /**
     * 
     * @param vector
     * @return 
     */
    public static double geometricAverage(Vector vector) {
        double prod = 1.;
        for (int i = 0; i <= vector.size() - 1; i++) {
            prod *= vector.get(i).doubleValue();
        }        
        return Math.pow(prod, (1.0 / vector.size()));
    }

    //TODO: function for geometric average using Frequency Table   
    
    /**
     * 
     * @param vector
     * @return 
     */
    public static double arithmeticAverage(ArrayList<Double> vector) {
        double sum = 0.;
        for (int i = 0; i <= vector.size() - 1; i++) {
            sum += vector.get(i).doubleValue();
        }
        return sum / (vector.size());
    }
}
