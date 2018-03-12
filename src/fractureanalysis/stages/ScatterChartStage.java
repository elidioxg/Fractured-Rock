package fractureanalysis.stages;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.model.DatasetModel;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ScatterChartStage {

    private static ScatterChartStage instance;

    private final List<DatasetModel> datasets;

    /**
     *
     * @param datasets
     * @throws java.io.IOException
     */
    public ScatterChartStage(List<DatasetModel> datasets) throws IOException {
        this.datasets = datasets;
        instance = this;
        createStage();
    }

    /**
     *
     * @return
     */
    public static ScatterChartStage getInstance() {
        return instance;
    }

    /**
     *
     * @return
     */
    public List<DatasetModel> getDatasets() {
        return this.datasets;
    }

    /**
     * Create the Stage
     * @throws IOException
     */
    private void createStage() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                FractureAnalysis.getInstance().getClass().getResource(
                        "views/stage_scatter_chart.fxml"));
        Parent parent = (Parent) loader.load();

        ObservableList ol = FXCollections.observableArrayList(datasets.get(0).getHeaderArray());
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

        ComboBox cbDatasets = (ComboBox) parent.lookup("#cbDatasets");
        cbDatasets.setButtonCell((ListCell) cellFactory.call(null));
        cbDatasets.setCellFactory(cellFactory);
        if (!getDatasets().isEmpty()) {
            cbDatasets.setItems(FXCollections.observableArrayList(datasets));
            cbDatasets.getSelectionModel().selectFirst();
        }

        cbDatasets.getSelectionModel().selectFirst();
        ComboBox comboBoxX = (ComboBox) parent.lookup("#cbX");
        ComboBox comboBoxY = (ComboBox) parent.lookup("#cbY");
        comboBoxX.setItems(ol);
        comboBoxY.setItems(ol);

        Stage stageLine = new Stage();
        Scene scene = new Scene(parent);
        stageLine.setTitle("Scatter Chart");
        stageLine.setScene(scene);
        stageLine.show();
    }

}
