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

import javafx.scene.paint.Material;
import javafx.scene.shape.Cylinder;

/**
 *
 * @author elidioxg
 */
public class Lithology extends Cylinder {
    
    private String name= "";
    private DrillingHole owner;
    private double init;
    private double end;    

    private double diameter = 0.;
    private double deep = 0.;
    private double posX = 0.;
    private double posY = 0.;
    private double posZ = 0.;

    public Lithology(String name, DrillingHole dh, 
            double init, double end, Material material) {
        this.name = name;
        owner = dh;
        this.init = init;
        this.end = end;
        diameter = dh.getDiameter()+1;
        deep = end-init;
        posX = dh.getX();
        posY = dh.getY()+init;
        posZ = dh.getZ();
        setMaterial(material);
        
        setHeight(deep);
        setRadius(diameter);
        setTranslateX(posX);
        setTranslateY(posY+(end-init)/2);
        setTranslateZ(posZ);
        dh.getLithology().add(this);
    }
    
    public String getName(){
        return this.name;
    }
    
    public double getInit(){
        return this.init;
    }
    
    public double getEnd(){
        return this.end;
    }        

}
