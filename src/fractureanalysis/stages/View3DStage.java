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
import fractureanalysis.scene.LightProperties;
import fractureanalysis.view3d.Axis;
import fractureanalysis.view3d.DrawPlanes3D;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class View3DStage {

    /**
     * Default Camera Parameters
     */
    private final int defaultFarClip = 10000;
    private final double defaultNearClip = 0.01;
    private final double defaultFieldOfView = 25.;
    private final double defaultXRotate = 180.;
    private final double defaultYRotate = 0.;
    private final double defaultZRotate = 0.;
    private final double defaultXTranslate = 500;
    private final double defaultYTranslate = -350.;
    private final double defaultZTranslate = -3000.;
    

    public void createStage() throws Exception {        
        final Group root = new Group();
        root.getChildren().addAll(LightProperties.setScene3DLight());
        root.getChildren().addAll(LightProperties.setScene3DAmbientLight());
        root.getChildren().addAll(DrawPlanes3D.drawPlanes(
                FractureAnalysis.getInstance().file.getScanLine()));
        root.getChildren().addAll(Axis.addAxis());        
        
        System.out.println("Fractures Count: "+
                FractureAnalysis.getInstance().file.getScanLine().fracturesCount());
        
        Scene scene = new Scene(root, DrawPlanes3D.getViewSize(),
                DrawPlanes3D.getViewSize(), true);
        scene.setFill(Color.WHITE);
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setFieldOfView(defaultFieldOfView);
        camera.setFarClip(defaultFarClip);
        camera.setNearClip(defaultNearClip);
        camera.setDepthTest(DepthTest.ENABLE);
        camera.getTransforms().addAll(
                new Rotate(defaultXRotate, Rotate.X_AXIS),
                new Rotate(defaultYRotate, Rotate.Y_AXIS),
                new Rotate(defaultZRotate, Rotate.Z_AXIS),
                new Translate(defaultXTranslate, defaultYTranslate,
                        defaultZTranslate));
        root.getChildren().add(camera);        
        scene.setCamera(camera);
        
        Stage stage = new Stage();
        stage.setTitle("3D View of Fracture Planes");
        stage.setScene(scene);
        stage.show();
    }

}
