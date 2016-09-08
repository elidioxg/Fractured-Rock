package fractureanalysis.stages;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.model.DatasetModel;
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
    
    private static LineChartStage instance;
    
    private static List<DatasetModel> datasets;
    
    /**
     * 
     * @param datasets 
     */
    public LineChartStage(List<DatasetModel> datasets){
        this.datasets = datasets;
        instance = this;
    }
    
    /**
     * 
     * @return 
     */
    public static LineChartStage getInstance(){
        return instance;
    }
    
    /**
     * 
     * @return 
     */
    public static List<DatasetModel> getDatasets(){
        return datasets;
    }

    /**
     * 
     * @throws IOException 
     */
    public void createStage() throws IOException {
        if (!getDatasets().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(
                    FractureAnalysis.getInstance().getClass().getResource(
                            "views/stage_line_chart.fxml"));
            Parent parent = (Parent) loader.load();
            
            //get the dataset list to put on combobox
            List list = new ArrayList();
            for (int i = 0; i < getDatasets().get(0).getHeaderArray().size(); i++) {
                list.add(getDatasets().get(0).getHeaderArray(i));                
            }
            List datasetList = new ArrayList();
            for (int i = 0; i < getDatasets().size(); i++) {                
                datasetList.add(getDatasets().get(i).getDatasetName());
            }
            ObservableList ol = FXCollections.observableArrayList(list);
            ObservableList olDatasets = FXCollections.observableArrayList(
                    datasetList);

            ComboBox cbDatasets = (ComboBox) parent.lookup("#cbDatasets");
            cbDatasets.setItems(olDatasets);
            cbDatasets.getSelectionModel().selectFirst();
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
