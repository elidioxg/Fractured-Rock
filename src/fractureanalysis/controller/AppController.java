package fractureanalysis.controller;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.analysis.FractureIntensity;
import fractureanalysis.data.DatasetProperties;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.stages.BarChartStage;
import fractureanalysis.stages.EstimateStage;
import fractureanalysis.stages.LineChartStage;
import fractureanalysis.stages.OpenDataStage;
import fractureanalysis.statistics.ClassInterval;
import fractureanalysis.statistics.Frequency;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import fractureanalysis.statistics.SampleAmplitude;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppController implements Initializable {          
    
    @FXML 
    protected TableView tvDataset;

    @FXML
    private TextField tfFilename;
    
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
        ArrayList<Double> spArray;
        spArray = OpenDataset.openCSVFileToDouble(
                FractureAnalysis.getInstance().file.getFileName(), 
                FractureAnalysis.getInstance().file.getSeparator(), spIndex,
                FractureAnalysis.getInstance().file.getHeader());
        ArrayList<Double> apArray;
        apArray = OpenDataset.openCSVFileToDouble(
                FractureAnalysis.getInstance().file.getFileName(), 
                FractureAnalysis.getInstance().file.getSeparator(), apIndex,
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
    protected TextField tfSeparator;
    
    @FXML protected ScrollPane spProperties;
    @FXML protected GridPane paneProperties;
    @FXML protected ComboBox cbColumnAp;
    @FXML protected ComboBox cbColumnSp;
    
    @FXML
    protected void addToList() throws IOException {
        if (cbHeader.isSelected()) {
            FractureAnalysis.getInstance().file.setHeader(true);
        } else {
            FractureAnalysis.getInstance().file.setHeader(false);
        }
        
        String sep = tfSeparator.getCharacters().toString();
        int columnCount = DatasetProperties.getColumnsCount(
                        FractureAnalysis.getInstance().file.getFileName(), sep);
        FractureAnalysis.getInstance().file.setSeparator(sep);
        FractureAnalysis.getInstance().file.setColumnsNumber(
                columnCount);
        if(FractureAnalysis.getInstance().file.getColumnsNumber()>1){
            FractureAnalysis.getInstance().file.setColumnAp(0);
            FractureAnalysis.getInstance().file.setColumnSp(1);
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
        od.createWindow();
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
    protected void estimateStage() throws IOException {
        EstimateStage es = new EstimateStage();
        es.createStage();
    }

    @FXML
    protected void barChartStage() throws IOException {
        BarChartStage bcs = new BarChartStage();
        bcs.createStage();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    @FXML 
    protected Label lMinValue, lMaxValue, lStdDevValue, lAvgValue;
    
    @FXML 
    protected ComboBox cbSColumn;
   
    @FXML 
    protected void cbSummaryChange(){        
        int colIndex = cbSColumn.getSelectionModel().getSelectedIndex();
        FractureAnalysis.getInstance().setColumnStatistics(
                FractureAnalysis.getInstance().file.getFileName(),
                FractureAnalysis.getInstance().file.getSeparator(),
                colIndex);                 
    }
    
    @FXML 
    protected BarChart chartHistogram;
    
    @FXML
    protected ComboBox cbColIndex;
    @FXML 
    protected void cbHistogramChange(){
        int index = cbColIndex.getSelectionModel().getSelectedIndex();
        ArrayList array = OpenDataset.openCSVFileToDouble(
                FractureAnalysis.getInstance().file.getFileName(), 
                FractureAnalysis.getInstance().file.getSeparator(), index, true);
        double amplitude = SampleAmplitude.getAmplitude(array);
        double classIntervals = Frequency.sturgesExpression(amplitude, array.size());
        double min = MinimumValue.getMinValue(array);
        double max = MaximumValue.getMaxValue(array);
        ArrayList<ClassInterval> intervals = Frequency.classIntervals(min, max, classIntervals);
        intervals = Frequency.countObsFrequency(array, intervals);
        
        XYChart.Series series= new XYChart.Series();
        series.setName("Histogram");        
        for(int i = 0; i < intervals.size(); i++){
            series.getData().add(
                    new XYChart.Data(intervals.get(i).getLabel(), intervals.get(i).getObsFrequency()));
        }        
        chartHistogram.getData().clear();
        chartHistogram.getData().addAll(series);
    }
        
}
