/*
 * Copyright (C) 2016 elidioxg
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractureanalysis;

/**
 *
 * @author elidioxg
 */
import fractureanalysis.Vectors.Vector;
import fractureanalysis.controller.AppController;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.AnalysisFile;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.statistics.Average;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import fractureanalysis.statistics.Mode;
import fractureanalysis.statistics.Quartiles;
import fractureanalysis.statistics.StdDeviation;
import fractureanalysis.statistics.Variance;
import fractureanalysis.statistics.VariationCoefficient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class FractureAnalysis extends Application {

    private final String strAppName = "Application Name";

    public Stage stage;

    public ListView<DatasetModel> listView;

    private List<DatasetModel> list;

    private AppController controller;

    private static FractureAnalysis instance;

    private GridPane grid;

    public FractureAnalysis() {
        instance = this;
    }

    public static FractureAnalysis getInstance() {
        return instance;
    }

    public List<DatasetModel> getDatasetList() {
        return this.list;
    }

    /**
     * Return the selected item on ListView of the Main Stage
     *
     * @return
     */
    public DatasetModel getDataset() {
        if (listView.getSelectionModel().getSelectedIndex() >= 0) {
            return this.listView.getSelectionModel().getSelectedItem();
        } else {
            return null;
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //file = new AnalysisFile();       
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource(
                    "views/stage_main.fxml"));
            grid = root.load();
            controller = root.getController();
            Scene scene = new Scene(grid);
            list = new ArrayList();
            listView = (ListView) grid.lookup("#lvDatasets");

            listView.setCellFactory(new Callback<ListView<DatasetModel>, ListCell<DatasetModel>>() {
                @Override
                public ListCell<DatasetModel> call(ListView<DatasetModel> myObjectListView) {
                    ListCell<DatasetModel> cell = new ListCell<DatasetModel>() {
                        @Override
                        protected void updateItem(DatasetModel myObject, boolean b) {
                            super.updateItem(myObject, b);
                            if (myObject != null) {
                                setText(myObject.getDatasetName());
                            }
                        }
                    };
                    return cell;
                }
            });

            listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DatasetModel dm = (DatasetModel) listView.getSelectionModel().getSelectedItem();
                    if (dm != null) {                        
                        try {
                            controller.populateTable(dm);
                        } catch (IOException ex) {
                            Logger.getLogger(FractureAnalysis.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            setColumnStatistics(dm, 0);
                        } catch (Exception ex) {
                            Logger.getLogger(FractureAnalysis.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        setDatasetStatistics(dm);
                        itemsComboboxes();

                    }
                }
            });

            primaryStage.setOnCloseRequest(e -> Platform.exit());
            primaryStage.setTitle(strAppName);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void addToListView(DatasetModel file) {
        list.add(file);
        ObservableList<DatasetModel> olDatasets
                = FXCollections.observableList(list);
        listView.setItems(olDatasets);
    }

    /**
     * Set the statistics to selected column on combobox
     *
     * @param dataset
     * @param column
     * @throws java.lang.Exception
     */
    public void setColumnStatistics(DatasetModel dataset, int column) throws Exception {
        if (dataset.getColumnsCount() > 0) {
            Vector array;
            if (dataset.isGeoeas()) {
                array = OpenDataset.openGeoeasToVector(
                        dataset.getFileName(), dataset.getSeparator().getChar(),
                        column);
            } else {
                array = OpenDataset.openCSVFileToVector(
                        dataset.getFileName(), dataset.getSeparator().getChar(),
                        column, dataset.getHeader());
            }

            Label lAvg = (Label) grid.lookup("#lAvgValue");
            double avg = Average.arithmeticAverage(array);
            lAvg.setText(String.valueOf(avg));
            Label lMinValue = (Label) grid.lookup("#lMinValue");
            double min = MinimumValue.getMinValue(array);
            lMinValue.setText(String.valueOf(min));
            Label lMaxValue = (Label) grid.lookup("#lMaxValue");
            double max = MaximumValue.getMaxValue(array);
            lMaxValue.setText(String.valueOf(max));
            Label lModeValue = (Label) grid.lookup("#lModeValue");
            double mode = Mode.getMode(array);
            lModeValue.setText(String.valueOf(mode));
            Label lStdDevValue = (Label) grid.lookup("#lStdDevValue");
            double stdDev = StdDeviation.stdDeviation(array);
            lStdDevValue.setText(String.valueOf(stdDev));
            Label lVariance = (Label) grid.lookup("#lVariance");
            double variance = Variance.variance(array);
            lVariance.setText(String.valueOf(variance));
            Label lVariation = (Label) grid.lookup("#lVariation");
            double variation = VariationCoefficient.variationCoefficient(array);
            lVariation.setText(String.valueOf(variation));
            Label lGeoAvg = (Label) grid.lookup("#lGeoAvg");
            double geoAvg = Average.geometricAverage(array);
            lGeoAvg.setText(String.valueOf(geoAvg));
            Label lCount = (Label) grid.lookup("#lCount");
            lCount.setText(String.valueOf(array.size()));
            double fQ = Quartiles.firstQuartil(array);
            Label lFirstQ = (Label) grid.lookup("#lFirstQ");
            lFirstQ.setText(String.valueOf(fQ));
            double sQ = Quartiles.secondQuartil(array);
            Label lSecQ = (Label) grid.lookup("#lSecQ");
            lSecQ.setText(String.valueOf(sQ));
            double tQ = Quartiles.thirdQuartil(array);
            Label lThirdQ = (Label) grid.lookup("#lThirdQ");
            lThirdQ.setText(String.valueOf(tQ));
        }
    }

    /**
     * Add the headers to the combobox in summary view
     *
     * @param filename
     * @param separator
     * @throws IOException
     */
    private void itemsComboboxes() {
        ObservableList<String> ol
                = FXCollections.observableList(getDataset().getHeaderArray()
                );

        ComboBox cbSColumn = (ComboBox) grid.lookup("#cbSColumn");
        cbSColumn.setItems(ol);
        cbSColumn.getSelectionModel().selectFirst();
        ComboBox cbColIndex = (ComboBox) grid.lookup("#cbColIndex");
        cbColIndex.setItems(ol);
        cbColIndex.getSelectionModel().selectFirst();
        ComboBox cbColumn = (ComboBox) grid.lookup("#cbColumn");
        cbColumn.setItems(ol);
        cbColumn.getSelectionModel().selectFirst();
        ComboBox cbVarA = (ComboBox) grid.lookup("#cbVarA");
        cbVarA.setItems(ol);
        ComboBox cbVarB = (ComboBox) grid.lookup("#cbVarB");
        cbVarB.setItems(ol);
        ComboBox cbX = (ComboBox) grid.lookup("#cbX");
        cbX.setItems(ol);
        ComboBox cbY = (ComboBox) grid.lookup("#cbY");
        cbY.setItems(ol);
        ComboBox cbContent = (ComboBox) grid.lookup("#cbContent");
        cbContent.setItems(ol);
    }

    /**
     * This function is used to change the labels texts on a Tab Pane in main
     * stage.
     *
     * @param file
     */
    private void setDatasetStatistics(DatasetModel file) {
        Label lFilename = (Label) grid.lookup("#lFilename");
        lFilename.setText(file.getFileName());
        Label lSeparator = (Label) grid.lookup("#lSeparator");
        lSeparator.setText(file.getSeparator().getChar());
        Label lColumns = (Label) grid.lookup("#lColumns");
        lColumns.setText(String.valueOf(file.getColumnsCount()));
        Label lRows = (Label) grid.lookup("#lRows");
        lRows.setText(String.valueOf(file.getRowsCount()));
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Application.launch(FractureAnalysis.class, args);
    }
}
