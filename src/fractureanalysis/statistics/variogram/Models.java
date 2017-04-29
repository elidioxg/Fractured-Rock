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

import fractureanalysis.Matrices.Matrix;

/**
 *
 * @author elidioxg
 */
public class Models {

    /**
     *
     * @param c The sill
     * @param a The range
     * @return
     * @throws java.lang.Exception
     */
    public static Matrix spherical(double c, double a) throws Exception {
        double h=a/2;
        double aux = 0.1;
        double value;
        int nSteps = 20;
        Matrix result = new Matrix(2, nSteps + 1);
        for (int i = 0; i <= nSteps; i++) {
            if (aux <= a) {
                value = c * ((1.5) * (aux / a) - (.5) * (Math.pow((aux / a), 3)));

            } else {
                value = c;
            }
            result.set(0, i, aux);
            result.set(1, i, value);
            aux += h;
        }
        return result;
    }   
    
    

    /**
     * Curve fitting for Exponential Model on Variogram h(steps) = a(range)
     *
     * @param c
     * @param a
     * @return
     * @throws Exception
     */
    public static Matrix exponential(double c, double a) throws Exception {
        double h = a/2;
        //int nSteps = (int) (a / h) + 5;
        int nSteps = 20;
        Matrix result = new Matrix(2, nSteps + 1);
        double aux = 0.1;
        int index = 0;
        while (index <= nSteps) {
            double value = c * (1 - Math.exp(-aux / a));
            result.set(0, index, aux);
            result.set(1, index, value);
            aux += h;
            index++;
        }
        return result;

    }

    /**
     *
     * @param c
     * @param a
     * @return
     * @throws Exception
     */
    public static Matrix gaussian(double c, double a) throws Exception {
        double h=a/2;        
        int nSteps = (int) (a / h) + 10;
        Matrix result = new Matrix(2, nSteps + 1);
        double aux = 0.1;
        int index = 0;
        while (index <= nSteps) {
            double value = c * (1 - Math.exp(-Math.pow(3 * aux, 2) / Math.pow(a, 2)));
            result.set(0, index, aux);
            result.set(1, index, value);
            aux += h;
            index++;
        }
        return result;
    }

}
