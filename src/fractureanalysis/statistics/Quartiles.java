/*
 * Copyright (C) 2017 elidioxg
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

/**
 *
 * @author elidioxg
 */
public class Quartiles {
    public static double firstQuartil(Vector vector) {
        vector.sort(true);
        int index = (int) Math.round((vector.size() + 1) * .25);
        return vector.get(index - 1).doubleValue();
    }

    public static double secondQuartil(Vector vector) {
        double result;
        vector.sort(true);
        if ((vector.size() % 2) == 0) {
            double n1 = vector.get((int) (vector.size() / 2) - 1).doubleValue();
            double n2 = vector.get((int) ((vector.size()) / 2)).doubleValue();
            result = (n1 + n2) / 2;
        } else {
            result = vector.get(((vector.size() + 1) / 2) - 1).doubleValue();
        }
        return result;
    }

    public static double thirdQuartil(Vector vector) {
        vector.sort(true);
        int index = (int) Math.round((vector.size() + 1) * .75);
        return vector.get(index - 1).doubleValue();
    }

    public static double amplitudeInterQuartiles(double first, double third) {        
        double result = third - first;
        return result;
    }
    
}
