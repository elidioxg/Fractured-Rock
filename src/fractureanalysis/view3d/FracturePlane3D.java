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
package fractureanalysis.view3d;

import javafx.scene.shape.Box;

/**
 *
 * @author elidioxg
 */
public class FracturePlane3D extends Box {
    
    private Azimuth az = new Azimuth();    
    private double aperture = 200;
    private double deepIntensity; 
    private Position3D position = new Position3D();
    
    private double planeHeight = 200.;
    
    public FracturePlane3D(double ap, double distance) throws Exception{
        this.az.setAz(0);
        this.deepIntensity = 0.;
        this.setPosX(distance);
        this.aperture = ap;
        this.setDepth(aperture);
        this.setWidth(aperture);
        this.setHeight(planeHeight);
    }
    
    public FracturePlane3D(double lenght, double direction, double deepIntensity, 
            double posX, double posY, double posZ) throws Exception{
        this.aperture = lenght;
        this.az.setAz(direction);       
        this.deepIntensity = deepIntensity;   
        this.setPosXYZ(posX, posY, posZ);        
        this.setDepth(lenght);
        this.setHeight(planeHeight);
    }

    public void setDirection(double direction){        
        this.az.setAz(direction);
    }
    
    public double getDirection(){
        return this.az.getAz();
    }     
    
    public double getOrientation(){
        return this.az.getShortAz()+90.;
    }
    
    public void setDeepIntensity(double intensity){
        this.deepIntensity = intensity;
    }
    
    public double getDeepIntensity(){
        return this.deepIntensity;
    }    
    
    public void setPosX(double position) throws Exception{
        this.position.setX(position);
        this.setTranslateX(position);
    }
    
    public double getPosX(){
        return this.position.getX();
    }
    
    public void setPosY(double position) throws Exception{
        this.position.setY(position);
        setTranslateY(position);
    }
    
    public double getPosY(){
        return this.position.getY();
    }
    
    public void setPosZ(double position) throws Exception{
        this.position.setZ(position);
        setTranslateZ(position);
    }
    
    public double getPosZ(){
        return this.position.getZ();
    }
    
    public void setPosXYZ(double x, double y, double z) throws Exception{
        this.setPosX(x);
        this.setPosY(y);
        this.setPosZ(z);
    }    
    
}
