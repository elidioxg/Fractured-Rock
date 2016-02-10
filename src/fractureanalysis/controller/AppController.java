package fractureanalysis.controller;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.analysis.FractureIntensity;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.stages.BarChartStage;
import fractureanalysis.stages.EstimateStage;
import fractureanalysis.stages.LineChartStage;
import fractureanalysis.stages.OpenDataStage;
import fractureanalysis.table.TableUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppController implements Initializable {

    @FXML
    private ComboBox comboBoxX;

    @FXML
    private ComboBox comboBoxY;

    @FXML
    private TextField tfFilename;

    @FXML
    private TableView tvDataset;

    @FXML
    private ListView lvDatasets;

    @FXML
    private CheckBox cbHeader;

    @FXML
    protected ComboBox cbSpValues;
    @FXML
    protected ComboBox cbApValues;
    
    @FXML protected TableView tvProcessed;

    @FXML
    protected void estimate() {
        int spIndex = cbSpValues.getSelectionModel().getSelectedIndex();
        int apIndex = cbApValues.getSelectionModel().getSelectedIndex();
        OpenDataset od = new OpenDataset();
        ArrayList<Double> spArray = new ArrayList<>();
        spArray = od.openCSVFileToDouble(FractureAnalysis.getInstance().file.getFileName(), FractureAnalysis.getInstance().file.getSeparator(), spIndex,
                FractureAnalysis.getInstance().file.getHeader());
        ArrayList<Double> apArray = new ArrayList<>();
        apArray = od.openCSVFileToDouble(FractureAnalysis.getInstance().file.getFileName(), FractureAnalysis.getInstance().file.getSeparator(), apIndex,
                FractureAnalysis.getInstance().file.getHeader());
        FractureIntensity fi = new FractureIntensity();
        ArrayList[][] arraySorted = fi.listAndSort(spArray, true);
        double scanlineLength = fi.getScanlineLength(apArray,
                FractureAnalysis.getInstance().file.getHeader());
        ArrayList[][] arrayNormalized = fi.filterAndNormalize(
                arraySorted, scanlineLength);
        TableUtils tu = new TableUtils();
        tu.AddColumn(tvProcessed, arrayNormalized,"test", 0,0);
    }                

    @FXML//mouse handler for ListView lvDatasets
    protected void onMouseClicked() throws IOException {
        DatasetModel dm = (DatasetModel) lvDatasets.getSelectionModel().getSelectedItem();
        populateTable(dm.getFileName(), dm.getSeparator(), dm.getHeader());
    }

    @FXML
    public void populateTable(final String filename, final String separator,
            final boolean hasHeader) {
        tvDataset.getItems().clear();
        tvDataset.getColumns().clear();
        if (filename.trim() != null) {
            tvDataset.setPlaceholder(new Label("Loading..."));
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    BufferedReader br = new BufferedReader(new FileReader(filename));
                    final String headerLine = br.readLine();
                    final String[] headerValues = headerLine.split(separator);
                    if (hasHeader) {
                        FractureAnalysis.getInstance().file.setHeaderStrings(headerValues);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                TableUtils tu = new TableUtils();
                                for (int column = 0; column < headerValues.length; column++) {
                                    tvDataset.getColumns().add(
                                            tu.createColumn(column, headerValues[column]));
                                }
                            }
                        });
                    }
                    String dataLine;
                    while ((dataLine = br.readLine()) != null) {
                        final String[] dataValues = dataLine.split(separator);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // Add additional columns if necessary:
                                TableUtils tu = new TableUtils();
                                for (int columnIndex = tvDataset.getColumns().size();
                                        columnIndex < dataValues.length; columnIndex++) {
                                    tvDataset.getColumns().add(tu.createColumn(columnIndex, ""));
                                }
                                ObservableList<StringProperty> data = FXCollections
                                        .observableArrayList();
                                for (String value : dataValues) {
                                    data.add(new SimpleStringProperty(value));
                                }
                                tvDataset.getItems().add(data);
                            }
                        });
                    }
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @FXML
    protected void datasetListAdd() throws IOException {
        if (cbHeader.isSelected()) {
            FractureAnalysis.getInstance().file.setHeader(true);
        } else {
            FractureAnalysis.getInstance().file.setHeader(false);
        }
        FractureAnalysis.getInstance().updateListView();
    }

    @FXML
    protected void dialogOpen() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(
                FractureAnalysis.getInstance().stage);
        if (file.getAbsolutePath() != null) {

            tfFilename.setText(file.getAbsolutePath());
            FractureAnalysis.getInstance().file.setDatasetName(file.getName());
            FractureAnalysis.getInstance().file.setFilename(file.getAbsolutePath());
        }
    }

    @FXML
    protected void openFileStage() throws IOException {
        OpenDataStage od = new OpenDataStage();
        Stage stageOpenWindow = od.createWindow();
    }

    @FXML
    Button btnClose;

    @FXML
    public void closeOpenFileStage() throws IOException {
        Stage stageOpenWindow = (Stage) btnClose.getScene().getWindow();
        stageOpenWindow.close();
    }

    @FXML
    protected void lineChartStage() throws IOException {
        LineChartStage lcs = new LineChartStage();
        lcs.createStage();
    }
    @FXML
    protected Button btnCloseLine;

    @FXML
    protected void closeLineStage() throws IOException {
        Stage stageLine = (Stage) btnCloseLine.getScene().getWindow();
        stageLine.close();
    }
    @FXML
    protected Button btnCloseBar;

    @FXML
    protected void closeBarStage() throws IOException {
        Stage stageBar = (Stage) btnCloseBar.getScene().getWindow();
        stageBar.close();
    }

    @FXML
    protected ComboBox cbDatasets;

    @FXML
    protected void estimateStage() throws IOException {
        EstimateStage es = new EstimateStage();
        es.createStage();
    }

    @FXML
    protected void barChartStage() throws IOException {
        BarChartStage bcs = new BarChartStage();
        bcs.createStage();
    }

    @FXML
    protected TextField tfGraphLabel, tfXLabel, tfYLabel, tfSerieLabel;

    @FXML
    protected LineChart lineChart;

    @FXML
    protected BarChart barChart;
    @FXML
    protected TextField tfLabelChart, tfLabelX, tfLabelY, tfBarSerie;
    @FXML
    protected ComboBox cbDataX, cbDataY;
    @FXML
    protected CheckBox cbSortX, cbSortY;

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
    protected void plotLine() {
        int indexX = comboBoxX.getSelectionModel().getSelectedIndex();
        int indexY = comboBoxY.getSelectionModel().getSelectedIndex();
        lineChart.setTitle(tfGraphLabel.getText());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
