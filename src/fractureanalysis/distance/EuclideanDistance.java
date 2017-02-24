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
package fractureanalysis.distance;

import fractureanalysis.Matrices.Matrix;
import fractureanalysis.Vectors.Vector;

/**
 *
 * @author elidioxg
 */
public class EuclideanDistance {
    /**
     * Create a matrix of Euclidean Distances
     * @param matrix
     * @param colX
     * @param colY
     * @return
     * @throws Exception 
     */
    public static Matrix getDistances(Matrix matrix, int colX, int colY) throws Exception{
        if(colX> matrix.getColumnsCount() || colY > matrix.getColumnsCount()){
            throw new Exception("Index of column out o range.");
        }
        Vector x = matrix.getColumn(colX);
        Vector y = matrix.getColumn(colY);
        Matrix result = new Matrix(x.size());
        for(int i = 0; i< x.size(); i++){
            for(int j = 0; j <x.size(); j++){
                double distX = x.get(i).doubleValue()-x.get(j).doubleValue();
                double distY = y.get(i).doubleValue()-y.get(j).doubleValue();
                double dist = Math.sqrt(Math.pow(distX, 2)+Math.pow(distY, 2));
                result.set(i, j, dist);
                System.out.println("X: "+i+"  Y:"+j+"  D:"+dist);
            }
        }        
        return result;
    }
    /**
     * Create a matrix of Euclidean Distances
     * @param x
     * @param y
     * @return
     * @throws Exception 
     */
    public static Matrix getDistances(Vector x, Vector y) throws Exception{
        if(x.size()!=y.size()){
            throw new Exception("Vectors must have same size.");
        }
        Matrix result = new Matrix(x.size());
        for(int i = 0; i< x.size(); i++){
            for(int j = 0; j <x.size(); j++){
                double distX = x.get(i).doubleValue()-x.get(j).doubleValue();
                double distY = y.get(i).doubleValue()-y.get(j).doubleValue();
                double dist = Math.sqrt(Math.pow(distX, 2)+Math.pow(distY, 2));
                result.set(i, j, dist);
                System.out.println("X: "+i+"  Y:"+j+"  D:"+dist);
            }
        }        
        return result;
    }
    
    /**
     * Get a list of distance from (X,Y) to the locations represented
     * by vectors x and y
     * @param X
     * @param Y
     * @param x
     * @param y
     * @return 
     * @throws java.lang.Exception 
     */
    public static Vector getDistanceFrom(double X, double Y, Vector x, Vector y) throws Exception{
        if(x.size()!=y.size()){
            throw new Exception("Vectors must have same size.");
        }
        Vector result = new Vector(x.size());
        for (int i = 0; i<x.size(); i++){
            double distX = X - x.get(i).doubleValue();
            double distY = Y - y.get(i).doubleValue();
            result.set(i, Math.sqrt(Math.pow(distX, 2)+Math.pow(distY, 2)));
            System.out.println("index: "+i+"  :  "+result.get(i).doubleValue());
        }
        return result;
    }
}
