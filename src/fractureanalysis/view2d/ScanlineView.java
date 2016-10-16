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
package fractureanalysis.view2d;

/**
 *
 * @author elidioxg
 */
public class ScanlineView {
    /**
     * This class represents the field of view of a 2D representation of
     * a scanline.
     */

    private double viewWidth = 1000;
    private double viewHeight = 200;
    private double apMult = 1;
    private double spMult = 1;
    private double fractureSize = 100;
    private double fracMinWidth = 1;

    public void setViewWidth(double value){
        this.viewWidth = value;
    }
    public double getViewWidth() {
        return this.viewWidth;
    }

    public void setViewHeight(double value){
        this.viewHeight = value;
    }
            
    public double getViewHeight() {
        return this.viewHeight;
    }

    public void setApMult(double value){
        this.apMult = value;
    }
    
    public double getApMult() {
        return this.apMult;
    }

    public void setSpMult(double value){
        this.spMult = value;
    }
    public double getSpMult() {
        return this.spMult;
    }
    
    public void setFractureSize(double size){
        this.fractureSize=size;
    }
    
    public double getFractureSize(){
        return this.fractureSize;
    }
    
    public void setFracMinWidth(double value){
        this.fracMinWidth = value;
    }
    
    public double getFracMinWidth(){
        return this.fracMinWidth;
    }

}
