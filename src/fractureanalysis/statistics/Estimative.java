package fractureanalysis.statistics;

import fractureanalysis.Matrices.Vector;
import java.util.ArrayList;

public class Estimative {
    
    /**
     * Confiability Constant      
     */ 
    public enum Z{
        conf90, conf95, conf97, conf99;
        
        private final double int90 = 1.64;
        private final double int95 = 1.96;
        private final double int97 = 2.17;
        private final double int99 = 2.58;
        
        public double getDouble(Z z){
            switch(z){
                case conf90: 
                    return int90;
                case conf95:
                    return int95;
                case conf97:
                    return int97;
                case conf99:
                    return int99;
                default:
                    break;
            }
            return Double.NaN;
        }
    }    
    /**
     *  Normal Distribution
     * @param samplesNumber
     * @param arithmeticAverage
     * @param stdDeviation
     * @param confiabilityConstant
     */ 
    public static double samplingPrecisionNormal(int samplesNumber, 
            double arithmeticAverage, double stdDeviation, 
            double confiabilityConstant){
        return (arithmeticAverage  + ( confiabilityConstant*stdDeviation)/
                Math.sqrt(samplesNumber));
    }
    /**
     * Gaussian Distribution
     * @param arrayX
     * @param studentConstant
     * @return 
     */
    public static double samplingPrecisionGaussian(Vector vectorX,
            double studentConstant){
        double avg = Average.arithmeticAverage(vectorX);
        double std = StdDeviation.stdDeviation(vectorX);        
        double result = (avg) + (  (studentConstant * std)/Math.sqrt(vectorX.size()-1) );
        return result;
    }
}
