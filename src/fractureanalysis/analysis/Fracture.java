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

/**
 *
 * @author elidioxg
 */
public class Fracture {
    final private double aperture;
    final private double spacement;
    private int cumulativeNumber;
    private double normalizedValue;
    
    public Fracture(double ap, double sp){
        this.aperture=ap;
        this.spacement=sp;
    }
    
    public void setNormalizedValue(double value){
        this.normalizedValue=value;
    }
    
    public double getNormalizedValue(){
        return this.normalizedValue;
    }
    
    public int getCumulativeNumber(){
        return this.cumulativeNumber;
    }
    public void setCumulativeNumber(int num){
        this.cumulativeNumber=num;
    }
    public double getAperture(){
        return this.aperture;
    }
    public double getSpacement(){
        return this.spacement;
    }
}
