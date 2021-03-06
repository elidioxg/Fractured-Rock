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
public class MatrixViewStage {

    private List<DatasetModel> datasets;

    public MatrixViewStage(List<DatasetModel> datasets) throws IOException {
        this.datasets = datasets;
        createStage();
    }

    public List<DatasetModel> getDatasets() {
        return this.datasets;
    }

    private void createStage() throws IOException {
        if (!getDatasets().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(
                    FractureAnalysis.getInstance().getClass().getResource(
                            "views/stage_matrix_view.fxml"));
            Parent root = loader.load();

            ComboBox cbDatasets = (ComboBox) root.lookup("#cbDatasets");

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

            Stage stage = new Stage();
            stage.setTitle("Matrix Content View");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
