package fractureanalysis.controller;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.plot.PlotSeries;
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
    private ComboBox comboBoxX;

    @FXML
    private ComboBox comboBoxY;
    
    @FXML
    protected LineChart lineChart;
    
    @FXML
    protected Button btnCloseLine;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
    @FXML
    protected void plotLine() {
        int indexX = comboBoxX.getSelectionModel().getSelectedIndex();
        int indexY = comboBoxY.getSelectionModel().getSelectedIndex();
        lineChart.setTitle(tfGraphLabel.getText().toString());
        lineChart.getXAxis().setLabel(tfXLabel.getText());
        lineChart.getYAxis().setLabel(tfYLabel.getText());
        PlotSeries plot = new PlotSeries();
        lineChart.getData().add(plot.plotLineSeries(
                FractureAnalysis.getInstance().file.getFileName(),
                FractureAnalysis.getInstance().file.getSeparator(),
                tfSerieLabel.getText(),
                indexX, indexY));
    }
    
    @FXML
    protected void clearLineChart() {
        lineChart.getData().clear();
    }
    @FXML
    protected void closeLineStage() throws IOException {
        Stage stageLine = (Stage) btnCloseLine.getScene().getWindow();
        stageLine.close();
    }

}
