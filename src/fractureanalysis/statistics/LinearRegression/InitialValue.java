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
package fractureanalysis.statistics.LinearRegression;

import fractureanalysis.Matrices.Vector;
import fractureanalysis.statistics.Average;
import java.util.ArrayList;

/**
 * 
 * @author elidioxg
 */
public class InitialValue {
    
    /**
     * 
     * @param averageX
     * @param averageY
     * @param inclination
     * @return 
     */
    public static double getInitialValue(double averageX, double averageY,
            double inclination) {
        return averageY - inclination * averageX;
    }

    /**
     * 
     * @param arrayX
     * @param arrayY
     * @param inclination
     * @return 
     */
    public static double getInitialValue(Vector arrayX,
            Vector arrayY, double inclination) {
        double avgY = Average.arithmeticAverage(arrayY);
        double avgX = Average.arithmeticAverage(arrayX);
        return avgY - inclination * avgX;
    }
    
    public static double getInitialValue(ArrayList<Double> arrayX,
            ArrayList<Double> arrayY, double inclination) {
        double avgY = Average.arithmeticAverage(arrayY);
        double avgX = Average.arithmeticAverage(arrayX);
        return avgY - inclination * avgX;
    }
}
