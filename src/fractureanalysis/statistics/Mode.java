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
package fractureanalysis.statistics;

import fractureanalysis.Vectors.Vector;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author elidioxg
 */
public class Mode {

    /**
     * Get the number repeated more in the list.     
     * Returns Double.NaN if there is not repeated values
     * 
     * @param array
     * @return
     */
    public static Double getMode(ArrayList<Double> array) {
        Double result = Double.NaN;
        ObservableList<Double> ol = FXCollections.observableArrayList(array);
        ol.sorted();
        int counter = 0;
        int higherCounter = 0;
        for (int i = 1; i < ol.size(); i++) {
            if (ol.get(i - 1).equals(ol.get(i))) {
                counter++;
            } else {
                if (counter > higherCounter) {
                    result = ol.get(i - 1);
                    higherCounter = counter;
                }
                counter = 0;
            }
        }
        return result;
    }

    /**
     * Get the most repeated number of a vector.
     * Returns Double.NaN if there is not repeated values
     *
     * @param vector
     * @return
     */
    public static Double getMode(Vector vector) {
        Double result = Double.NaN;
        vector.sort();
        int counter = 0;
        int higherCounter = 0;
        for (int i = 1; i < vector.size(); i++) {
            if (vector.get(i - 1).equals(vector.get(i))) {
                counter++;
            } else {
                if (counter > higherCounter) {
                    result = vector.get(i - 1).doubleValue();
                    higherCounter = counter;
                }
                counter = 0;
            }
        }
        return result;
    }

}
