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
package fractureanalysis.controller;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.view2d.DrawScanline;
import fractureanalysis.scene.LightProperties;
import fractureanalysis.stages.View3DStage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 *
 * @author elidioxg
 */
public class ViewController implements Initializable{   
    
    @FXML 
    protected Button button;    
    @FXML 
    protected Group group;
    
    @FXML
    protected void view(){
        group.getChildren().addAll(LightProperties.setAmbientLight(Color.WHITE));
        group.getChildren().addAll(LightProperties.setPointLight(Color.WHITE, 50, 50));
        group.getScene().setFill(Color.WHITE);        
        //group.getChildren().addAll(DrawScanline.drawPlane());
        group.getChildren().addAll(DrawScanline.drawFractures(
        FractureAnalysis.getInstance().file.getScanLine()));                
    }
    
    @FXML
    protected void view3d(){
        View3DStage stage = new View3DStage();
        stage.createStage();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
