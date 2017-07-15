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
import fractureanalysis.view2d.CanvasSearchWindow;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class SearchWindowStage {
    public void createStage(double initAngle, double finalAngle, boolean reflect) throws IOException{
        FXMLLoader loader = new FXMLLoader(FractureAnalysis.getInstance().getClass().getResource(
                            "views/pane_variogram_canvas.fxml"));
        GridPane parent = (GridPane)loader.load();
        CanvasSearchWindow csw = new CanvasSearchWindow(initAngle, finalAngle, reflect);
        Canvas canvas = csw.getCanvas();
               
        parent.add(canvas, 2, 1, 4, 4);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);        
        stage.setTitle("Variogram Search Window");
        stage.setScene(scene);
        stage.show();
    }
    
}
