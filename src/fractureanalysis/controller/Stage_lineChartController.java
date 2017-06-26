package fractureanalysis.controller;

import contrib.LogarithmicAxis;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.stages.LineChartStage;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Stage_lineChartController implements Initializable {

    @FXML
    protected GridPane gpStage;

    @FXML
    protected TextField tfGraphLabel, tfXLabel, tfYLabel, tfSerieLabel,
            tfMinX, tfMaxX, tfMinY, tfMaxY;

    @FXML
    protected CheckBox cbAutoAxis, cbXSorted, cbYSorted, cbLogX, cbLogY;

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
     * Plot in Line Chart using the parameter defined by the user on Line Chart
     * Stage.
     */
    @FXML
    protected void plotLine() throws Exception {
        int datasetIndex = cbDatasets.getSelectionModel().getSelectedIndex();
        DatasetModel dm = LineChartStage.getInstance().getDatasets().get(datasetIndex);
        int indexX = comboBoxX.getSelectionModel().getSelectedIndex();
        int indexY = comboBoxY.getSelectionModel().getSelectedIndex();
        Vector x = OpenDataset.openCSVFileToVector(dm.getFileName(),
                dm.getSeparator().getChar(), indexX, dm.getHeader());
        Vector y = OpenDataset.openCSVFileToVector(dm.getFileName(),
                dm.getSeparator().getChar(), indexY, dm.getHeader());
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
        LineChart.Series serie = new XYChart.Series();
        serie.setName(tfSerieLabel.getText());
        for (int i = 0; i < x.size(); i++) {
            serie.getData().add(
                    new XYChart.Data<>(x.get(i), y.get(i)));
        }
        lineChart.setVisible(false);
        data.add(serie);
        lineChart = new LineChart(xAxis, yAxis, data);
        gpStage.add(lineChart, 3, 1, 1, 13);

        lineChart.setTitle(tfGraphLabel.getText());
        lineChart.getXAxis().setLabel(tfXLabel.getText());
        lineChart.getYAxis().setLabel(tfYLabel.getText());
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
     *
     * @throws IOException
     */
    @FXML
    protected void closeLineStage() throws IOException {
        Stage stageLine = (Stage) btnCloseLine.getScene().getWindow();
        stageLine.close();
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
