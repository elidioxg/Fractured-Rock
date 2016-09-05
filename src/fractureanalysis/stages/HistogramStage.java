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
import fractureanalysis.model.DatasetModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class HistogramStage {

    private static HistogramStage instance;

    private List<DatasetModel> datasets;

    public HistogramStage(List<DatasetModel> datasets) {
        instance = this;
        this.datasets = datasets;
    }

    public static HistogramStage getInstance() {
        return instance;
    }

    public List<DatasetModel> getDatasets() {
        return this.datasets;
    }

    public void createStage() throws IOException {
        if (!FractureAnalysis.getInstance().file.getFileName().trim().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(
                    FractureAnalysis.getInstance().getClass().getResource(
                            "views/stage_histogram.fxml"));
            Parent parent = (Parent) loader.load();
            ComboBox cbDatasets = (ComboBox) parent.lookup("#cbDatasets");
            ArrayList<String> datasetNames = new ArrayList();
            for (int i = 0; i < datasets.size(); i++) {
                datasetNames.add(datasets.get(i).getDatasetName());
            }
            cbDatasets.setItems(FXCollections.observableArrayList(datasetNames));
            cbDatasets.getSelectionModel().selectFirst();
            ComboBox cbColumns = (ComboBox) parent.lookup("#cbColumnIndex");
            cbColumns.setItems(FXCollections.observableArrayList(
                    datasets.get(0).getHeaderArray()));
            Stage stageLine = new Stage();
            Scene scene = new Scene(parent);
            stageLine.setTitle("Custom Histogram");
            stageLine.setScene(scene);
            stageLine.show();
        }
    }

}
