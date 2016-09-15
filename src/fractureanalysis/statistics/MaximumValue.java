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
import java.util.ArrayList;

/**
 *
 * @author elidioxg
 */
public class MaximumValue {

    /**
     * 
     * @param vector
     * @return 
     */
    public static double getMaxValue(Vector vector) {
        if (vector.size() != 0) {
            double max = vector.get(0).doubleValue();
            for (int i = 0; i < vector.size(); i++) {
                if (max < vector.get(i).doubleValue()) {
                    max = vector.get(i).doubleValue();
                }
            }
            return max;
        } else {
            return Double.NaN;
        }
    }
     
    /**
     * 
     * @param array
     * @return 
     */
    public static double getMaxValue(ArrayList<Double> array) {
        if (array.size() != 0) {
            double max = array.get(0).doubleValue();
            for (int i = 0; i < array.size(); i++) {
                if (max < array.get(i)) {
                    max = array.get(i);
                }
            }
            return max;
        } else {
            return Double.NaN;
        }
    }
}
