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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author elidioxg
 */
public class Kendall {
//TODO: determine the standard error for the equation
    /**
     * 
     * @param a
     * @param b
     * @return
     * @throws Exception 
     */
    public double calc(ArrayList<Double> a, ArrayList<Double> b) throws Exception {
        double result = Double.NaN;
        if (a.size() != b.size()) {
            throw new Exception("Arrays must have same size.");
        } else {
            ObservableList<Double> olA = FXCollections.observableArrayList(a);
            ObservableList<Double> olB = FXCollections.observableArrayList(b);
            ArrayList<Row> table = new ArrayList();
            for (int i = 0; i < a.size(); i++) {
                Row row = new Row(i + 1, a.get(i), b.get(i));
                table.add(row);
            }
            FXCollections.sort(olA);
            for (int i = 0; i < a.size(); i++) {
                for (int j = 0; j < a.size(); j++) {
                    if (Objects.equals(a.get(i), olA.get(j))) {
                        table.get(i).setXRank(a.size() - (j));
                        break;
                    }
                }
            }
            FXCollections.sort(olB);
            for (int i = 0; i < b.size(); i++) {
                for (int j = 0; j < b.size(); j++) {
                    if (Objects.equals(b.get(i), olB.get(j))) {
                        table.get(i).setYRank(b.size() - (j));
                        break;
                    }
                }
            }
            Collections.sort(table, (Row o1, Row o2) -> {
                if (o1.getXRank() > o2.getXRank()) {
                    return 1;
                }
                if (o1.getXRank() < o2.getXRank()) {
                    return -1;
                }
                return 0;
            });

            for (int k = 0; k < a.size(); k++) {
                System.out.println(table.get(k).getIndex() + "  " + table.get(k).getXRank()
                        + "  " + table.get(k).getYRank());
            }

            int S = 0;
            int j = 1;            
            while (j < a.size()) {
                for (int i = j; i < a.size(); i++) {
                    boolean xBigger = table.get(j - 1).getXRank() > table.get(i).getXRank();
                    boolean yBigger = table.get(j - 1).getYRank() > table.get(i).getYRank();
                    if (xBigger == yBigger) {                    
                        S++;
                    } else {                        
                        S--;
                    }
                }                
                j++;
            }            
            result = S/(.5*(a.size())*(a.size()-1));
        }
        return result;
    }

    private class Row {

        private int index;
        private final Double x;
        private final Double y;
        private int xRank;
        private int yRank;

        public Row(int index, Double x, Double y) {
            this.index = index;
            this.x = x;
            this.y = y;
        }

        public int getIndex() {
            return index;
        }

        public void setXRank(int rank) {
            this.xRank = rank;
        }

        public void setYRank(int rank) {
            this.yRank = rank;
        }

        public int getXRank() {
            return this.xRank;
        }

        public int getYRank() {
            return this.yRank;
        }

    }

}

