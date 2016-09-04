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
package fractureanalysis.Vectors;

import fractureanalysis.Matrices.Vector;

/**
 *
 * @author elidioxg
 */
public class Matrix {

    private int lines = 0;
    private int columns = 0;
    private Number[][] data;

    public Matrix(){
    
    }
    
    public Matrix(int columns, int lines) {
        data = new Number[columns][];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                final int ii = i;
                final int jj = j;
                data[i][j] = new Number() {
                    @Override
                    public int intValue() {
                        return (int) data[ii][jj];
                    }

                    @Override
                    public long longValue() {
                        return (long) data[ii][jj];
                    }

                    @Override
                    public float floatValue() {
                        return (float) data[ii][jj];
                    }

                    @Override
                    public double doubleValue() {
                        return (double) data[ii][jj];
                    }
                };
            }
        }
    }
    
    public void set(int column, int line, Number value){
        this.data[column][line] = value;
    }

    public void fillZeros(int size) {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                final int ii = i;
                final int jj = j;
                data[i][j] = new Number() {
                    @Override
                    public int intValue() {
                        return (int) data[jj][ii];
                    }

                    @Override
                    public long longValue() {
                        return (long) data[jj][ii];
                    }

                    @Override
                    public float floatValue() {
                        return (float) data[jj][ii];
                    }

                    @Override
                    public double doubleValue() {
                        return (double) data[jj][ii];
                    }
                };
                data[j][i] = 0;
            }
        }

    }

    public void setData(Number[][] data, int columns, int lines) {
        this.data = data;
        this.lines = lines;
        this.columns = columns;
    }
    
    public Number get(int column, int line){
        return this.data[column][line];
    }

    public void joinHorizontalVectors(Vector vector1, Vector vector2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void joinVerticalVectors(Vector vector1, Vector vector2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Number[][] getMatrix() {
        return this.data;
    }

    public int getColumnsCount() {
        return this.columns;
    }

    public int getLinesCount() {
        return this.lines;
    }

    public Vector getColumn(int index) {        
        Number[] data = new Number[lines];
        for(int i = 0; i< lines; i++){
            final int ii = i;
            data[i] = new Number() {
                @Override
                public int intValue() {
                    return (int) data[ii]; 
                }

                @Override
                public long longValue() {
                    return (long) data[ii];
                }

                @Override
                public float floatValue() {
                    return (float) data[ii];
                }

                @Override
                public double doubleValue() {
                    return (double) data[ii];
                }                
            };
            data[i] = this.data[index][i];            
        }
        Vector vector = new Vector(data, lines);
        return vector;
    }

    public Vector getLine(int index) {
        return null;
    }
}

