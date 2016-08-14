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
package fractureanalysis.model;

import java.util.ArrayList;

/**
 *
 * @author elidioxg
 */
public class AnalysisFile extends CSVFile {

    private ArrayList<Double> ap;
    private ArrayList<Double> sp;

    //This variable defines the distance from first point
    private ArrayList<Double> distances = new ArrayList();

    private void setDistance(ArrayList<Double> sp) {
        double distance = 0.;
        for (int i = 0; i < sp.size(); i++) {
            if (i == 0) {
                //this.distances.add(distance);
            } else {
                for (int j = 0; j < i; j++) {
                    distance += this.sp.get(j);
                }
            }
            if (distance != 0) {
                this.distances.add(distance);
            }
        }
        System.out.println(" **  Distances:    " + this.distances);
    }

    public ArrayList<Double> getArrayDistances() {
        return this.distances;
    }

    public void setArrayAp(ArrayList<Double> values) {
        this.ap = values;
    }

    public ArrayList<Double> getArrayAp() {
        return this.ap;
    }

    public void setArraySp(ArrayList<Double> values) {
        this.sp = values;
        setDistance(values);
    }

    public ArrayList<Double> getArraySp() {
        return this.sp;
    }
}
