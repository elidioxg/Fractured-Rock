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
package fractureanalysis.stages;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.analysis.ScanLine;
import fractureanalysis.scene.LightProperties;
import fractureanalysis.view3d.Axis;
import fractureanalysis.view3d.DrawPlanes3D;
import fractureanalysis.view3d.FracturePlane3D;
import fractureanalysis.view3d.SceneUtils;
import fractureanalysis.view3d.Well3D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class View3DStage {
    
    private List<FracturePlane3D> list = new ArrayList();
    private ListView lvFrac;

    /**
     * Setup stage for Fractures and Wells 3D view 
     * @throws IOException 
     */
    public void createSetupStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(FractureAnalysis.getInstance().getClass().getResource(
                "views/stage_3dview.fxml"));
        Parent root = (Parent) loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Fractures view on wells");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Create a Stage for 3D fractures representation
     *
     * @throws Exception
     */
    public void scanlineContext(ScanLine scanline) throws Exception {
        final Group root = new Group();
        root.getChildren().addAll(LightProperties.setScene3DLight());
        root.getChildren().addAll(LightProperties.setScene3DAmbientLight());
        root.getChildren().addAll(DrawPlanes3D.drawPlanes(
                scanline));
        root.getChildren().addAll(Axis.addAxis());
        root.getChildren().addAll(Axis.addScanlineAxis(
                FractureAnalysis.getInstance().file.getScanLine().getLenght()));
        System.out.println(FractureAnalysis.getInstance().file.getScanLine().getLenght());

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

    public void fracWellContext(FracturePlane3D[] fracs, Well3D[] wells) {
        final Group root = new Group();
        root.getChildren().addAll(LightProperties.setScene3DLight());
        root.getChildren().addAll(LightProperties.setScene3DAmbientLight());
        root.getChildren().addAll(Axis.addAxis());
        
        root.getChildren().addAll(fracs);
        root.getChildren().addAll(wells);

        Scene scene = new Scene(root, DrawPlanes3D.getViewSize(),
                DrawPlanes3D.getViewSize(), true);
        scene.setFill(Color.WHITE);
        
        SceneUtils utils = new SceneUtils();
        utils.buildCamera(root);
        utils.handleMouse(scene, root);
        utils.handleKeyboard(scene, root);

        Stage stage = new Stage();
        stage.setTitle("3D View of Fracture Planes and Wells");
        stage.setScene(scene);
        stage.show();

    }

}
