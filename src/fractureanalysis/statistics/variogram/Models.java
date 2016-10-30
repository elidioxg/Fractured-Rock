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
package fractureanalysis.statistics.variogram;

import fractureanalysis.Vectors.Vector;

/**
 *
 * @author elidioxg
 */
public class Models {

    /**
     *
     * @param c The sill
     * @param a The range
     * @param h The steps on calculation
     * @return
     * @throws java.lang.Exception
     */
    public Vector spherical(double c, double a, double h) throws Exception {
        if (h > a) {
            throw new Exception("'h' must be smaller than 'a'");
        }
        int nSteps = (int) (a / h) + 5;
        Vector result = new Vector(nSteps);
        double aux = 0.1;
        int index = 0;
        while (aux <= a) {
            double value = c * (((3 * aux) / 2 * a) - ((Math.pow(h, 3) / (2 * Math.pow(a, 3)))));
            result.set(index, value);
            aux += h;
            index++;
        }
        for (int i = index; i < index + 5; i++) {
            result.set(i, c);
        }
        return result;
    }

    /**
     * Curve fitting for Exponential Model on Variogram
     * 
     * @param c The sill
     * @param a The range
     * @param h Steps of calculation
     * @return
     * @throws Exception 
     */
    public Vector exponential(double c, double a, double h) throws Exception {
        if (h > a) {
            throw new Exception("'h' must be smaller than 'a'");
        }
        int nSteps = (int) (a / h) + 5;
        Vector result = new Vector(nSteps);
        double aux = 0.1;
        int index = 0;
        while (index <= nSteps) {
            double value = c * (1 - Math.exp(-aux/a));
            result.set(index, value);
            aux+=h;
            index++;
        }
        return result;

    }
    
    public Vector gaussian(double c, double a, double h) throws Exception {
        if (h > a) {
            throw new Exception("'h' must be smaller than 'a'");
        }
        int nSteps = (int) (a / h) + 5;
        Vector result = new Vector(nSteps);
        double aux = 0.1;
        int index = 0;
        while (index <= nSteps) {
            double value = c * (1 - Math.exp(-Math.pow(aux,2)/Math.pow(a,2)));
            result.set(index, value);
            aux+=h;
            index++;
        }
        return result;

    }
    
    

}
