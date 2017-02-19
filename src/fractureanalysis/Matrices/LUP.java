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

    private Matrix matrix;
    private Vector P;
    private final int size;

    public LUP(Matrix matrix) throws Exception {
        if (matrix.getColumnsCount() != matrix.getLinesCount()) {
            throw new Exception("Columns and Lines counts must be equal.");
        }
        this.matrix = matrix;
        this.size = matrix.getColumnsCount();
    }

    public Matrix lupDecompose() throws Exception {
        
        int size = matrix.getColumnsCount();
        Vector P = new Vector(size);
        //this.P = new Vector(size);
        int i, j, k, kd = 0, T;
        double p, t;
        for (i = 1; i < size; i++) {
            P.set(i, i);
        }
        P.print();
        for (k = 1; k < size; k++) {
            p = 0.;
            for (i = k; i < size; i++) {
                t = matrix.get(i, k).doubleValue();
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
            P.set(kd, P.get(k));
            P.set(k, T);
            for (i = 1; i < size; i++) {
                t = matrix.get(i, kd).doubleValue();
                matrix.set(i, kd, matrix.get(i, k));
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
        System.out.println();
        System.out.println("PIVOT");
        P.print();
        matrix.print();
        return matrix;
    }

    public Matrix lupInverse() throws Exception {
        int size = matrix.getColumnsCount();
        int i, j, n, m;
        double t;

        Vector X = new Vector(size);
        Vector Y = new Vector(size);
        Matrix B = new Matrix(size);
        /* Solving LUX = Pe, in order to calculate the inverse of 'A'. Here, 'e' is a column  
        * vector of the identity matrix of size 'size-1'. Solving for all 'e'. */
        for (i = 1; i < size; i++) {
            //Storing elements of the i-th column of the identity matrix in i-th row of 'B'.                          
            B.set(i, i, 0);

            //Solving Ly = Pb
            for (n = 1; n < size; n++) {
                t = 0;
                for (m = 1; m <= n - 1; m++) {
                    t += matrix.get(n, m).doubleValue() * Y.get(m).doubleValue();
                }
                double aux = this.P.get(n).doubleValue();
                Y.set(n, B.get(i, (int) aux).doubleValue() - t);
            }
            //Solving Ux = y
            for (n = size - 1; n >= 1; n--) {
                t = 0;
                for (m = n + 1; m < size; m++) {
                    t += matrix.get(n, m).doubleValue() * X.get(m).intValue();
                }
                X.set(n, (Y.get(n).doubleValue() - t) / (matrix.get(n, n).doubleValue()));
            }

            for (j = 1; j < size; j++) {
                B.set(i, j, X.get(j));
            }
        }
        /* Copying transpose of 'B' into 'LU', which would the inverse of 'A'. */
        for (i = 1; i < size; i++) {
            for (j = 1; j < size; j++) {
                matrix.set(i, j, B.get(j, i));
            }
        }
        return matrix;
    }

}
