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
package fractureanalysis.statistics.histogram;

/**
 *
 * @author elidioxg
 */
public class ClassInterval {

    private int index;
    private double inferior;
    private double superior;
    private double centralTeor;
    private int obsFrequency =0;
    private String label= "";
    
    /**
     * Defines the interval classes of the histogram.
     * 
     * @param index Index of the Class Interval
     * @param inferior Inferior limit of Class Interval 
     * @param superior Superior limit of Class Interval
     */
    public ClassInterval(int index, double inferior, double superior){
        this.index=index;
        this.inferior=inferior;
        this.superior=superior;
        this.centralTeor = (superior - inferior);
        this.label= String.format("%.1f", inferior)+" - "+
                String.format("%.1f", superior);
    }
    
    /**
     * Increment the frequency count of the class interval
     */
    public void addFrequency(){
        this.obsFrequency+=1;
    }
    
    public int getObsFrequency(){
        return this.obsFrequency;
    }
    
    public void changeFrequency(int num){
        this.obsFrequency=num;
    }
    public double getInferiorValue(){
        return this.inferior;
    }
    public double getSuperiorValue(){
        return this.superior;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabel(){
        return this.label;
    }
}
