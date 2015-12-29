/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractureanalysis.controller;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.openData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author exg
 */
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
    private ArrayList<DatasetModel> list;

    
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
                        FractureAnalysis.getInstance().headerStrings = headerValues;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                for (int column = 0; column < headerValues.length; column++) {
                                    tvDataset.getColumns().add(
                                            createColumn(column, headerValues[column]));
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
                                for (int columnIndex = tvDataset.getColumns().size();
                                        columnIndex < dataValues.length; columnIndex++) {
                                    tvDataset.getColumns().add(createColumn(columnIndex, ""));
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

    private TableColumn<ObservableList<StringProperty>, String> createColumn(
            final int columnIndex, String columnTitle) {
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
        String title;
        if (columnTitle == null || columnTitle.trim().length() == 0) {
            title = "Column " + (columnIndex + 1);
        } else {
            title = columnTitle;
        }
        column.setText(title);
        column.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObservableList<
                StringProperty>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
                ObservableList<StringProperty> values = cellDataFeatures.getValue();
                if (columnIndex >= values.size()) {
                    return new SimpleStringProperty("");
                } else {
                    return cellDataFeatures.getValue().get(columnIndex);
                }
            }
        });
        return column;
    }

    @FXML
    private void datasetListAdd() throws IOException {
        if (cbHeader == null) {
            System.out.println("cbHeader=null");
        }
        if (cbHeader.isSelected()) {
            FractureAnalysis.getInstance().hasHeader = true;
            System.out.println("CheckBox Header is Selected");
        } else {
            FractureAnalysis.getInstance().hasHeader = false;
            System.out.println("CheckBox Header is NOT Selected");
        }
        FractureAnalysis.getInstance().updateListView();
    }

    @FXML
    private void dialogOpen() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(
                FractureAnalysis.getInstance().stage);
        if (file.getAbsolutePath() != null) {

            tfFilename.setText(file.getAbsolutePath());
            FractureAnalysis.getInstance().datasetName = file.getName();
            FractureAnalysis.getInstance().datasetFilename = file.getAbsolutePath();
        }
    }

    private Stage stageOpenWindow;
    
    @FXML
    protected void openFileStage() throws IOException {
        openData od = new openData();
        stageOpenWindow = od.createWindow();
    }
    
    @FXML
    public void closeOpenFileStage() throws IOException{
        stageOpenWindow.close();
    }

    @FXML private Stage stageLine;
    
    @FXML
    protected void lineChartStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                FractureAnalysis.getInstance().getClass().getResource(
                        "views/lineChartFXML.fxml"));
        Parent parent = (Parent) loader.load();
        List list = new ArrayList();
        for (int i = 0; i < FractureAnalysis.getInstance().headerStrings.length; i++) {
            list.add(FractureAnalysis.getInstance().headerStrings[i]);
        }
        ObservableList ol = FXCollections.observableArrayList(list);
        comboBoxX = (ComboBox) parent.lookup("#comboBoxX");
        comboBoxY = (ComboBox) parent.lookup("#comboBoxY");
        comboBoxX.setItems(ol);
        comboBoxY.setItems(ol);

        stageLine = new Stage();
        Scene scene = new Scene(parent);
        stageLine.setTitle("Line Chart");
        stageLine.setScene(scene);
        stageLine.show();
    }
    
    @FXML private void closeLineStage() throws IOException{
        stageLine.close();
    }
    
    @FXML private void closeBarStage() throws IOException{
        stageBar.close();
    }

    @FXML
    private ComboBox cbXData, cbYData;
    
    private Stage stageBar;

    @FXML
    protected void barChartStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(FractureAnalysis.getInstance().getClass().getResource(
                "views/barChartFXML.fxml"));
        Parent parent = (Parent) loader.load();
        List list = new ArrayList();
        //to do:  platform.runlater {
        for (int i = 0; i < FractureAnalysis.getInstance().headerStrings.length; i++) {
            list.add(FractureAnalysis.getInstance().headerStrings[i]);
        }
        ObservableList ol = FXCollections.observableArrayList(list);
        if (cbXData == null) {
            System.out.println("cbXData is null");
        }
        if (cbYData == null) {
            System.out.println("cbYData is null");
        }
        cbXData = (ComboBox) parent.lookup("#cbDataX");
        cbYData = (ComboBox) parent.lookup("#cbDataY");
        cbXData.setItems(ol);
        cbYData.setItems(ol);

        stageBar = new Stage();
        Scene scene = new Scene(parent);
        stageBar.setTitle("Bar Chart");
        stageBar.setScene(scene);
        stageBar.show();
    }

    @FXML
    private TextField tfGraphLabel, tfXLabel, tfYLabel, tfSerieLabel;

    @FXML
    private LineChart lineChart;    
    
    private XYChart.Series plotLineSeries(String filename, String separator,
            String serieLabel, int columnX, int columnY) {
        
        XYChart.Series<Number, Number> series = new XYChart.Series();
        series.setName(serieLabel);

        Task<List<XYChart.Data<Number, Number>>> task;
        task = new Task<List<XYChart.Data<Number, Number>>>() {
            @Override
            protected List<XYChart.Data<Number, Number>> call() throws Exception {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                List<XYChart.Data<Number, Number>> chartData = new ArrayList<>();
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    final String[] dataValues = dataLine.split(separator);
                    chartData.add(new XYChart.Data(dataValues[columnX],
                            dataValues[columnY]));
                }
                return chartData;
            }
        };
        task.setOnSucceeded(e -> series.getData().addAll(task.getValue()));

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return series;
    }

    @FXML
    private BarChart barChart;    
    @FXML
    private TextField tfLabelChart, tfLabelX, tfLabelY, tfBarSerie;    
    @FXML
    private ComboBox cbDataX, cbDataY;    
    @FXML
    private CheckBox cbSortX, cbSortY;
    
    private XYChart.Series<String, Number> plotBarSeries(String filename, String separator, 
            String serieName, int columnX, int columnY){
        XYChart.Series<String, Number> series = new XYChart.Series();
        series.setName(serieName);

        Task<List<XYChart.Data<String, Number>>> task;
        task = new Task<List<XYChart.Data<String, Number>>>() {
            @Override
            protected List<XYChart.Data<String, Number>> call() throws Exception {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                List<XYChart.Data<String, Number>> chartData = new ArrayList<>();
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    final String[] dataValues = dataLine.split(separator);
                    chartData.add(new XYChart.Data(dataValues[columnX],
                            Float.valueOf(dataValues[columnY])));
                }
                return chartData;
            }
        };
        task.setOnSucceeded(e -> series.getData().addAll(task.getValue()));

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return series;
    }

    @FXML
    public void plotBar() {
        tfLabelChart.lookup("tfLabelChart");
        tfBarSerie.lookup("tfBarSerie");
        barChart.lookup("barChart");
        tfLabelX.lookup("tfLabelX");
        tfLabelY.lookup("tfLabelY");
        cbDataX.lookup("cbDataX");
        cbDataY.lookup("cbDataY");
        cbSortX.lookup("cbSortX");
        cbSortY.lookup("cbSortY");
        int indexX = cbDataX.getSelectionModel().getSelectedIndex();
        int indexY = cbDataY.getSelectionModel().getSelectedIndex();
        barChart.setTitle(tfLabelChart.getText());
        barChart.getXAxis().setLabel(tfLabelX.getText());
        barChart.getYAxis().setLabel(tfLabelY.getText());        
        barChart.getData().add(plotBarSeries(FractureAnalysis.getInstance().datasetFilename,
                FractureAnalysis.getInstance().separator,
                tfBarSerie.getText(),
                indexX, indexY));
    }

    public void plotLine() {
        lineChart.lookup("lineChart");
        tfGraphLabel.lookup("tfGraphLabel");
        tfXLabel.lookup("tfXLabel");
        tfYLabel.lookup("tfYLabel");
        tfSerieLabel.lookup("tfSerieLabel");
        comboBoxX.lookup("comboBoxX");
        comboBoxY.lookup("comboBoxY");
        int indexX = comboBoxX.getSelectionModel().getSelectedIndex();
        int indexY = comboBoxY.getSelectionModel().getSelectedIndex();
        lineChart.setTitle(tfGraphLabel.getText());
        lineChart.getXAxis().setLabel(tfXLabel.getText());
        lineChart.getYAxis().setLabel(tfYLabel.getText());
        lineChart.getData().add(plotLineSeries(FractureAnalysis.getInstance().datasetFilename,
                FractureAnalysis.getInstance().separator,
                tfSerieLabel.getText(),
                indexX, indexY));
    }

    @FXML
    private void clearLineChart() {
        lineChart.getData().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
