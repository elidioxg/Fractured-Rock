/*
 * Copyright (C) 2017 fedora
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
import fractureanalysis.data.DatasetProperties;
import fractureanalysis.model.GeoeasFormat;
import fractureanalysis.model.Separator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fedora
 */
public class Stage_open_geoeasController implements Initializable {

    @FXML
    protected TextField tfFilename;
    @FXML
    protected RadioButton rbComma, rbSpaces;

    @FXML
    protected void dialogOpen() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(
                FractureAnalysis.getInstance().stage);
        if (file != null) {
            if (file.exists()) {
                if (file.getAbsolutePath() != null) {
                    tfFilename.setText(file.getAbsolutePath());
                }
            }
        }
    }

    @FXML
    protected void addToList() throws IOException {
        Separator sep;
        if (rbComma.isSelected()) {
            sep = new Separator(2);
        } else {
            sep = new Separator(" ");
        }
        if (!tfFilename.getText().trim().isEmpty()) {
            File file = new File(tfFilename.getText());
            GeoeasFormat gf = DatasetProperties.getGoeasProperties(file.getAbsolutePath());
            FractureAnalysis.getInstance().file.setFilename(file.getAbsolutePath());
            FractureAnalysis.getInstance().file.setDatasetName(gf.getTitle());            
            FractureAnalysis.getInstance().file.setHeaderStrings(
                    gf.getHeaders());
            FractureAnalysis.getInstance().file.setSeparator(sep);
            FractureAnalysis.getInstance().file.setColumnsCount(
                    gf.getColumnsCount());            
            FractureAnalysis.getInstance().file.setRowsCount(gf.getRowsCount());
            //FractureAnalysis.getInstance().file.setHeader(true);
            FractureAnalysis.getInstance().file.setGeoeasFormat(true);
            FractureAnalysis.getInstance().updateListView();
        } else {
            //TODO: alert message
        }
    }

    @FXML
    protected void closeStage() {
        Stage stage = (Stage) tfFilename.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void rbSpaces() {

    }

    @FXML
    protected void rbComma() {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
