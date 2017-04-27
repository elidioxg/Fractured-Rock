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
package fractureanalysis.data;

import fractureanalysis.Vectors.Vector;
import fractureanalysis.Matrices.Matrix;
import fractureanalysis.util.ParseNumber;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OpenDataset {

    /**
     * Open data file as a vector
     *
     * @param fileName
     * @param separator
     * @param column
     * @param hasHeader
     * @return
     * @throws java.lang.Exception
     */
    public static Vector openCSVFileToVector(String fileName, String separator,
            int column, boolean hasHeader) throws Exception {
        BufferedReader br = null;
        String line = null;
        int size = 0;
        try {
            br = new BufferedReader(new FileReader(fileName));
            if (hasHeader) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    size++;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        Vector vector = new Vector();
        Number[] number = new Number[size];
        line = null;
        String[] lineValues;
        try {
            br = new BufferedReader(new FileReader(fileName));
            if (hasHeader) {
                line = br.readLine();
            }
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lineValues = line.split(separator);
                    final int ii = i;
                    number[i] = new Number() {
                        @Override
                        public int intValue() {
                            return (int) number[ii];
                        }

                        @Override
                        public long longValue() {
                            return (long) number[ii];
                        }

                        @Override
                        public float floatValue() {
                            return (float) number[ii];
                        }

                        @Override
                        public double doubleValue() {
                            return (double) number[ii];
                        }
                    };
                    if (lineValues[column].trim().isEmpty()) {
                        number[i] = Double.NaN;
                    } else {
                        String aux = lineValues[column].trim().
                                replace(",", ".").replace("\"", "");
                        int count = aux.length() - aux.replace(".", "").length();
                        if (count > 1) {
                            number[i] = Double.NaN;
                        } else {
                            number[i] = Double.parseDouble(aux);
                        }
                    }
                    i++;
                }
            }
            vector.setData(number, i);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return vector;
    }

    /**
     * Open data file as a matrix
     *
     * @param fileName
     * @param separator
     * @param hasHeader
     * @return
     */
    public static Matrix openCSVFileToMatrix(String fileName, String separator,
            boolean hasHeader) {
        BufferedReader br = null;
        String line = null;
        int xSize = 0;
        int ySize = 0;
        try {
            br = new BufferedReader(new FileReader(fileName));
            if (hasHeader) {
                br.readLine();
            }

            line = br.readLine();
            ySize++;
            String[] lineValues = line.split(separator);
            xSize = lineValues.length;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    ySize++;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        Matrix matrix = new Matrix();
        Number[][] number = new Number[xSize][ySize];
        line = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            if (hasHeader) {
                br.readLine();
            }
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {

                    String[] lineValues = line.split(separator);
                    for (int j = 0; j < lineValues.length; j++) {
                        final int ii = i;
                        final int jj = j;
                        number[j][i] = new Number() {
                            @Override
                            public int intValue() {
                                return (int) number[jj][ii];
                            }

                            @Override
                            public long longValue() {
                                return (long) number[jj][ii];
                            }

                            @Override
                            public float floatValue() {
                                return (float) number[jj][ii];
                            }

                            @Override
                            public double doubleValue() {
                                return (double) number[jj][ii];
                            }
                        };
                        if (lineValues[j].trim().isEmpty()) {
                            number[j][i] = Double.NaN;
                        } else {
                            number[j][i] = Double.parseDouble(lineValues[j].trim().
                                    replace(",", ".").replace("\"", ""));
                        }
                    }
                    i++;
                }
            }
            matrix.setData(number, xSize, ySize);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return matrix;
    }

    /**
     * Open a data file as a ArrayList with Double values
     *
     * @param fileName
     * @param separator
     * @param column
     * @param hasHeader
     * @return
     */
    public static ArrayList<Double> openCSVFileToDouble(String fileName, String separator,
            int column, boolean hasHeader) {
        BufferedReader br = null;
        ArrayList<Double> values = new ArrayList<>();
        String line = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            if (hasHeader) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] lineValues = line.split(separator);
                    values.add(Double.valueOf(lineValues[column].trim().
                            replace(",", ".").replace("\"", "")));
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return values;
    }

    /**
     * Open a file in Geoeas format convenction to a matrix
     *
     * @param filename
     * @param sep
     * @return
     * @throws java.io.IOException
     */
    public static Matrix openGeoeasToMatrix(String filename, String sep) throws IOException {
        BufferedReader br;
        int fileRows = DatasetProperties.getRowCount(filename, true);
        Matrix result = new Matrix();
        try {
            br = new BufferedReader(new FileReader(filename));
            br.readLine(); //Jump title of file
            String[] nVar = br.readLine().split(sep); //Get the number of columns
            int cols = Integer.valueOf(nVar[0]);
            int rows = fileRows - cols - 2;
            for (int i = 0; i < cols; i++) {
                br.readLine(); // jump the headers
            }
            Number[][] number = new Number[cols][rows];
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    break;
                } else {
                    while (line.subSequence(0, 1).equals(" ")) {
                        line = line.substring(1);
                    }
                    while (line.subSequence(line.length() - 1, line.length()).equals(" ")) {
                        line = line.substring(line.length());
                    }
                    //if separator is blank char
                    if (sep.trim().equals("")) {
                        line = line.replace(sep + sep + sep + sep, sep);
                        line = line.replace(sep + sep + sep, sep);
                        line = line.replace(sep + sep, sep);
                    }
                    String lineValues[] = line.split(sep);
                    for (int j = 0; j < lineValues.length; j++) {
                        final int ii = i;
                        final int jj = j;
                        number[j][i] = new Number() {
                            @Override
                            public int intValue() {
                                return (int) number[jj][ii];
                            }

                            @Override
                            public long longValue() {
                                return (long) number[jj][ii];
                            }

                            @Override
                            public float floatValue() {
                                return (float) number[jj][ii];
                            }

                            @Override
                            public double doubleValue() {
                                return (double) number[jj][ii];
                            }
                        };
                        number[j][i] = ParseNumber.parse(lineValues[j]);
                    }
                    i++;
                }
            }
            result.setData(number, cols, rows);
        } catch (IOException | NumberFormatException e) {

        }
        return result;
    }

    /**
     * Open a file in Geoeas format convenction to a vector
     *
     * @param filename
     * @param sep
     * @param column
     * @return
     * @throws java.io.IOException
     */
    public static Vector openGeoeasToVector(String filename, String sep,
            int column) throws IOException, Exception {
        BufferedReader br = null;
        //count the lines, ignoring the empty ones
        int fileRows = DatasetProperties.getRowCount(filename, true);
        Vector result = new Vector();
        try {
            br = new BufferedReader(new FileReader(filename));
            br.readLine(); //Jump title of file
            String[] nVar = br.readLine().split(" ");
            //Get the number of columns
            int cols = Integer.valueOf(nVar[0]);
            if (cols < column) {
                throw new Exception("Column index is higher than number of columns in file.");
            }
            int rows = fileRows - cols - 2;
            for (int i = 0; i < cols; i++) {
                br.readLine(); // jump the headers
            }
            Number[] number = new Number[rows];
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String theNumber = null;
                if (line.trim().isEmpty()) {
                    //TODO: how to handle with empty lines
                    break;
                } else {
                    while (line.subSequence(0, 1).equals(" ")) {
                        line = line.substring(1);
                    }
                    while (line.subSequence(line.length(), line.length()).equals(" ")) {
                        line = line.substring(line.length());                        
                    }
                    // if the request column is the first
                    if (column == 0) {
                        int c = 1;
                        while (!line.subSequence(c, c + 1).equals(sep)) {                            
                            c++;
                        }
                        theNumber = line.substring(0, c);                        
                    } else //if the requested column is the last one
                    if (column == (cols - 1)) {
                        int c = line.length();
                        while (line.subSequence(c - 1, c).equals(sep)) {
                            c--;
                        }
                        theNumber = line.substring(c - 1, line.length());
                    } else //the requested column is not the first nor the last
                    {
                        int colIndex = 0;
                        for (int n = 0; n < line.length(); n++) {
                            if (line.subSequence(n, n + sep.length()).equals(sep)) {
                                //jump other blank spaces, if separator is blank space
                                while (line.subSequence(n, n + sep.length()).equals(sep)) {
                                    n++;
                                }
                                colIndex++;
                                if (colIndex == column) {
                                    //now, find the end of this column
                                    for (int m = n; n < line.length(); m++) {
                                        if (line.subSequence(m, m + sep.length()).equals(sep)) {
                                            theNumber = line.subSequence(n, m).toString();
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                final int ii = i;
                number[i] = new Number() {
                    @Override
                    public int intValue() {
                        return (int) number[ii];
                    }

                    @Override
                    public long longValue() {
                        return (long) number[ii];
                    }

                    @Override
                    public float floatValue() {
                        return (float) number[ii];
                    }

                    @Override
                    public double doubleValue() {
                        return (double) number[ii];
                    }
                };
                number[i] = ParseNumber.parse(theNumber);
                i++;
            }
            result.setData(number, rows);
        } catch (IOException | NumberFormatException e) {

        } finally {
            if (br != null) {
                br.close();
            }
        }
        return result;
    }

}
