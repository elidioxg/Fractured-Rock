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

public class LineChartStage {

    private static LineChartStage instance;

    private static List<DatasetModel> datasets;

    /**
     * Constructor Create a Stage with defined list of datasets
     *
     * @param datasets
     */
    public LineChartStage(List<DatasetModel> datasets) throws IOException {
        this.datasets = datasets;
        instance = this;
        createStage();
    }

    public static LineChartStage getInstance() {
        return instance;
    }

    /**
     * Get datasets created with stage
     *
     * @return
     */
    public static List<DatasetModel> getDatasets() {
        return datasets;
    }

    /**
     * Create Stage for Line Chart Plot
     *
     * @throws IOException
     */
    private void createStage() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                FractureAnalysis.getInstance().getClass().getResource(
                        "views/stage_line_chart.fxml"));
        Parent parent = (Parent) loader.load();

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

        ObservableList ol = FXCollections.observableArrayList(datasets.get(0).getHeaderArray());

        ComboBox cbDatasets = (ComboBox) parent.lookup("#cbDatasets");

        cbDatasets.setButtonCell((ListCell) cellFactory.call(null));
        cbDatasets.setCellFactory(cellFactory);
        if (!getDatasets().isEmpty()) {
            cbDatasets.setItems(FXCollections.observableArrayList(
                    datasets));
            cbDatasets.getSelectionModel().selectFirst();
        }
        ComboBox comboBoxX = (ComboBox) parent.lookup("#comboBoxX");
        ComboBox comboBoxY = (ComboBox) parent.lookup("#comboBoxY");
        comboBoxX.setItems(ol);
        comboBoxY.setItems(ol);

        Stage stageLine = new Stage();
        Scene scene = new Scene(parent);
        stageLine.setTitle("Line Chart");
        stageLine.setScene(scene);
        stageLine.show();
    }

}
