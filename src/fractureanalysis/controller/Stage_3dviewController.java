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

import fractureanalysis.scene.MaterialProperties;
import fractureanalysis.stages.View3DStage;
import fractureanalysis.view3d.DrillingHole;
import fractureanalysis.view3d.FracturePlane;
import fractureanalysis.view3d.Lithology;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Material;

/**
 * FXML Controller class
 *
 * @author elidioxg
 */
public class Stage_3dviewController implements Initializable {

    @FXML
    protected TextField tfDir, tfDip, tfFracDeep,
            tfWellX, tfWellY, tfWellZ, tfDeep, tfDiameter, tfInit, tfEnd,
            tfLitName;

    @FXML
    protected ComboBox cbFracWell, cbLitWell, cbMaterial;

    @FXML
    protected Button bWellAdd, bFracAdd;

    @FXML
    protected ListView lvFrac, lvWell, lvLithology;

    @FXML
    protected void addLit() {
        int i = cbLitWell.getSelectionModel().getSelectedIndex();
        int z = cbMaterial.getSelectionModel().getSelectedIndex();
        if (i >= 0 && z >= 0) {
            Lithology lit = new Lithology(tfLitName.getText(), (DrillingHole) cbLitWell.getItems().get(i),
                    Double.valueOf(tfInit.getText()), Double.valueOf(tfEnd.getText()),
                    (Material) cbMaterial.getItems().get(z));
            lvLithology.getItems().add(lit);
        }
    }

    /**
     * Add FracturePlane3D Object as item on ListView
     *
     * @throws Exception
     */
    @FXML
    protected void addFracture() throws Exception {
        double dir = 0.;
        double dip = 0.;
        if (!tfDir.getText().isEmpty()) {
            dir = Double.valueOf(tfDir.getText());
        }
        if (!tfDip.getText().isEmpty()) {
            dip = Double.valueOf(tfDip.getText());
        }
        
        DrillingHole dh = (DrillingHole) cbFracWell.getSelectionModel().getSelectedItem();        
        double y = dh.getY() + Double.valueOf(tfFracDeep.getText());
                
        FracturePlane frac
                = new FracturePlane(dh, dir, dip, y);
        ObservableList<FracturePlane> ol = lvFrac.getItems();
        ol.add(frac);
        dh.getFractures().add(frac);
        lvFrac.setItems(ol);
    }

    @FXML
    protected void addWell() {
        double x = 0.;
        double y = 0.;
        double z = 0.;
        double deep = 100.;
        double diameter = 10.;
        if (!tfWellX.getText().isEmpty()) {
            x = Double.valueOf(tfWellX.getText());
        }
        if (!tfWellY.getText().isEmpty()) {
            y = Double.valueOf(tfWellY.getText());
        }
        if (!tfWellZ.getText().isEmpty()) {
            z = Double.valueOf(tfWellZ.getText());
        }
        if (!tfDeep.getText().isEmpty()) {
            deep = Double.valueOf(tfDeep.getText());
        }
        if (!tfDiameter.getText().isEmpty()) {
            diameter = Double.valueOf(tfDiameter.getText());
        }
        DrillingHole well = new DrillingHole(diameter, deep, x, y, z);
        ObservableList<DrillingHole> ol = FXCollections.observableList(lvWell.getItems());
        ol.add(well);
        lvWell.setItems(ol);
        cbFracWell.setItems(ol);
        cbLitWell.setItems(ol);
    }

    @FXML
    protected void generateContext() throws Exception {
        View3DStage s = new View3DStage();
        s.fracWellContext(lvWell.getItems());
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvFrac.setCellFactory(lv -> new ListCell<FracturePlane>() {
            @Override
            public void updateItem(FracturePlane item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText("Teste");
                }
            }
        });
        lvWell.setCellFactory(lv -> new ListCell<DrillingHole>() {
            @Override
            public void updateItem(DrillingHole well, boolean empty) {
                super.updateItem(well, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText("Teste");
                }
            }
        });
        cbMaterial.setCellFactory(lv -> new ListCell<Material>() {
            @Override
            public void updateItem(Material material, boolean empty) {
                super.updateItem(material, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText("Classe a ser criada");
                }
            }            
        });
        cbMaterial.setItems(MaterialProperties.getLithologiesMaterial());
        lvLithology.setCellFactory(lv -> new ListCell<Lithology>() {
            @Override
            public void updateItem(Lithology lit, boolean empty){
                super.updateItem(lit, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(lit.getName());
                }
            }
        });

    }

}
