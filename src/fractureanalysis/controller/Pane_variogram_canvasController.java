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
package fractureanalysis.controller;

import fractureanalysis.view2d.CanvasSearchWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeType;

/**
 * FXML Controller class
 *
 * @author elidioxg
 */
public class Pane_variogram_canvasController implements Initializable {

   

    @FXML
    protected Canvas canvas;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        double radiusX = canvas.getWidth() / 1.5;
        double radiusY = canvas.getHeight() / 1.5;
        double centerX = (canvas.getWidth() / 2) - (radiusX / 2);
        double centerY = (canvas.getHeight() / 2) - (radiusY / 2);
        CanvasSearchWindow csw = new CanvasSearchWindow(0, 30);
        double startAngle = csw.getInitAngle();
        double finalAngle = csw.getFinalAngle();
        canvas.getGraphicsContext2D().setStroke(Paint.valueOf("BLACK"));
        canvas.getGraphicsContext2D().setFill(Paint.valueOf("WHITE"));
        canvas.getGraphicsContext2D().strokeArc(
                centerX, centerY,
                radiusX, radiusY,
                startAngle, finalAngle,
                ArcType.ROUND);
    }

}
