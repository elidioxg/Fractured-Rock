package fractureanalysis.controller;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.data.DatasetProperties;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.model.Separator;
import fractureanalysis.stages.FractureAnalysisStage;
import fractureanalysis.stages.HistogramStage;
import fractureanalysis.stages.LineChartStage;
import fractureanalysis.stages.OpenDataStage;
import fractureanalysis.stages.ScatterChartStage;
import fractureanalysis.stages.Variogram1DStage;
import fractureanalysis.stages.VariogramStage;
import fractureanalysis.statistics.histogram.ClassInterval;
import fractureanalysis.statistics.histogram.Frequency;
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
import java.util.Arrays;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected TableView tvDataset;

    @FXML
    private TextField tfFilename;

    @FXML
    private ListView lvDatasets;

    @FXML
    private CheckBox cbHeader;

    /**
     * Mouse handler for datasets listview on main stage
     *
     * @throws IOException
     */
    @FXML
    protected void onMouseClicked() throws IOException {
        DatasetModel dm = (DatasetModel) lvDatasets.getSelectionModel().getSelectedItem();
        if (dm != null) {
            populateTable(dm.getFileName(), dm.getSeparator(), dm.getHeader());
        }
    }

    /**
     * Populates the table on main stage. This table is used to view dataset
     * values.
     * 
     * TODO: Tirar esse procedimento daqui 
     * 
     * @param filename
     * @param separator
     * @param hasHeader
     */
    
    public void populateTable(final String filename, final Separator separator,
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
                    final String[] headerValues = headerLine.split(separator.getChar());
                    ArrayList<String> array = new ArrayList<>(Arrays.asList(headerValues));
                    if (hasHeader) {
                        FractureAnalysis.getInstance().file.setHeaderStrings(array);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                TableUtils tu = new TableUtils();
                                for (int i = 0; i < headerValues.length; i++) {
                                    tvDataset.getColumns().add(
                                            tu.createColumn(i, headerValues[i]));
                                }
                            }
                        });
                    } else {
                        Platform.runLater(() -> {
                            TableUtils tu = new TableUtils();
                            for (int i = 0; i < headerValues.length; i++) {
                                tvDataset.getColumns().add(
                                        tu.createColumn(i, "Column " + String.valueOf(i + 1)));
                            }
                        });
                    }
                    String dataLine;
                    while ((dataLine = br.readLine()) != null) {
                        final String[] dataValues = dataLine.split(separator.getChar());
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

    @FXML
    protected RadioButton rbTab, rbComma, rbSemicolon, rbOther;

    @FXML
    protected void rbTabAction() {
        rbTab.setSelected(true);
        rbComma.setSelected(false);
        rbSemicolon.setSelected(false);
        rbOther.setSelected(false);
    }

    @FXML
    protected void rbCommaAction() {
        rbTab.setSelected(false);
        rbComma.setSelected(true);
        rbSemicolon.setSelected(false);
        rbOther.setSelected(false);
    }

    @FXML
    protected void rbSemicolonAction() {
        rbTab.setSelected(false);
        rbComma.setSelected(false);
        rbSemicolon.setSelected(true);
        rbOther.setSelected(false);
    }

    @FXML
    protected void rbOtherAction() {
        rbTab.setSelected(false);
        rbComma.setSelected(false);
        rbSemicolon.setSelected(false);
        rbOther.setSelected(true);
    }

    @FXML
    protected ScrollPane spProperties;
    @FXML
    protected GridPane paneProperties;
    @FXML
    protected ComboBox cbColumnAp;
    @FXML
    protected ComboBox cbColumnSp;

    /**
     * Add a dataset to the list of datasets on main form and update the
     * datasets listview.
     *
     * @throws IOException
     */
    @FXML
    protected void addToList() throws IOException, Exception {
        if (cbHeader.isSelected()) {
            FractureAnalysis.getInstance().file.setHeader(true);
        } else {
            FractureAnalysis.getInstance().file.setHeader(false);
        }
        Separator sep;
        if (rbTab.isSelected()) {
            sep = new Separator(0);
        } else if (rbSemicolon.isSelected()) {
            sep = new Separator(1);
        } else if (rbComma.isSelected()) {
            sep = new Separator(2);;
        } else {
            String aux = tfSeparator.getCharacters().toString();
            if (aux.length() != 0) {
                sep = new Separator(aux);
            } else {
                sep = new Separator();
                throw new Exception("Invalid Separator");
            }
        }
        if (!tfFilename.getText().trim().isEmpty()) {
            File file = new File(tfFilename.getText());
            FractureAnalysis.getInstance().file.setFilename(file.getAbsolutePath());
            FractureAnalysis.getInstance().file.setDatasetName(file.getName());
            int columnCount = DatasetProperties.getColumnsCount(file.getAbsolutePath(), sep);
            FractureAnalysis.getInstance().file.setHeaderStrings(
                    DatasetProperties.getHeaders(file.getAbsolutePath(), sep));
            FractureAnalysis.getInstance().file.setSeparator(sep);
            FractureAnalysis.getInstance().file.setColumnsCount(
                    columnCount);
            int rowCount = DatasetProperties.getRowCount(file.getAbsolutePath());
            FractureAnalysis.getInstance().file.setRowsCount(rowCount);
            if (FractureAnalysis.getInstance().file.getColumnsCount() > 1) {
                FractureAnalysis.getInstance().file.setApColumn(1);
                FractureAnalysis.getInstance().file.setSpColumn(0);
            }
            FractureAnalysis.getInstance().updateListView();
        } else {
            //TODO: alert message
        }
    }

    /**
     * Open a dialog for choose the file to be opened as dataset.
     *
     * @throws IOException
     */
    @FXML
    protected void dialogOpen() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(
                FractureAnalysis.getInstance().stage);
        if (file != null) {
            if (file.exists()) {
                if (file.getAbsolutePath() != null) {
                    tfFilename.setText(file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * Create the stage for choosing the dataset file to add to program.
     *
     * @throws IOException
     */
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

    /**
     * Create a stage for line chart plotting.
     *
     * @throws IOException
     */
    @FXML
    protected void lineChartStage() throws IOException {
        LineChartStage lcs = new LineChartStage(
                FractureAnalysis.getInstance().getDatasetList());
        lcs.createStage();
    }

    @FXML
    protected void scatterChartStage() throws IOException {
        ScatterChartStage scs = new ScatterChartStage(
                FractureAnalysis.getInstance().getDatasetList());
        scs.createStage();
    }

    @FXML
    protected void histogramStage() throws IOException {
        HistogramStage hs = new HistogramStage(
                FractureAnalysis.getInstance().getDatasetList());
        hs.createStage();
    }

    @FXML
    protected void variogramStage() throws IOException {
        VariogramStage vs = new VariogramStage(
                FractureAnalysis.getInstance().getDatasetList());
        vs.createStage();
    }
    
    @FXML
    protected void variogramStage2() throws IOException {
        VariogramStage vs = new VariogramStage(
                FractureAnalysis.getInstance().getDatasetList());
        vs.createStage2();
    }
    
    @FXML
    protected void variogram1DStage() throws IOException {
        Variogram1DStage stage = new Variogram1DStage(
                FractureAnalysis.getInstance().getDatasetList());
        stage.createStage();
    }
    
    @FXML
    protected void fractureStage() throws IOException{
        FractureAnalysisStage stage = new FractureAnalysisStage(
                FractureAnalysis.getInstance().getDatasetList());
        stage.createStage();
    }

    @FXML
    protected Label lMinValue, lMaxValue, lStdDevValue, lAvgValue;

    @FXML
    protected ComboBox cbSColumn;

    @FXML
    protected void cbSummaryChange() throws Exception {
        int colIndex = cbSColumn.getSelectionModel().getSelectedIndex();
        if (colIndex >= 0) {
            FractureAnalysis.getInstance().setColumnStatistics(
                    FractureAnalysis.getInstance().file.getFileName(),
                    FractureAnalysis.getInstance().file.getSeparator(),
                    colIndex, FractureAnalysis.getInstance().file.getHeader());
        }
    }

    @FXML
    protected BarChart chartHistogram;

    @FXML
    protected ComboBox cbColIndex;

    /**
     * When the combobox representing the column of dataset in current use is
     * changed, this procedure is executed. This procedure will clear and
     * generate the Histogram of one column of dataset.
     */
    @FXML
    protected void cbHistogramChange() throws Exception {
        int index = cbColIndex.getSelectionModel().getSelectedIndex();
        boolean header = FractureAnalysis.getInstance().file.getHeader();
        Vector vector = new Vector();
        if (index >= 0) {
            vector = OpenDataset.openCSVFileToVector(
                    FractureAnalysis.getInstance().file.getFileName(),
                    FractureAnalysis.getInstance().file.getSeparator().getChar(), index, header);
        }
        double amplitude = SampleAmplitude.getAmplitude(vector);
        double classIntervals = Frequency.sturgesExpression(amplitude, vector.size());
        double min = MinimumValue.getMinValue(vector);
        double max = MaximumValue.getMaxValue(vector);
        ArrayList<ClassInterval> intervals = Frequency.classIntervals(min, max, classIntervals);
        Frequency.countObsFrequency(vector, intervals);

        XYChart.Series series = new XYChart.Series();
        series.setName("Histogram");
        for (int i = 0; i < intervals.size(); i++) {
            series.getData().add(
                    new XYChart.Data(intervals.get(i).getLabel(), intervals.get(i).getObsFrequency()));
        }
        chartHistogram.getData().clear();
        chartHistogram.getData().addAll(series);
    }

    /**
     * Finish the program.
     */
    @FXML
    protected void close() {
        Platform.exit();
        System.exit(0);
    }

}
