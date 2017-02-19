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
public class HistogramStage {
    /**
     * Custom Histogram Stage
     * 
     * This stage is used for plotting a histogram with many properties
     * defined by the user(cutoff values, number of classes, etc);
     * 
     * View: views/stage_histogram.fxml
     * Controller: controller/Stage_histogramController.java
     */

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

    /**
     * Create a stage for plotting custom histogram.
     * 
     * @throws IOException 
     */
    public void createStage() throws IOException {
        if (FractureAnalysis.getInstance().getDatasetList().size()>0) {
            FXMLLoader loader = new FXMLLoader(FractureAnalysis.getInstance().
                    getClass().getResource(
                            "views/stage_histogram.fxml"));
            Parent parent = (Parent) loader.load();
            ComboBox cbDatasets = (ComboBox) parent.lookup("#cbDatasets");           
            
            Callback<ListView<DatasetModel>, ListCell<DatasetModel>> cellFactory
                    = new Callback<ListView<DatasetModel>, ListCell<DatasetModel>>() {
                @Override
                public ListCell<DatasetModel> call(ListView<DatasetModel> param) {
                    return new ListCell<DatasetModel>() {
                        @Override
                        protected void updateItem(DatasetModel item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getDatasetName());
                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                }

            };
            cbDatasets.setButtonCell((ListCell) cellFactory.call(null));
            cbDatasets.setCellFactory(cellFactory);
            cbDatasets.setItems(FXCollections.observableArrayList(datasets));
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
