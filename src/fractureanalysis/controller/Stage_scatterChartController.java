package fractureanalysis.controller;

import contrib.LogarithmicAxis;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.stages.ScatterChartStage;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Stage_scatterChartController implements Initializable {

    @FXML
    protected GridPane gpStage;

    @FXML
    protected Button btnCloseLine;

    @FXML
    protected ComboBox cbDatasets, cbX, cbY;

    @FXML
    protected TextField tfGraphLabel, tfXLabel, tfYLabel,
            tfMinX, tfMaxX, tfMinY, tfMaxY, tfSerieLabel;

    @FXML
    protected CheckBox cbAutoAxis, cbXSorted, cbYSorted, cbLogX, cbLogY;

    @FXML
    protected ScatterChart scatterChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Plot a chart serie on Scatter Chart Stage
     *
     * @throws Exception
     */
    @FXML
    protected void plot() throws Exception {
        int datasetIndex = cbDatasets.getSelectionModel().getSelectedIndex();
        DatasetModel dm = ScatterChartStage.getInstance().getDatasets().get(datasetIndex);
        int indexX = cbX.getSelectionModel().getSelectedIndex();
        int indexY = cbY.getSelectionModel().getSelectedIndex();
        Vector x;
        Vector y;
        if (dm.isGeoeas()) {
            x = OpenDataset.openGeoeasToVector(dm.getFileName(),
                    dm.getSeparator().getChar(), indexX);
            y = OpenDataset.openGeoeasToVector(dm.getFileName(),
                    dm.getSeparator().getChar(), indexY);

        } else {
            x = OpenDataset.openCSVFileToVector(dm.getFileName(),
                    dm.getSeparator().getChar(), indexX, dm.getHeader());
            y = OpenDataset.openCSVFileToVector(dm.getFileName(),
                    dm.getSeparator().getChar(), indexY, dm.getHeader());
        }
        if (cbXSorted.isSelected()) {
            x.sort();
        }
        if (cbYSorted.isSelected()) {
            y.sort();
        }
        ValueAxis xAxis;
        ValueAxis yAxis;
        double minX;
        if (tfMinX.getText().isEmpty()) {
            minX = MinimumValue.getMinValue(x);
        } else {
            minX = Double.valueOf(tfMinX.getText());
        }
        double maxX;
        if (tfMaxX.getText().isEmpty()) {
            maxX = MaximumValue.getMaxValue(x);
        } else {
            maxX = Double.valueOf(tfMaxX.getText());
        }
        double minY;
        if (tfMinY.getText().isEmpty()) {
            minY = MinimumValue.getMinValue(y);
        } else {
            minY = Double.valueOf(tfMinY.getText());
        }
        double maxY;
        if (tfMaxY.getText().isEmpty()) {
            maxY = MaximumValue.getMaxValue(y);
        } else {
            maxY = Double.valueOf(tfMaxY.getText());
        }
        if (cbLogX.isSelected()) {
            if (!cbAutoAxis.isSelected()) {
                xAxis = new LogarithmicAxis(minX, maxX);
                xAxis.setAutoRanging(false);
            } else {
                xAxis = new LogarithmicAxis();
                xAxis.setAutoRanging(true);
            }

        } else {
            xAxis = new NumberAxis(tfXLabel.getText(), minX, maxX, (maxX - minX) / 10);
        }

        if (cbLogY.isSelected()) {
            if (!cbAutoAxis.isSelected()) {
                yAxis = new LogarithmicAxis(minY, maxY);
                yAxis.setAutoRanging(false);
            } else {
                yAxis = new LogarithmicAxis();
                yAxis.setAutoRanging(true);
            }

        } else {
            yAxis = new NumberAxis(tfYLabel.getText(), minY, maxY, (maxY - minY) / 10);
        }

        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        ScatterChart.Series serie = new XYChart.Series();
        serie.setName(tfSerieLabel.getText());
        for (int i = 0; i < x.size(); i++) {
            serie.getData().add(
                    new XYChart.Data<>(x.get(i), y.get(i)));
        }
        scatterChart.setVisible(false);
        data.add(serie);
        scatterChart = new ScatterChart(xAxis, yAxis, data);
        gpStage.add(scatterChart, 3, 1, 1, 13);

        scatterChart.setTitle(tfGraphLabel.getText());
        scatterChart.getXAxis()
                .setLabel(tfXLabel.getText());
        scatterChart.getYAxis()
                .setLabel(tfYLabel.getText());
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

    @FXML
    protected void cbAxisChange() {
        if (cbAutoAxis.isSelected()) {
            tfMinX.setDisable(true);
            tfMaxX.setDisable(true);
            tfMinY.setDisable(true);
            tfMaxY.setDisable(true);
        } else {
            tfMinX.setDisable(false);
            tfMaxX.setDisable(false);
            tfMinY.setDisable(false);
            tfMaxY.setDisable(false);
        }
    }

}
