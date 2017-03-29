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

import fractureanalysis.stages.View3DStage;
import fractureanalysis.view3d.FracturePlane3D;
import fractureanalysis.view3d.Well3D;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author elidioxg
 */
public class Stage_3dviewController implements Initializable {

    @FXML
    protected TextField tfLen, tfDir, tfDip, tfFracX, tfFracY, tfFracZ,
            tfWellX, tfWellY, tfWellZ, tfDeep, tfDiameter;

    @FXML
    protected Button bWellAdd, bFracAdd;

    @FXML
    protected ListView lvFrac, lvWell;

    /**
     * Add FracturePlane3D Object as item on ListView
     *
     * @throws Exception
     */
    @FXML
    protected void addFracture() throws Exception {
        double len = 0.;
        double dir = 0.;
        double dip = 0.;
        double x = 0.;
        double y = 0.;
        double z = 0.;
        if (!tfLen.getText().isEmpty()) {
            len = Double.valueOf(tfLen.getText());
        }
        if (!tfDir.getText().isEmpty()) {
            dir = Double.valueOf(tfDir.getText());
        }
        if (!tfDip.getText().isEmpty()) {
            dip = Double.valueOf(tfDip.getText());
        }
        if (!tfFracX.getText().isEmpty()) {
            x = Double.valueOf(tfFracX.getText());
        }
        if (!tfFracY.getText().isEmpty()) {
            y = Double.valueOf(tfFracY.getText());
        }
        if (!tfFracZ.getText().isEmpty()) {
            z = Double.valueOf(tfFracZ.getText());
        }
        FracturePlane3D frac = new FracturePlane3D(len, dir, dip, x, y, z);
        ObservableList<FracturePlane3D> ol = lvFrac.getItems();
        ol.add(frac);
        lvFrac.setItems(ol);
        
    }
    
    @FXML
    protected void addPlane(){
        double x = 0.;
        double y = 0.;
        double z = 0.;
        double deep = 100.;
        double diameter = 10.;
        if(!tfWellX.getText().isEmpty()){
            x = Double.valueOf(tfWellX.getText());
        }
        if(!tfWellY.getText().isEmpty()){
            y = Double.valueOf(tfWellY.getText());
        }
        if(!tfWellZ.getText().isEmpty()){
            z = Double.valueOf(tfWellZ.getText());
        }
        if(!tfDeep.getText().isEmpty()){
            deep = Double.valueOf(tfDeep.getText());
        }
        if(!tfDiameter.getText().isEmpty()){
            diameter = Double.valueOf(tfDiameter.getText());
        }
        Well3D well = new Well3D(diameter, deep, x, y, z);
        ObservableList<Well3D> ol = FXCollections.observableList(lvWell.getItems());
        ol.add(well);
        lvWell.setItems(ol);
    }

    @FXML
    protected void generateContext() {
        int size = lvFrac.getItems().size();
        View3DStage s = new View3DStage();
        FracturePlane3D[] fracs = new FracturePlane3D[size];
        for (int i = 0; i < size; i++) {
            fracs[i] = (FracturePlane3D) lvFrac.getItems().get(i);
        }
        ObservableList wellList = lvWell.getItems();
        Well3D[] wells = new Well3D[wellList.size()];
        for(int i = 0; i < wellList.size(); i++){
            wells[i] = (Well3D) lvWell.getItems().get(i);
        }
        s.fracWellContext(fracs, wells);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvFrac.setCellFactory(lv -> new ListCell<FracturePlane3D>(){
            @Override
            public void updateItem(FracturePlane3D item, boolean empty){
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                } else {
                    setText("Teste");
                }
            }
        });
        lvWell.setCellFactory(lv -> new ListCell<Well3D>(){
            @Override
            public void updateItem(Well3D well, boolean empty){
                super.updateItem(well, empty);
                if(empty){
                    setText(null);
                } else {
                    setText("Teste");
                }
            }
        });
    }

}
