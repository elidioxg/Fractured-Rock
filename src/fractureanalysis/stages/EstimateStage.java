package fractureanalysis.stages;

import fractureanalysis.FractureAnalysis;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class EstimateStage {

    public void createStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                FractureAnalysis.getInstance().getClass().getResource(
                        "views/fracture_analysis.fxml"));
        Parent parent = (Parent) loader.load();

        List list = new ArrayList();
        if (FractureAnalysis.getInstance().file.getHeaderArray().size() > 0) {
            for (int i = 0; i < FractureAnalysis.getInstance().file.getHeaderArray().size(); i++) {
                list.add(FractureAnalysis.getInstance().file.getHeaderStrings(i));
            }
        }
        ObservableList ol = FXCollections.observableArrayList(list);
        ObservableList olDatasets = FXCollections.observableArrayList(
                FractureAnalysis.getInstance().datasets);

        ComboBox cbDatasets = (ComboBox) parent.lookup("#cbDataset");
        ComboBox cbDataX = (ComboBox) parent.lookup("#cbApValues");
        ComboBox cbDataY = (ComboBox) parent.lookup("#cbSpValues");
        cbDatasets.setItems(olDatasets);
        cbDatasets.getSelectionModel().select(
                FractureAnalysis.getInstance().file.getDatasetName());
        cbDataX.setItems(ol);
        cbDataY.setItems(ol);

        Stage stageBar = new Stage();
        Scene scene = new Scene(parent);
        stageBar.setTitle("Estimate the cumulative-frequency of fracture-size distribution");
        stageBar.setScene(scene);
        stageBar.show();
    }

}
