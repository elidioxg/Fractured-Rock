/*
 * Copyright (C) 2017 fedora
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
package fractureanalysis.view3d;

import javafx.scene.shape.Cylinder;

/**
 *
 * @author fedora
 */
public class Well3D extends Cylinder{
    private double diameter = 10.;
    private double deep = 100.;
    private double posX = 0.;
    private double posY = 0.;
    private double posZ = 0.;
    
    public Well3D(){
    
    }
    
    public Well3D(double diameter, double deep, double x, double y, double z){
        this.diameter = diameter;
        this.deep = deep;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    
    }
    
    public void setDiameter(double diameter){
        this.diameter = diameter;
    }
    
    public void setDeep(double deep){
        this.deep = deep;
    }
    
    public void setPosX(double posX){
        this.posX = posX;
    }
    
    public void setPosY(double posY){
        this.posY = posY;
    }
    
    public void setPosZ(double posZ){
        this.posZ = posZ;
    }
    
    public double getDiameter(){
        return this.diameter;
    }
    
    public double getDeep(){
        return this.deep;
    }
    
    public double getX(){
        return this.posX;
    }
    
    public double getY(){
        return this.posY;        
    }
    
    public double getZ(){
        return this.posZ;
    }
}
