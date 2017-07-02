/*
 * Copyright (C) 2017 elidioxg
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

import fractureanalysis.scene.MaterialProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Cylinder;

/**
 *
 * @author elidioxg
 */
public class DrillingHole {

    private double diameter = 10.;
    private double deep = 100.;
    private double posX = 0.;
    private double posY = 0.;
    private double posZ = 0.;
    private ObservableList<Lithology> lit;
    private ObservableList<FracturePlane> frac;    
    
    public DrillingHole(double diameter, double deep, double x,
            double y, double z) {
        this.diameter = diameter;
        this.deep = deep;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        lit = FXCollections.observableArrayList();
        frac = FXCollections.observableArrayList();
    }

    public DrillingHole(double diameter, double deep, double x,
            double y, double z, ObservableList lithology, ObservableList fractures) {
        this.diameter = diameter;
        this.deep = deep;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        lit= lithology;
        frac = fractures;
    }
    
    public Cylinder drawWell(){
        if(this.diameter <=1){ this.diameter=2;}
        Cylinder c = new Cylinder(this.diameter-1., this.getDeep());
        c.setTranslateX(this.getX());
        c.setTranslateY(this.getY()+(this.getDeep()/2));
        c.setTranslateZ(this.getZ());
        c.setMaterial(MaterialProperties.setWellMaterial());
        return c;
    }
    
    public ObservableList<FracturePlane> getFracturePlane3Ds() throws Exception{
        return this.frac;
    }
    
    public ObservableList<Lithology> getLithologys(){
        return this.lit;
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
    
    public ObservableList<Lithology> getLithology(){
        return this.lit;
    }
    
    public ObservableList<FracturePlane> getFractures(){
        return this.frac;
    }
}
