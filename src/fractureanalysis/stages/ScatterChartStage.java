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

public class ScatterChartStage {
    
    public void createStage() throws IOException {
        if(!FractureAnalysis.getInstance().file.getFileName().trim().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(
                    FractureAnalysis.getInstance().getClass().getResource(
                            "views/scatter_chart_stage.fxml"));
            Parent parent = (Parent) loader.load();
            
            //get the dataset list to put on combobox
            List list = new ArrayList();
            for (int i = 0; i < FractureAnalysis.getInstance().file.getHeaderStrings().length; i++) {
                list.add(FractureAnalysis.getInstance().file.getHeaderStrings(i));
            }
            ObservableList ol = FXCollections.observableArrayList(list);
            ObservableList olDatasets = FXCollections.observableArrayList(
                    FractureAnalysis.getInstance().datasets);            
            ComboBox cbDatasets =  (ComboBox) parent.lookup("#cbDatasets");
            cbDatasets.setItems(olDatasets);
            cbDatasets.getSelectionModel().select(
                    FractureAnalysis.getInstance().file.getDatasetName());
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
    
}
