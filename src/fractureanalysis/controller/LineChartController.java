package fractureanalysis.controller;

import fractureanalysis.model.DatasetModel;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.stages.LineChartStage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LineChartController implements Initializable{

    @FXML
    protected TextField tfGraphLabel, tfXLabel, tfYLabel, tfSerieLabel;

    @FXML
    private ComboBox cbDatasets, comboBoxX, comboBoxY;

    @FXML
    protected LineChart lineChart;
    
    @FXML
    protected Button btnCloseLine;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
    /**
     * Plot in Line Chart using the parameter defined by the user on
     * Line Chart Stage.
     */
    @FXML
    protected void plotLine() {
        int datasetIndex = cbDatasets.getSelectionModel().getSelectedIndex();
        DatasetModel dm = LineChartStage.getInstance().getDatasets().get(datasetIndex);
        int indexX = comboBoxX.getSelectionModel().getSelectedIndex();
        int indexY = comboBoxY.getSelectionModel().getSelectedIndex();
        lineChart.setTitle(tfGraphLabel.getText());
        lineChart.getXAxis().setLabel(tfXLabel.getText());
        lineChart.getYAxis().setLabel(tfYLabel.getText());
        PlotSeries plot = new PlotSeries();
        lineChart.getData().add(plot.plotLineSeries(dm.getFileName(),
                dm.getSeparator().getChar(), tfSerieLabel.getText(), indexX, indexY));
    }
    
    /**
     * Clear the Line Chart. This procedure is used for the button 'clear'.
     */
    @FXML
    protected void clearLineChart() {
        lineChart.getData().clear();
    }
    
    /**
     * Close the Line Chart Stage.
     * @throws IOException 
     */
    @FXML
    protected void closeLineStage() throws IOException {
        Stage stageLine = (Stage) btnCloseLine.getScene().getWindow();
        stageLine.close();
    }

}
