package fractureanalysis.data;

import fractureanalysis.Matrices.Vector;
import fractureanalysis.Vectors.Matrix;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class OpenDataset {

    /**
     *
     * @param fileName
     * @param separator
     * @param column
     * @param hasHeader
     * @param size
     * @return
     */
    /**
     * public static Vector openCSVFileToVector(String fileName, String
     * separator, int column, boolean hasHeader, int size) { BufferedReader br =
     * null; Vector vector = new Vector(); Number[] number = new Number[size];
     * String line = null; try { br = new BufferedReader(new
     * FileReader(fileName)); if (hasHeader) { br.readLine(); } int i = 0; while
     * ((line = br.readLine()) != null) { String[] lineValues =
     * line.split(separator); final int ii = i; number[i] = new Number() {
     *
     * @Override public int intValue() { return (int) number[ii]; }
     *
     * @Override public long longValue() { return (long) number[ii]; }
     *
     * @Override public float floatValue() { return (float) number[ii]; }
     *
     * @Override public double doubleValue() { return (double) number[ii]; } };
     * try { number[i] = NumberFormat.getInstance().parse(lineValues[column]); }
     * catch (ParseException e) { e.printStackTrace(); } i++; }
     * vector.setData(number, i); } catch (FileNotFoundException e) { } catch
     * (IOException e) { } finally { if (br != null) { try { br.close(); } catch
     * (IOException e) { } } }
     *
     * return vector; }
     *
     */
    /**
     *
     * @param fileName
     * @param separator
     * @param column
     * @param hasHeader
     * @return
     */
    public static Vector openCSVFileToVector(String fileName, String separator,
            int column, boolean hasHeader) {
        BufferedReader br = null;
        Vector vector = new Vector();
        String line = null;
        int size = 0;
        try {
            br = new BufferedReader(new FileReader(fileName));
            if (hasHeader) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                size++;
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
                    try {
                        number[i] = NumberFormat.getInstance().parse(lineValues[column].trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                i++;
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
                ySize++;
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
                        try {
                            number[j][i] = NumberFormat.getInstance().parse(lineValues[j].trim());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                i++;

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

    /*public static ArrayList<Double> openCSVFileToDouble(String fileName, String separator,
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
                String[] lineValues = line.split(separator);
                values.add(Double.valueOf(lineValues[column].trim()));
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
     */
    public ArrayList[][] openCSVFileToArray(String fileName, String separator,
            int indexId, int indexSp, int indexAp) {
        ArrayList[][] array = null;
        BufferedReader br = null;
        List<Double> values = new ArrayList<>();
        String line = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            int countLines = 0;
            while (br.readLine() != null) {
                countLines++;
            }
            array = new ArrayList[countLines][];
            br.reset();
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] lineValues = line.split(separator);
                array[count][0] = new ArrayList();
                array[count][0].add(lineValues[indexId].trim());
                array[count][1] = new ArrayList();
                array[count][1].add(lineValues[indexSp].trim());
                array[count][2] = new ArrayList();
                array[count][2].add(lineValues[indexAp].trim());
                count++;
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
        return array;
    }
}
