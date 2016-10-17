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

import javafx.scene.AmbientLight;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;

/**
 *
 * @author elidioxg
 */
public class LightProperties {

    /**
     * This is the default position for Ambient Light.
     */
    static int lightPosition = -500;
    /**
     * Scale of the light
     */
    static int lightScale = 10;

    /**
     * Add one single point line on scene.
     * 
     * @param color
     * @param posX
     * @param posY
     * @return 
     */
    public static PointLight setPointLight(Color color,
            double posX, double posY) {
        PointLight l = new PointLight();
        l.setColor(color);
        l.setLayoutX(posX);
        l.setLayoutY(posY);
        l.setVisible(true);
        return l;
    }

    /**
     * Add a ambient light to the scene.
     * 
     * @param color
     * @return 
     */
    public static AmbientLight setAmbientLight(Color color) {
        AmbientLight l = new AmbientLight();
        l.setColor(color);
        //l.setLayoutX(lightScale);
        //l.setLayoutY(lightScale);
        //l.setTranslateX(lightPosition);
        //l.setTranslateY(lightPosition);
        l.setTranslateZ(lightPosition);
        l.setVisible(true);
        return l;
    }

    /**
     * Deafult position of light on 3D views
     */
    static int lightPosition3d = 400;
    /**
     * Default scale of light on 3D views
     */
    static int lightScale3d = 200;
    /**
     * Default ambient color
     */
    static Color ambientLight = Color.GREEN;
    /**
     * Add eight point lights to the scene
     * 
     * @return 
     */
    public static PointLight[] setScene3DLight(){
        PointLight[] l = new PointLight[8];
        l[0] = new PointLight();
        l[0].setColor(Color.ALICEBLUE);
        l[0].setLayoutX(lightScale3d);
        l[0].setLayoutY(lightScale3d);
        l[0].setTranslateX(lightPosition3d);
        l[0].setTranslateY(lightPosition3d);
        l[0].setTranslateZ(lightPosition3d);        
        
        l[1] = new PointLight();
        l[1].setColor(Color.ALICEBLUE);
        l[1].setLayoutX(lightScale3d);
        l[1].setLayoutY(lightScale3d);        
        l[1].setTranslateX(-lightPosition3d);
        l[1].setTranslateY(lightPosition3d);
        l[1].setTranslateZ(lightPosition3d);
        
        l[2] = new PointLight();
        l[2].setColor(Color.ALICEBLUE);
        l[2].setLayoutX(lightScale3d);
        l[2].setLayoutY(lightScale3d);        
        l[2].setTranslateX(lightPosition3d);
        l[2].setTranslateY(-lightPosition3d);
        l[2].setTranslateZ(lightPosition3d);
        
        l[3] = new PointLight();
        l[3].setColor(Color.ALICEBLUE);
        l[3].setLayoutX(lightScale3d);
        l[3].setLayoutY(lightScale3d);        
        l[3].setTranslateX(lightPosition3d);
        l[3].setTranslateY(lightPosition3d);
        l[3].setTranslateZ(-lightPosition3d);
        
        l[4] = new PointLight();
        l[4].setColor(Color.ALICEBLUE);
        l[4].setLayoutX(lightScale3d);
        l[4].setLayoutY(lightScale3d);        
        l[4].setTranslateX(-lightPosition3d);
        l[4].setTranslateY(-lightPosition3d);
        l[4].setTranslateZ(lightPosition3d);
        
        l[5] = new PointLight();
        l[5].setColor(Color.ALICEBLUE);
        l[5].setLayoutX(lightScale3d);
        l[5].setLayoutY(lightScale3d);        
        l[5].setTranslateX(lightPosition3d);
        l[5].setTranslateY(-lightPosition3d);
        l[5].setTranslateZ(-lightPosition3d);
        
        l[6] = new PointLight();
        l[6].setColor(Color.ALICEBLUE);
        l[6].setLayoutX(lightScale3d);
        l[6].setLayoutY(lightScale3d);        
        l[6].setTranslateX(-lightPosition3d);
        l[6].setTranslateY(lightPosition3d);
        l[6].setTranslateZ(-lightPosition3d);
        
        l[7] = new PointLight();
        l[7].setColor(Color.ALICEBLUE);
        l[7].setLayoutX(lightScale3d);
        l[7].setLayoutY(lightScale3d);        
        l[7].setTranslateX(-lightPosition3d);
        l[7].setTranslateY(-lightPosition3d);
        l[7].setTranslateZ(-lightPosition3d);        
        return l;
    }
    
    /**
     * Add ambient light to 3d view
     * 
     * @return 
     */
    public static AmbientLight[] setScene3DAmbientLight(){
        AmbientLight[] l = new AmbientLight[8];
        l[0] = new AmbientLight();
        l[0].setColor(ambientLight);
        l[0].setLayoutX(lightScale3d);
        l[0].setLayoutY(lightScale3d);
        l[0].setTranslateX(lightPosition3d);
        l[0].setTranslateY(lightPosition3d);
        l[0].setTranslateZ(lightPosition3d);        
        
        l[1] = new AmbientLight();
        l[1].setColor(ambientLight);
        l[1].setLayoutX(lightScale3d);
        l[1].setLayoutY(lightScale3d);        
        l[1].setTranslateX(-lightPosition3d);
        l[1].setTranslateY(lightPosition3d);
        l[1].setTranslateZ(lightPosition3d);
        
        l[2] = new AmbientLight();
        l[2].setColor(ambientLight);
        l[2].setLayoutX(lightScale3d);
        l[2].setLayoutY(lightScale3d);        
        l[2].setTranslateX(lightPosition3d);
        l[2].setTranslateY(-lightPosition3d);
        l[2].setTranslateZ(lightPosition3d);
        
        l[3] = new AmbientLight();
        l[3].setColor(ambientLight);
        l[3].setLayoutX(lightScale3d);
        l[3].setLayoutY(lightScale3d);        
        l[3].setTranslateX(lightPosition3d);
        l[3].setTranslateY(lightPosition3d);
        l[3].setTranslateZ(-lightPosition3d);
        
        l[4] = new AmbientLight();
        l[4].setColor(ambientLight);
        l[4].setLayoutX(lightScale3d);
        l[4].setLayoutY(lightScale3d);        
        l[4].setTranslateX(-lightPosition3d);
        l[4].setTranslateY(-lightPosition3d);
        l[4].setTranslateZ(lightPosition3d);
        
        l[5] = new AmbientLight();
        l[5].setColor(ambientLight);
        l[5].setLayoutX(lightScale3d);
        l[5].setLayoutY(lightScale3d);        
        l[5].setTranslateX(lightPosition3d);
        l[5].setTranslateY(-lightPosition3d);
        l[5].setTranslateZ(-lightPosition3d);
        
        l[6] = new AmbientLight();
        l[6].setColor(ambientLight);
        l[6].setLayoutX(lightScale3d);
        l[6].setLayoutY(lightScale3d);        
        l[6].setTranslateX(-lightPosition3d);
        l[6].setTranslateY(lightPosition3d);
        l[6].setTranslateZ(-lightPosition3d);
        
        l[7] = new AmbientLight();
        l[7].setColor(ambientLight);
        l[7].setLayoutX(lightScale3d);
        l[7].setLayoutY(lightScale3d);        
        l[7].setTranslateX(-lightPosition3d);
        l[7].setTranslateY(-lightPosition3d);
        l[7].setTranslateZ(-lightPosition3d);
        return l;
    }
}
