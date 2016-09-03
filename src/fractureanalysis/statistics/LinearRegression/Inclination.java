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

public class Inclination {
    /**
     * 
     * @param arrayX
     * @param arrayY
     * @return
     * @throws Exception 
     */
    public static double getInclination(Vector arrayX, Vector arrayY) throws Exception {
        if (arrayX.size() != arrayY.size()) {
            throw new Exception("X and Y arrays must have same size.");
        }
        double sumX2 = 0.;
        double sumXY = 0.;
        for (int i = 0; i < arrayX.size(); i++) {
            sumX2 += Math.pow(arrayX.get(i).doubleValue(), 2);
            sumXY += arrayX.get(i).doubleValue()* arrayY.get(i).doubleValue();
        }        
        double avgX = Average.arithmeticAverage(arrayX);
        double avgY = Average.arithmeticAverage(arrayY);
        double sXX = sumX2 - (arrayX.size() * Math.pow(avgX, 2));
        double sXY = sumXY - (arrayX.size() * avgX * avgY);
        return sXY / sXX;
    }

    /**
     * 
     * @param arrayX
     * @param arrayY
     * @param averageX
     * @param averageY
     * @return
     * @throws Exception 
     */
    public static double getInclination(Vector arrayX,
            Vector arrayY, double averageX,
            double averageY) throws Exception {
        if (arrayX.size() != arrayY.size()) {
            throw new Exception("X and Y arrays must have same size.");
        }
        double sumX2 = 0.;
        double sumXY = 0.;
        for (int i = 0; i < arrayX.size(); i++) {
            sumX2 += Math.pow(arrayX.get(i).doubleValue(), 2);
            sumXY += arrayX.get(i).doubleValue()
                    * arrayY.get(i).doubleValue();
        }
        double sXX = sumX2 - (arrayX.size() * Math.pow(averageX, 2));
        double sXY = sumXY - (arrayX.size() * averageX * averageY);
        return sXY / sXX;
    }
    
    /**
     * 
     * @param arrayX
     * @param arrayY
     * @return
     * @throws Exception 
     */
    public static double getInclination(ArrayList<Double> arrayX, 
            ArrayList<Double> arrayY) throws Exception {
        if (arrayX.size() != arrayY.size()) {
            throw new Exception("X and Y arrays must have same size.");
        }
        double sumX2 = 0.;
        double sumXY = 0.;
        for (int i = 0; i < arrayX.size(); i++) {
            sumX2 += Math.pow(arrayX.get(i).doubleValue(), 2);
            sumXY += arrayX.get(i).doubleValue()* arrayY.get(i).doubleValue();
        }        
        double avgX = Average.arithmeticAverage(arrayX);
        double avgY = Average.arithmeticAverage(arrayY);
        double sXX = sumX2 - (arrayX.size() * Math.pow(avgX, 2));
        double sXY = sumXY - (arrayX.size() * avgX * avgY);
        return sXY / sXX;
    }
}
