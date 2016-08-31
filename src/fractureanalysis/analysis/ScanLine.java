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
package fractureanalysis.analysis;

import java.util.ArrayList;

/**
 *
 * @author elidioxg
 */
public class ScanLine {

    private ArrayList<Double> ap = new ArrayList();
    private ArrayList<Double> sp = new ArrayList();
    private double lenght = Double.NaN;
    private int fracturesCount = 0;
    //distance from scanline origin:
    private ArrayList<Double> distance = new ArrayList();

    /**
     * 
     * @param fractures 
     */
    public ScanLine(ArrayList<Fracture> fractures) {
        if (fractures != null) {
            this.lenght = 0.;
            for (int i = 0; i < fractures.size(); i++) {
                ap.add(fractures.get(i).getAperture());
                sp.add(fractures.get(i).getSpacement());                
                this.lenght += fractures.get(i).getAperture() +
                        fractures.get(i).getSpacement();
                this.distance.add(this.lenght);
                this.fracturesCount += 1;
            }
        }
    }

    public double getLenght() {
        return this.lenght;
    }

    public ArrayList<Double> getApList() {
        return this.ap;
    }

    public ArrayList<Double> getSpList() {
        return this.sp;
    }

    public ArrayList<Double> getDistanceList() {
        return this.distance;
    }

    public int fracturesCount() {
        return this.fracturesCount;
    }

}
