package fractureanalysis.statistics.LinearRegression;

import java.util.ArrayList;

public class LinearRegression {

    private double initialValue = 0.;
    private double inclination = 0.;
    private ArrayList<Double> x = new ArrayList<>();

    public LinearRegression(ArrayList<Double> arrayX,
            ArrayList<Double> arrayY) {
        x = arrayX;
        try {
            this.inclination = Inclination.getInclination(arrayX, arrayY);
        } catch (Exception e) {

        }
        this.initialValue = InitialValue.getInitialValue(arrayX, arrayY, inclination);
    }

    public ArrayList<Double> linearRegressionPointsList(double firstValue,
            double lastValue, double stepValue) {
        ArrayList<Double> result = new ArrayList<>();
        for (double i = firstValue; i <= lastValue; i += stepValue) {
            result.add(this.initialValue + (this.inclination * i));            
        }
        return result;
    }

    /**
     *
     * @param valueAt
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
