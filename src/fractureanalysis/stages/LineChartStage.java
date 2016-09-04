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

public class LineChartStage {

    public void createStage() throws IOException {
        if (!FractureAnalysis.getInstance().file.getFileName().trim().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(
                    FractureAnalysis.getInstance().getClass().getResource(
                            "views/line_chart_stage.fxml"));
            Parent parent = (Parent) loader.load();
            
            //get the dataset list to put on combobox
            List list = new ArrayList();
            for (int i = 0; i < FractureAnalysis.getInstance().file.getHeaderArray().size(); i++) {
                list.add(FractureAnalysis.getInstance().file.getHeaderArray(i));
            }
            ObservableList ol = FXCollections.observableArrayList(list);
            ObservableList olDatasets = FXCollections.observableArrayList(
                    FractureAnalysis.getInstance().datasets);

            ComboBox cbDatasets = (ComboBox) parent.lookup("#cbDataset");
            cbDatasets.setItems(olDatasets);
            cbDatasets.getSelectionModel().select(
                    FractureAnalysis.getInstance().file.getDatasetName());
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

}
