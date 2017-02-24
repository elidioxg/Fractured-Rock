package fractureanalysis.statistics;

import fractureanalysis.Matrices.Matrix;
import fractureanalysis.Vectors.Vector;
import java.util.ArrayList;

public class Covariance {
    
    /**
     * Calculate covariance of two vectors
     * @param vector1
     * @param vector2
     * @return
     * @throws Exception 
     */
    public static double covariance(Vector vector1, Vector vector2) throws Exception {
        double result = 0.;
        if(vector1.size()!=vector2.size()){
            throw new Exception("The arrays must have same size");
        } else {            
            double avg1 = Average.arithmeticAverage(vector1);
            double avg2 = Average.arithmeticAverage(vector2);
            for(int i = 0; i <vector1.size(); i++){
                result += (vector1.get(i).doubleValue() -
                        avg1)*(vector2.get(i).doubleValue()- avg2);
            }
        }               
        return result /( vector1.size()-1);
    }    
    
    /**
     * 
     * @param array1
     * @param array2
     * @return
     * @throws Exception 
     */
    public static double covariance(ArrayList<Double> array1, 
            ArrayList<Double> array2) throws Exception {
        double result = 0.;
        if(array1.size()!=array2.size()){
            throw new Exception("The arrays must have same size");
        } else {            
            double avg1 = Average.arithmeticAverage(array1);
            double avg2 = Average.arithmeticAverage(array2);
            for(int i = 0; i <array1.size()-1; i++){
                result += (array1.get(i) - avg1)*(array2.get(i) - avg2);
            }
        }               
        return result/(array1.size()-1);
    }    
    
    /**     
     * @param array1
     * @param array2
     * @param average1
     * @param average2
     * @return
     * @throws Exception 
     */
    public static double covariance(ArrayList<Double> array1, ArrayList<Double> array2,
            double average1, double average2) throws Exception {
        double result = 0.;
        if(array1.size()!=array2.size()){
            throw new Exception("The arrays must have same size");
        } else {
            for(int i = 0; i <array1.size()-1; i++){
                result += (array1.get(i) - average1)*(array2.get(i) - average2);
            }
        }               
        return result/(array1.size()-1);
    }
    
    /**
     * Calculate covariance for matrix with x, y and z columns
     * @param matrix
     * @param xcolumn 
     * @param ycolumn
     * @param contentcolumn
     * @return 
     */
    public static Matrix covariance(Matrix matrix, int xcolumn, int ycolumn,
            int contentcolumn){
        Matrix result  = new Matrix();
        return result;
    }
    
    public static Matrix getCovarianceMatrix(Matrix input) throws Exception{
        if(input.getColumnsCount()!=input.getLinesCount()){
            throw new Exception("Input must be a square matrix.");
        }               
        int size = input.getColumnsCount();
        Matrix result = new Matrix(size);
        /**
         * Each column of the matrix will be a vector
         */
        Vector[] vector = new Vector[size];
        for(int i = 0 ; i < size; i++){
            //vector[i] = new Vector(size);
            vector[i] = input.getColumn(i);                    
        }
        /**
         * Covariance matrix algorithm
         */
        for (int i = 0 ; i< size; i++){
            result.set(i, i, Variance.variance(vector[i]));
            for(int j = i+1; j < size; j++){
                result.set(j, i, Covariance.covariance(vector[i], vector[j]));
                result.set(i, j , result.get(j, i));
            }
        }
        return result;
    }
    
}
