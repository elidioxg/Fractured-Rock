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

/**
 *
 * @author elidioxg
 */
public class Azimuth {
    /**
     * This class should make work with azimuths easier.
     * Automatically defines the short and long quadrants according with
     * azimuth value.
     */
    private final double maxAz = 360.;    
    
    /**
     * Eight kinds of sector
     * 
     */
    //TODO: aumentar o numero de setores
    private enum Sector {
        N, S, E, W, NE, NW, SE, SW;
    }        
    
    private double azimuth = 0.;
    private double shortAz;
    private double longAz;
    
    private String shortStrAz;
    private String longStrAz;
    
    private Sector shortSector;
    private Sector longSector;
    
    public Azimuth(){
        setAz(0.);
    }        
    
    public double getShortAz(){
        return this.shortAz;
    }
    
    public double getLongAz(){
        return this.longAz;
    }
    /**
     * Define the short and long sector according to azimuth
     * Obs: shortSector always will be closer to N than longSector
     * 
     * @param az 
     */
    private void setSector(double az){
        if(az==0){
            this.shortSector =  Sector.N;
            this.longSector = Sector.S;
        }
        if(az==360){
            this.shortSector = Sector.N;
            this.longSector = Sector.S;
        }
        if(az==90){
            this.shortSector = Sector.E;
            this.longSector = Sector.W;
        }
        if(az==180){
            this.shortSector = Sector.N;
            this.longSector = Sector.S;
        }
        if(az==270){
            this.shortSector = Sector.E;
            this.longSector = Sector.W;
        }
        if(az>0 && az<90){
            this.shortSector = Sector.NE;
            this.longSector = Sector.SW;
        }
        if(az>90 && az<180){
            this.shortSector = Sector.NW;
            this.longSector = Sector.SE;
        }
        if(az>180 && az<270){
            this.shortSector = Sector.NE;
            this.longSector = Sector.SW;
        }
        if(az>270 && az<360){
            this.shortSector = Sector.NW;
            this.longSector = Sector.SE;
        }       
        //TODO: if az>360 make smaller than 360
    }
    
    public Sector getShortSector(){
        return this.shortSector;
    }
    
    public Sector getLongSector(){
        return this.longSector;
    }
    
    private void setShortAz(double az){
        this.shortAz = az;
        this.longAz = az+180.;
        setSector(az);
    }
    
    private void setLongAz(double az){
        this.longAz = az;
        this.shortAz = az-180.;
        setSector(shortAz);
    }
    
    public void setAz(double az){
        this.azimuth = az;
        if(az==maxAz){
            az=0.;
        }
        if(az>maxAz){
            int it = (int)(az/maxAz);
            az = az - (maxAz*it);
        }
        if(az>=180){
            az = az-180.;
        }        
        setShortAz(az);
        setLongAz(az +180.);
        setSector(az);
    }    
    
    public double getAz(){
        return this.azimuth;
    }
}
