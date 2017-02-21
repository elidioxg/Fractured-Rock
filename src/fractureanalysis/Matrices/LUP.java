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
package fractureanalysis.Matrices;

import fractureanalysis.Vectors.Vector;

/**
 * This code was strongly based on this program:
 * http://chandraacads.blogspot.com.br/2015/12/c-program-for-matrix-inversion.html
 * Thank you for sharing.
 *
 * @author elidioxg
 */
public class LUP {
    private Matrix original;    
    private Matrix decomposed;
    private Matrix inverse;
    private Vector P;
    private final int size;

    public LUP(Matrix matrix) throws Exception {
        if (matrix.getColumnsCount() != matrix.getLinesCount()) {
            throw new Exception("Columns and Lines counts must be equal.");
        }
        
        this.original = matrix;
        this.size = matrix.getColumnsCount();
    }

    public void lupDecompose() throws Exception {
        Matrix matrix = this.original;
        this.P = new Vector(size);
        int i, j, k, kd = 0, T;
        double p, t;
        for (i = 0; i < size; i++) {
            P.set(i, i);
        }
        for (k = 0; k < size - 1; k++) {
            p = 0.;
            for (i = k; i < size; i++) {
                t = matrix.get(k, i).doubleValue();
                if (t < 0) {
                    t *= -1;
                }
                if (t > p) {
                    p = t;
                    kd = i;
                }

            }
            if (p == 0) {
                throw new Exception("ERROR: A singular matrix is supplied.");
            }

            T = P.get(kd).intValue();            
            P.set(kd, P.get(k).doubleValue());
            P.set(k, T);
            for (i = 0; i < size; i++) {
                t = matrix.get(i, kd).doubleValue();
                matrix.set(i, kd, matrix.get(i, k).doubleValue());
                matrix.set(i, k, t);
            }

            //Performing substraction to decompose matrix as LU.
            for (i = k + 1; i < size; i++) {
                matrix.set(k, i, (matrix.get(k, i).doubleValue() / matrix.get(k, k).doubleValue()));
                for (j = k + 1; j < size; j++) {
                    double value = matrix.get(j, i).doubleValue();
                    value -= matrix.get(k, i).doubleValue() * matrix.get(j, k).doubleValue();
                    matrix.set(j, i, value);
                }

            }
        }
        this.decomposed = matrix;
    }

    public void lupInverse() throws Exception {
        Matrix matrix = this.decomposed;
        int i, n, m;
        double t;

        Vector X = new Vector(size);
        Vector Y = new Vector(size);
        Matrix B = new Matrix(size);

        //Storing elements of the i-th column of the identity matrix in i-th row of 'B'.                          
        for (i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                B.set(j, i, 0);
            }
            B.set(i, i, 1);
        }
        /* Solving LUX = Pe, in order to calculate the inverse of 'A'. Here, 'e' is a column  
        * vector of the identity matrix of size 'size-1'. Solving for all 'e'. */

        for (i = 0; i < size; i++) {
            //Solving Ly = Pb
            for (n = 0; n < size; n++) {
                t = 0;
                for (m = 0; m <= n-1; m++) {
                    t += matrix.get(m, n).doubleValue() * Y.get(m).doubleValue();
                }
                int aux = (int) this.P.get(n).intValue();
                Y.set(n, B.get(aux, i).doubleValue() - t);       
            }
            //Solving Ux = y            
            for (n = size - 1; n >= 0; n--) {
                t = 0;                
                for (m = n+1; m < size; m++) {                    
                    t += matrix.get(m, n).doubleValue() * X.get(m).doubleValue();                    
                }
                double value = (Y.get(n).doubleValue() - t) / (matrix.get(n, n).doubleValue());
                X.set(n, value);                
            }     //Now, X contains the solution.         

            for (int j = 0; j < size; j++) {
                B.set(j, i, X.get(j));
                //System.out.println("B setado: " + B.get(j, i).doubleValue());
            }
        }
        this.inverse = B;
        System.out.println("\nB: ");
        B.print();
        /* Copying transpose of 'B' into 'LU', which would the inverse of 'A'. */
        /*System.out.println("Copying transpose of 'B' into 'LU', which would the inverse of 'A'.");
        for (i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix.set(j, i, B.get(i, j).doubleValue());
            }
        }
        matrix.print();*/
    }

    public Matrix getOriginal() {
        return this.original;
    }
    
    public Matrix getInverse(){
        return this.inverse;
    }
    
    public Matrix getDecomposed(){
        return this.decomposed;
    }
}
