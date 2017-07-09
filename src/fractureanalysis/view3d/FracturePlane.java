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

import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Rotate;

/**
 *
 * @author elidioxg
 */
public class FracturePlane extends Ellipse {

    private Azimuth az = new Azimuth();
    /**
     * Deep intensity of the fracture
     */
    private double deepIntensity;
    /**
     * Position of the plane on scene
     */
    private final Position3D position = new Position3D();

    private final double planeHeight = 10;    

    public FracturePlane(DrillingHole owner, double direction,
            double deepIntensity, double posY) throws Exception {
        this.az.setAz(direction);
        this.deepIntensity = deepIntensity;        
        this.setPosX(owner.getX());
        this.setPosY(owner.getY() + posY);
        this.setPosZ(owner.getZ());
        this.setRadiusX(owner.getDiameter()*planeHeight);
        this.setRadiusY(owner.getDiameter()*planeHeight);

        this.getTransforms().addAll(
                new Rotate(direction, Rotate.Z_AXIS),
                new Rotate(90.+deepIntensity, Rotate.X_AXIS));
    }

    public void setDirection(double direction) {
        this.az.setAz(direction);
    }

    public double getDirection() {
        return this.az.getAz();
    }

    public double getOrientation() {
        return this.az.getShortAz() + 90.;
    }

    public void setDeepIntensity(double intensity) {
        this.deepIntensity = intensity;
    }

    public double getDeepIntensity() {
        return this.deepIntensity;
    }

    public void setPosX(double position) throws Exception {
        this.position.setX(position);
        this.setTranslateX(position);
    }

    public double getPosX() {
        return this.position.getX();
    }

    public void setPosY(double position) throws Exception {
        this.position.setY(position);
        setTranslateY(position);
    }

    public double getPosY() {
        return this.position.getY();
    }

    public void setPosZ(double position) throws Exception {
        this.position.setZ(position);
        setTranslateZ(position);
    }

    public double getPosZ() {
        return this.position.getZ();
    }

}
