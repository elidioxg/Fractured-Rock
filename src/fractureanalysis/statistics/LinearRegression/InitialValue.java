package fractureanalysis.statistics.LinearRegression;

import fractureanalysis.statistics.Average;
import java.util.ArrayList;

public class InitialValue {
    public static double getInitialValue(double averageX, double averageY,
            double inclination) {
        return averageY - inclination * averageX;
    }

    public static double getInitialValue(ArrayList<Double> arrayX,
            ArrayList<Double> arrayY, double inclination) {
        double avgY = Average.arithmeticAverage(arrayY);
        double avgX = Average.arithmeticAverage(arrayX);
        return avgY - inclination * avgX;
    }
}
