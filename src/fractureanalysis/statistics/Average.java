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

public class Average {
    
    /**
     * Arithmetic Average
     * @param array
     * @return 
     */
    public static double arithmeticAverage(ArrayList<Double> array) {
        double sum = 0.;
        for (int i = 0; i < array.size(); i++) {
            sum += array.get(i);
        }
        return sum / (array.size());
    }

    //TODO: function for arithmetic average using Frequency Table    
    /**
     * Geometric Average
     * @param array
     * @return 
     */
    public static double geometricAverage(ArrayList<Double> array) {
        double prod = 1.;
        for (int i = 0; i <= array.size() - 1; i++) {
            prod *= array.get(i);
        }        
        return Math.pow(prod, (1.0 / array.size()));
    }

    //TODO: function for geometric average using Frequency Table    
}
