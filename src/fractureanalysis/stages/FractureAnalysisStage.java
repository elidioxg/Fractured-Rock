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

import fractureanalysis.model.AnalysisFile;
import fractureanalysis.model.DatasetModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author elidioxg
 */
public class FractureAnalysisStage {

    private static FractureAnalysisStage instance;

    private static List<DatasetModel> datasets;
    private static AnalysisFile analysisFile;

    public FractureAnalysisStage(List<DatasetModel> datasets) throws Exception {
        FractureAnalysisStage.datasets = datasets;
        instance = this;
        createStage();
    }

    public static FractureAnalysisStage getInstance() {
        return instance;
    }

    public static List<DatasetModel> getDatasets() {
        return datasets;
    }

    public static void setAnalysisFile(AnalysisFile af) {
        analysisFile = af;
    }

    public static AnalysisFile getAnalysisFile() {
        return analysisFile;
    }

    private void createStage() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                fractureanalysis.FractureAnalysis.getInstance().getClass()
                        .getResource("views/stage_analysis.fxml")
        );
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        /**
         * Get the headers of dataset
         */
        ArrayList<String> list = getDatasets().get(0).getHeaderArray();

        /**
         * Add the headers of dataset to comboboxes
         */
        ComboBox cbDS = (ComboBox) parent.getScene().getRoot().lookup("#cbDS");       
        
        cbDS.setCellFactory(new Callback<ListView<DatasetModel>, ListCell<DatasetModel>>() {
            @Override
            public ListCell<DatasetModel> call(ListView<DatasetModel> myObjectListView) {
                ListCell<DatasetModel> cell = new ListCell<DatasetModel>() {
                    @Override
                    protected void updateItem(DatasetModel myObject, boolean b) {
                        super.updateItem(myObject, b);
                        if (myObject != null) {
                            setText(myObject.getDatasetName());
                        }
                    }
                };
                return cell;
            }
        });
        cbDS.setItems(FXCollections.observableList(getDatasets()));

        ComboBox cbSpVar = (ComboBox) parent.getScene().getRoot().lookup("#cbSpVar");
        ComboBox cbApVar = (ComboBox) parent.getScene().getRoot().lookup("#cbApVar");
        cbApVar.setItems(FXCollections.observableArrayList(list));
        cbSpVar.setItems(FXCollections.observableArrayList(list));

        Stage stage = new Stage();
        stage.setTitle("Fracture Intensity Analysis");
        stage.setScene(scene);
        stage.show();

    }

}
