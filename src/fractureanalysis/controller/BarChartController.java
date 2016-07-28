package fractureanalysis.controller;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.plot.PlotSeries;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BarChartController implements Initializable{

    @FXML
    protected Button btnCloseBar;
    
    @FXML
    protected ComboBox cbBarDatasets; //not necessary
    
    @FXML
    protected BarChart barChart;
    @FXML
    protected TextField tfLabelChart, tfLabelX, tfLabelY, tfBarSerie;
    @FXML
    protected ComboBox cbDataX, cbDataY;
    @FXML
    protected CheckBox cbSortX, cbSortY;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    protected void plotBar() {
        int indexX = cbDataX.getSelectionModel().getSelectedIndex();
        int indexY = cbDataY.getSelectionModel().getSelectedIndex();
        barChart.setTitle(tfLabelChart.getText());
        barChart.getXAxis().setLabel(tfLabelX.getText());
        barChart.getYAxis().setLabel(tfLabelY.getText());
        PlotSeries plot = new PlotSeries();
        barChart.getData().add(plot.plotBarSeries(
                FractureAnalysis.getInstance().file.getFileName(),
                FractureAnalysis.getInstance().file.getSeparator(),
                tfBarSerie.getText(),
                indexX, indexY));
    }

    @FXML
    protected void closeBarStage() throws IOException {
        Stage stageBar = (Stage) btnCloseBar.getScene().getWindow();
        stageBar.close();
    }
    
}
