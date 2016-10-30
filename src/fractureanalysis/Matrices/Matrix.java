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
package fractureanalysis.Matrices;

import fractureanalysis.Vectors.Vector;

/**
 *
 * @author elidioxg
 */
public class Matrix {

    private int lines = 0;
    private int columns = 0;
    private Number[][] data;

    public Matrix() {

    }

    /**
     * Create a zero-filled matrix with given size
     *
     * @param columns
     * @param lines
     */
    public Matrix(int columns, int lines) {
        this.lines = lines;
        this.columns = columns;
        data = new Number[columns][lines];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                final int ii = i;
                final int jj = j;
                data[j][i] = new Number() {
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
                data[jj][ii] = 0.;
            }
        }
    }

    /**
     * Create a matrix with all values equal to given value
     *
     * @param columns
     * @param lines
     * @param value
     */
    public Matrix(int columns, int lines, Number value) {
        this.lines = lines;
        this.columns = columns;
        data = new Number[columns][lines];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                final int ii = i;
                final int jj = j;
                data[j][i] = new Number() {
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
                data[jj][ii] = value;
            }
        }
    }

    /**
     * Create a symetrical zero-fill matrix
     *
     * @param size
     */
    public Matrix(int size) {
        data = new Number[columns][lines];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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

    /**
     * Change one value in the matrix
     *
     * @param column
     * @param line
     * @param value
     * @throws java.lang.Exception
     */
    public void set(int column, int line, Number value) throws Exception {
        if (!this.data.equals(null)) {
            if (column < this.columns) {
                if (line < this.lines) {
                    this.data[column][line] = value;
                } else {
                    throw new Exception("Line index higher than columns number");
                }
            } else {
                throw new Exception("Column index higher than columns number");
            }
        } else {
            throw new Exception("Null Matrix");
        }

    }

    public void setData(Number[][] data, int columns, int lines) {
        this.data = data;
        this.lines = lines;
        this.columns = columns;
    }

    public Number get(int column, int line) {
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
        for (int i = 0; i < lines; i++) {
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
