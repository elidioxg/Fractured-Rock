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

import fractureanalysis.Vectors.Vector;
import java.util.ArrayList;

/**
 * 
 * @author elidioxg
 */

public class LinearRegression {
    /**
     * This class handle the Linear Regression analysis.
     * The class receives the vectors X and Y on constructor and handle the
     * properties of Linear Regression analysis as two variables: 'initialValue'
     * and 'inclination'.
     */

    private double initialValue = 0.;
    private double inclination = 0.;   

    /**
     * 
     * @param arrayX
     * @param arrayY 
     */
    public LinearRegression(Vector arrayX, Vector arrayY) {        
        try {
            this.inclination = Inclination.getInclination(arrayX, arrayY);
        } catch (Exception e) {

        }
        this.initialValue = InitialValue.getInitialValue(arrayX, arrayY, inclination);        
    }
    
    public LinearRegression(ArrayList<Double> arrayX, ArrayList<Double> arrayY) {        
        try {
            this.inclination = Inclination.getInclination(arrayX, arrayY);
        } catch (Exception e) {

        }
        this.initialValue = InitialValue.getInitialValue(arrayX, arrayY, inclination);        
    }

    /**
     * 
     * @param firstValue
     * @param lastValue
     * @param stepValue
     * @return 
     */
    public Vector linearRegressionPointsList(double firstValue,
            double lastValue, double stepValue) {
        Vector result = new Vector();
        for (double i = firstValue; i <= lastValue; i += stepValue) {
            result.add(this.initialValue + (this.inclination * i));            
        }
        return result;
    }

    /**
     * Estimate the value of Y at specific value of X.
     * @param valueAt Value of X
     * @return
     */
    public double getValueAt(double valueAt) {        
        return (this.initialValue + (this.inclination * valueAt));
    }
    
    /**
     * 
     * @return 
     */
    public double getInclination(){
        return this.inclination;
    }
    
    /**
     * 
     * @return 
     */
    public double getInitialValue(){
        return this.initialValue;
    }

}
