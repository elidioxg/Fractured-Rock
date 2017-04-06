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

import fractureanalysis.model.GeoeasFormat;
import fractureanalysis.model.Separator;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author elidioxg
 */
public class DatasetProperties {

    //TODO: um único procedimento para pegar todas as propriedades
    /**
     * Get the number of columns on the dataset
     *
     * @param filename
     * @param separator
     * @return Number of columns in dataset file
     */
    public static int getColumnsCount(String filename, String separator) {
        BufferedReader br = null;
        ArrayList<Double> values = new ArrayList<>();
        String line = null;
        String[] lineValues = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            line = br.readLine();
            lineValues = line.split(separator);
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
        if (lineValues != null) {
            return lineValues.length;
        } else {
            return 0;
        }
    }

    /**
     * Get the number of columns
     *
     * @param filename
     * @param separator
     * @return
     */
    public static int getColumnsCount(String filename, Separator separator) {
        BufferedReader br = null;
        ArrayList<Double> values = new ArrayList<>();
        String line = null;
        String[] lineValues = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            line = br.readLine();
            lineValues = line.split(separator.getChar());
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
        if (lineValues != null) {
            return lineValues.length;
        } else {
            return 0;
        }
    }

    /**
     *
     * @param filename
     * @param separator
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<String> getHeaders(String filename, String separator)
            throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        final String headerLine = br.readLine();
        final String[] headerValues = headerLine.split(separator);
        ArrayList<String> result = new ArrayList();
        result.addAll(Arrays.asList(headerValues));
        return result;
    }

    public static ArrayList<String> getHeaders(String filename, Separator separator)
            throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        final String headerLine = br.readLine();
        final String[] headerValues = headerLine.split(separator.getChar());
        ArrayList<String> result = new ArrayList();
        result.addAll(Arrays.asList(headerValues));
        return result;
    }

    public static GeoeasFormat getGoeasProperties(String filename) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        GeoeasFormat gf = new GeoeasFormat();
        gf.setTitle(br.readLine());
        String[] nVar = br.readLine().split(" "); //Get the number of columns
        int cols = Integer.valueOf(nVar[0]);
        gf.setColumnsCount(cols);
        for (int i = 0; i < cols; i++) {
            gf.addHeader(br.readLine());
        }
        int nrows = 0;
        while(br.readLine()!=null){
            nrows++;
        }
        gf.setRowsCount(nrows);
        return gf;
    }

    /**
     * Get the number of rows on dataset
     *
     * @param filename
     * @return
     */
    public static int getRowCount(String filename) {
        BufferedReader br = null;
        int count = 0;
        String line = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                count += 1;
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
        return count;
    }

    /**
     * Quick way to count the lines of a file
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

}
