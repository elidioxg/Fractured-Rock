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
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author elidioxg
 */
public class Spearman {
    //TODO: eliminate ArrayList and ObservableList, substitute for vector, use Comparator
    /**
     *
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    public static double calc(ArrayList<Double> a, ArrayList<Double> b) throws Exception {
        double result = Double.NaN;
        if (a.size() != b.size()) {
            throw new Exception("Arrays must have same size.");
        } else {
            ObservableList<Double> olA = FXCollections.observableArrayList(a);
            ObservableList<Double> olB = FXCollections.observableArrayList(b);

            ArrayList<Row> table = new ArrayList();
            for (int i = 0; i < a.size(); i++) {
                Row row = new Row(olA.get(i), olB.get(i));
                table.add(row);
            }
            FXCollections.sort(olA);
            for (int i = 0; i < a.size(); i++) {
                for (int j = 0; j < a.size(); j++) {
                    if (Objects.equals(table.get(i).xValue, olA.get(j))) {
                        table.get(i).setXRank(j + 1);
                        break;
                    }
                }
            }
            FXCollections.sort(olB);
            for (int i = 0; i < b.size(); i++) {
                for (int j = 0; j < b.size(); j++) {
                    if (Objects.equals(table.get(i).yValue, olB.get(j))) {
                        table.get(i).setYRank(j + 1);
                        break;
                    }
                }
            }
            //TODO: aqui ficarão os procedimentos que procuram por valores repetidos e modificam            
//            for (int k = 0; k < a.size(); k++) {
//                System.out.println("x: " + table.get(k).getX(k) + "  y: " + table.get(k).getY(k)
//                        + "  xi: " + table.get(k).getXRank() + "  yi: " + table.get(k).getYRank());
//            }
            double sum = 0.;
            for (int i = 0; i < a.size(); i++) {
                sum += Math.pow(table.get(i).getXRank() - table.get(i).getYRank(), 2);
            }
            result = 1 - ((6 * sum) / (Math.pow(a.size(), 3) - a.size()));
        }
        return result;
    }

    private static class Row {

        private Double xValue;
        private Double yValue;
        private int xRank;
        private int yRank;

        public Row(Double x, Double y) {
            xValue = x;
            yValue = y;
        }

        public Double getX(int index) {
            return this.xValue;
        }

        public Double getY(int index) {
            return this.yValue;
        }

        public int getXRank() {
            return this.xRank;
        }

        public int getYRank() {
            return this.yRank;
        }

        public void setXRank(int rank) {
            this.xRank = rank;
        }

        public void setYRank(int rank) {
            this.yRank = rank;
        }
    }
}
