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

public class BarChartStage {

    public void createStage() throws IOException {
        if(!FractureAnalysis.getInstance().file.getFileName().trim().isEmpty()){
        FXMLLoader loader = new FXMLLoader(FractureAnalysis.getInstance().getClass().getResource(
                "views/BarChartStage.fxml"));
        Parent parent = (Parent) loader.load();
        List list = new ArrayList();
        //to do:  platform.runlater {
        for (int i = 0; i < FractureAnalysis.getInstance().
                file.getHeaderStrings().length; i++) {
            list.add(FractureAnalysis.getInstance().file.getHeaderStrings(i));
        }
        ObservableList ol = FXCollections.observableArrayList(list);
        ObservableList olDatasets = FXCollections.observableArrayList(
                FractureAnalysis.getInstance().datasets);

        ComboBox cbDatasets = (ComboBox) parent.lookup("#cbBarDataset");
        cbDatasets.setItems(olDatasets);
        cbDatasets.getSelectionModel().select(
                FractureAnalysis.getInstance().file.getDatasetName());
        ComboBox cbDataX = (ComboBox) parent.lookup("#cbDataX");
        ComboBox cbDataY = (ComboBox) parent.lookup("#cbDataY");
        cbDataX.setItems(ol);
        cbDataY.setItems(ol);

        Stage stageBar = new Stage();
        Scene scene = new Scene(parent);
        stageBar.setTitle("Bar Chart");
        stageBar.setScene(scene);
        stageBar.show();
        }
    }

}
