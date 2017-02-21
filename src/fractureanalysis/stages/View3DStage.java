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
package fractureanalysis.stages;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.analysis.ScanLine;
import fractureanalysis.scene.LightProperties;
import fractureanalysis.view3d.Axis;
import fractureanalysis.view3d.DrawPlanes3D;
import fractureanalysis.view3d.SceneUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class View3DStage {

    /**
     * Create a Stage for 3D fractures representation
     *
     * @throws Exception
     */
    public void createStage() throws Exception {
        final Group root = new Group();
        root.getChildren().addAll(LightProperties.setScene3DLight());
        root.getChildren().addAll(LightProperties.setScene3DAmbientLight());
        root.getChildren().addAll(DrawPlanes3D.drawPlanes(
                FractureAnalysis.getInstance().file.getScanLine()));
        root.getChildren().addAll(Axis.addAxis());

        Scene scene = new Scene(root, DrawPlanes3D.getViewSize(),
                DrawPlanes3D.getViewSize(), true);
        scene.setFill(Color.WHITE);

        SceneUtils utils = new SceneUtils();
        utils.buildCamera(root);
        utils.handleMouse(scene, root);
        utils.handleKeyboard(scene, root);

        Stage stage = new Stage();
        stage.setTitle("3D View of Fracture Planes");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Create a Stage for 3D fractures representation
     *
     * @throws Exception
     */
    public void createStage(ScanLine scanline) throws Exception {
        final Group root = new Group();
        root.getChildren().addAll(LightProperties.setScene3DLight());
        root.getChildren().addAll(LightProperties.setScene3DAmbientLight());
        root.getChildren().addAll(DrawPlanes3D.drawPlanes(
                scanline));
        root.getChildren().addAll(Axis.addAxis());

        Scene scene = new Scene(root, DrawPlanes3D.getViewSize(),
                DrawPlanes3D.getViewSize(), true);
        scene.setFill(Color.WHITE);

        SceneUtils utils = new SceneUtils();
        utils.buildCamera(root);
        utils.handleMouse(scene, root);
        utils.handleKeyboard(scene, root);

        Stage stage = new Stage();
        stage.setTitle("3D View of Fracture Planes");
        stage.setScene(scene);
        stage.show();
    }

}
