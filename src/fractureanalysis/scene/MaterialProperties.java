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
package fractureanalysis.scene;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

/**
 *
 * @author elidioxg
 */
public class MaterialProperties {

    /**
     *
     * @return
     */
    public static PhongMaterial setPlaneMaterial() {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.WHITE);
        material.setSpecularColor(Color.WHITE);
        return material;
    }

    /**
     *
     * @return
     */
    public static PhongMaterial setFractureMaterial() {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLUE);
        material.setSpecularColor(Color.GREEN);
        return material;
    }

    /**
     * 
     * @param index
     * @return 
     */
    public static PhongMaterial setAxisMaterial(int index) {
        final PhongMaterial axis = new PhongMaterial();
        switch (index) {
            case 0:
                axis.setDiffuseColor(Color.DARKRED);
                axis.setSpecularColor(Color.RED);
                break;
            case 1:
                axis.setDiffuseColor(Color.DARKGREEN);
                axis.setSpecularColor(Color.GREEN);
                break;
            case 2:
                axis.setDiffuseColor(Color.DARKBLUE);
                axis.setSpecularColor(Color.BLUE);
                break;
        }
        return axis;
    }
}
