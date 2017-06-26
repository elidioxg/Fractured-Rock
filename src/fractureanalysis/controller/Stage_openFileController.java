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

import fractureanalysis.FractureAnalysis;
import fractureanalysis.data.DatasetProperties;
import fractureanalysis.model.AnalysisFile;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.model.Separator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class Stage_openFileController implements Initializable{

    @FXML
    private TextField tfFilename;
    
    @FXML
    private CheckBox cbHeader;
    
    @FXML
    protected TextField tfSeparator;

    @FXML
    protected RadioButton rbTab, rbComma, rbSemicolon, rbOther;
    
    @FXML
    Button btnClose;

    @FXML
    protected void rbTabAction() {
        rbTab.setSelected(true);
        rbComma.setSelected(false);
        rbSemicolon.setSelected(false);
        rbOther.setSelected(false);
    }

    @FXML
    protected void rbCommaAction() {
        rbTab.setSelected(false);
        rbComma.setSelected(true);
        rbSemicolon.setSelected(false);
        rbOther.setSelected(false);
    }

    @FXML
    protected void rbSemicolonAction() {
        rbTab.setSelected(false);
        rbComma.setSelected(false);
        rbSemicolon.setSelected(true);
        rbOther.setSelected(false);
    }

    @FXML
    protected void rbOtherAction() {
        rbTab.setSelected(false);
        rbComma.setSelected(false);
        rbSemicolon.setSelected(false);
        rbOther.setSelected(true);
    }         

    @FXML
    public void closeOpenFileStage() throws IOException {
        Stage stageOpenWindow = (Stage) btnClose.getScene().getWindow();
        stageOpenWindow.close();
    }
    
    
        /**
     * Add a dataset to the list of datasets on main form and update the
     * datasets listview.
     *
     * @throws IOException
     */
    @FXML
    protected void addToList() throws IOException, Exception {
        Separator sep;
        if (rbTab.isSelected()) {
            sep = new Separator(0);
        } else if (rbSemicolon.isSelected()) {
            sep = new Separator(1);
        } else if (rbComma.isSelected()) {
            sep = new Separator(2);
        } else {
            String aux = tfSeparator.getCharacters().toString();
            if (aux.length() != 0) {
                sep = new Separator(aux);
            } else {
                sep = new Separator();
                throw new Exception("Invalid Separator");
            }
        }
        if (!tfFilename.getText().trim().isEmpty()) {
            File file = new File(tfFilename.getText());
            DatasetModel dataset = new AnalysisFile();
            dataset.setFilename(file.getAbsolutePath());
            dataset.setDatasetName(file.getName());
            int columnCount = DatasetProperties.getColumnsCount(file.getAbsolutePath(), sep);
            dataset.setHeaderStrings(
                    DatasetProperties.getHeaders(file.getAbsolutePath(), sep));
            dataset.setSeparator(sep);
            dataset.setColumnsCount(columnCount);
            int rowCount = DatasetProperties.getRowCount(file.getAbsolutePath());
            dataset.setRowsCount(rowCount);
            if (cbHeader.isSelected()) {
                dataset.setHeader(true);
            } else {
                dataset.setHeader(false);
            }
            FractureAnalysis.getInstance().addToListView(dataset);
        } else {
            //TODO: alert message
        }
    }

    /**
     * Open a dialog for choose the file to be opened as dataset.
     *
     * @throws IOException
     */
    @FXML
    protected void dialogOpen() throws IOException {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
