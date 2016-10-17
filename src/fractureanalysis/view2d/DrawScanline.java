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

import fractureanalysis.analysis.ScanLine;
import fractureanalysis.scene.MaterialProperties;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author elidioxg
 */
public class DrawScanline {

    public static void drawScale() {

    }

    /**
     * Draw a background plane for the scanline view.
     * @return 
     */
    public static Box drawPlane() {
        ScanlineView view = new ScanlineView();
        Box box = new Box();
        box.setHeight(view.getViewHeight());
        box.setWidth(view.getViewWidth());
        box.setMaterial(MaterialProperties.setPlaneMaterial());
        return box;
    }

    /**
     * Draw a 2D view representing a scanline
     * @param scanline
     * @return 
     */
    public static Box[] drawFractures(ScanLine scanline) {
        ScanlineView view = new ScanlineView();
        Box[] box = new Box[scanline.fracturesCount()];
        double distance = 0.;
        for (int i = 0; i < scanline.fracturesCount(); i++) {
            distance += scanline.getSpList().get(i);
            System.out.println("Distance: " + distance);
            box[i] = new Box();
            box[i].setHeight(view.getFractureSize());
            box[i].setTranslateX(
                    view.getSpMult() * distance * view.getViewWidth() / scanline.getLenght());
            System.out.println("Ap: " + scanline.getApList().get(i));
            double ap = (view.getApMult()
                    * scanline.getApList().get(i) * view.getViewWidth() / scanline.getLenght());
            if (ap < view.getFracMinWidth()) {
                box[i].setWidth(view.getFracMinWidth());
            } else {
                box[i].setWidth(ap);
            }
            
            System.out.println("Box width: " + box[i].getWidth());
            box[i].setOpacity(200);
            box[i].setMaterial(MaterialProperties.setFractureMaterial());
            distance += scanline.getApList().get(i);
            System.out.println(view.getSpMult() * distance * view.getViewWidth() / scanline.getLenght());
        }
        return box;
    }

}
