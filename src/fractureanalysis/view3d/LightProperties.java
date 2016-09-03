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

import javafx.scene.AmbientLight;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;

/**
 *
 * @author elidioxg
 */
public class LightProperties {

    static int lightPosition = 500;
    static int lightScale = 10;

    public static PointLight setPointLight(Color color,
            double posX, double posY) {
        PointLight l = new PointLight();
        l.setColor(color);
        l.setLayoutX(posX);
        l.setLayoutY(posY);
        l.setVisible(true);
        return l;
    }

    public static AmbientLight setAmbientLight(Color color) {
        AmbientLight l = new AmbientLight();
        l.setColor(color);
        //l.setLayoutX(lightScale);
        //l.setLayoutY(lightScale);
        //l.setTranslateX(lightPosition);
        //l.setTranslateY(lightPosition);
        l.setTranslateZ(-lightPosition);
        l.setVisible(true);
        return l;
    }

}
