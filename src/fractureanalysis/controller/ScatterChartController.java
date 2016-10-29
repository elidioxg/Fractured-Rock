package fractureanalysis.controller;

import fractureanalysis.Vectors.Vector;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.stages.ScatterChartStage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ScatterChartController implements Initializable {

    @FXML
    protected Button btnCloseLine;

    @FXML
    protected ComboBox cbDatasets, cbX, cbY;
    
    @FXML
    protected TextField tfGraphLabel, tfXLabel, tfYLabel;
    
    @FXML
    protected ScatterChart scatterChart;

    /**
     * Plot a chart serie on Scatter Chart Stage
     * @throws Exception 
     */
    @FXML
    protected void plot() throws Exception {
        int datasetIndex = cbDatasets.getSelectionModel().getSelectedIndex();
        DatasetModel dm = ScatterChartStage.getInstance().getDatasets().get(datasetIndex);
        int indexX = cbX.getSelectionModel().getSelectedIndex();
        int indexY = cbY.getSelectionModel().getSelectedIndex();
        scatterChart.setTitle(tfGraphLabel.getText());
        scatterChart.getXAxis().setLabel(tfXLabel.getText());
        scatterChart.getYAxis().setLabel(tfYLabel.getText());    
        
        Vector x = OpenDataset.openCSVFileToVector(dm.getFileName(), 
                dm.getSeparator().getSep(), indexX, dm.getHeader());
        Vector y = OpenDataset.openCSVFileToVector(dm.getFileName(), 
                dm.getSeparator().getSep(), indexY, dm.getHeader());
        scatterChart.getData().add(PlotSeries.plotLineSeries(x, y));
    }

    /**
     * Clear all series from chart
     */
    @FXML
    protected void clear() {
        scatterChart.getData().clear();
    }

    /**
     * Close the Scatter Chart Stage
     */
    @FXML
    protected void close() {
        Stage stage = (Stage) btnCloseLine.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
