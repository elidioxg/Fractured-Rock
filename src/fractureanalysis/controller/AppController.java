package fractureanalysis.controller;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.data.DatasetProperties;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.AnalysisFile;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.model.Separator;
import fractureanalysis.stages.FractureAnalysisStage;
import fractureanalysis.stages.HistogramStage;
import fractureanalysis.stages.LineChartStage;
import fractureanalysis.stages.MatrixViewStage;
import fractureanalysis.stages.OpenDataStage;
import fractureanalysis.stages.ScatterChartStage;
import fractureanalysis.stages.VariogramStage;
import fractureanalysis.stages.View3DStage;
import fractureanalysis.statistics.histogram.ClassInterval;
import fractureanalysis.statistics.histogram.Frequency;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import fractureanalysis.statistics.SampleAmplitude;
import fractureanalysis.table.PopulateTable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
            PopulateTable.populateTable(tvDataset, dm);
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

    /**
     * Add a dataset to the list of datasets on main form and update the
     * datasets listview.
     *
     * @throws IOException
     */
    @FXML
    protected void addToList() throws IOException, Exception {
        Separator sep;
        if (rbTab.isSelected()) {
            sep = new Separator(0);
        } else if (rbSemicolon.isSelected()) {
            sep = new Separator(1);
        } else if (rbComma.isSelected()) {
            sep = new Separator(2);
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
            DatasetModel dataset = new AnalysisFile();
            dataset.setFilename(file.getAbsolutePath());
            dataset.setDatasetName(file.getName());
            int columnCount = DatasetProperties.getColumnsCount(file.getAbsolutePath(), sep);
            dataset.setHeaderStrings(
                    DatasetProperties.getHeaders(file.getAbsolutePath(), sep));
            dataset.setSeparator(sep);
            dataset.setColumnsCount(columnCount);
            int rowCount = DatasetProperties.getRowCount(file.getAbsolutePath());
            dataset.setRowsCount(rowCount);
            if (cbHeader.isSelected()) {
                dataset.setHeader(true);
            } else {
                dataset.setHeader(false);
            }
            FractureAnalysis.getInstance().addToListView(dataset);
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
    protected void openGeoeas() throws IOException {
        OpenDataStage od = new OpenDataStage();
        od.stageOpenGeoeas();
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
    protected void fractureStage() throws IOException, Exception {
        FractureAnalysisStage stage = new FractureAnalysisStage(
                FractureAnalysis.getInstance().getDataset());
        stage.createStage();
    }

    @FXML
    protected void matrixViewStage() throws IOException {
        MatrixViewStage view = new MatrixViewStage(FractureAnalysis.getInstance().getDatasetList());
        view.createStage();
    }

    @FXML
    protected void view3DStage() throws IOException {
        View3DStage stage = new View3DStage();
        stage.createSetupStage();
    }

    @FXML
    protected Label lMinValue, lMaxValue, lStdDevValue, lAvgValue;

    @FXML
    protected ComboBox cbSColumn;

    @FXML
    protected void cbSummaryChange() throws Exception {
        int colIndex = cbSColumn.getSelectionModel().getSelectedIndex();
        if (colIndex >= 0) {
            DatasetModel dataset
                    = FractureAnalysis.getInstance().listView.getSelectionModel().getSelectedItem();
            FractureAnalysis.getInstance().setColumnStatistics(dataset, colIndex);
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
     *
     * @throws java.lang.Exception
     */
    @FXML
    protected void cbHistogramChange() throws Exception {
        int index = cbColIndex.getSelectionModel().getSelectedIndex();
        boolean header = FractureAnalysis.getInstance().getDataset().getHeader();
        boolean geoeas = FractureAnalysis.getInstance().getDataset().isGeoeas();
        Vector vector = new Vector();
        if (index >= 0) {
            if (geoeas) {
                vector = OpenDataset.openGeoeasToVector(
                        FractureAnalysis.getInstance().getDataset().getFileName(),
                        FractureAnalysis.getInstance().getDataset().getSeparator().getChar(),
                        index);
            } else {
                vector = OpenDataset.openCSVFileToVector(
                        FractureAnalysis.getInstance().getDataset().getFileName(),
                        FractureAnalysis.getInstance().getDataset().getSeparator().getChar(),
                        index, header);
            }

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
