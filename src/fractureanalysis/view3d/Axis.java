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

import fractureanalysis.scene.MaterialProperties;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author elidioxg
 */
public class Axis {
    static int slAxis = 50;

    /**
     * This class is responsible for drawing axis on 2D and 3D views
     */
    /**
     * Deafult width of axis
     */
    static int defaultWidth = 5;
    /**
     * Default lenght of axis
     */
    static int defaultLenght = 15000;

    /**
     * Add the X, Y and Z axis. The axis consists on three cylinders with width
     * and height defined by constants.
     *
     * @return
     */
    public static Cylinder[] addAxis() {
        Cylinder[] c = new Cylinder[3];
        c[0] = new Cylinder(defaultWidth, defaultLenght);
        c[0].getTransforms().add(new Rotate(90., Rotate.X_AXIS));
        c[0].setMaterial(MaterialProperties.setAxisMaterial(0));

        c[1] = new Cylinder(defaultWidth, defaultLenght);
        c[1].getTransforms().add(new Rotate(90., Rotate.Y_AXIS));
        c[1].setMaterial(MaterialProperties.setAxisMaterial(1));

        c[2] = new Cylinder(defaultWidth, defaultLenght);
        c[2].getTransforms().add(new Rotate(90., Rotate.Z_AXIS));
        c[2].setMaterial(MaterialProperties.setAxisMaterial(2));
        return c;
    }

    public static Box addScanlineAxis(double slSize) {        
        Box b = new Box(slAxis, slAxis, slSize*1000);
        b.getTransforms().add(new Translate((slSize*1000) / 2, slAxis/2, 0));
        b.getTransforms().add(new Rotate(90., Rotate.Y_AXIS));
        b.setMaterial(MaterialProperties.setAxisMaterial(3));
        return b;
    }

}
