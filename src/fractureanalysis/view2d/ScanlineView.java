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
     * 
     * The scanline view basically consists in a rectangular plane with
     * lines representing each fracture. 
     * The line width is defined as the aperture value.
     * 
     */

    /**
     * The width of the scanline view.
     */
    private double viewWidth = 1000;
    /**
     * Height of the scanline view.
     */
    private double viewHeight = 200;    
    /**
     * Scale of the aperture value (fracture width).          
     */
    private double apMult = 1;
    /**
     * Scale of the spacement value.          
     */
    private double spMult = 1;
    /**
     * The height of the fracture
     */
    private double fractureSize = 100;
    /**
     * In some cases depending the viewWidth, the line representing a 
     * fracture can be smaller than one pixel. This constants is for
     * line be drawn anyway.
     */
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
