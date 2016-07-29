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

import fractureanalysis.controller.AppController;
import fractureanalysis.data.DatasetProperties;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.AnalysisFile;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.statistics.Average;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import javafx.application.Application;
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

    public AnalysisFile file = new AnalysisFile();
    public Stage stage;   

    public ListView listView;

    private List<DatasetModel> list;
    public ArrayList<String> datasets = new ArrayList<>();

    private AppController controller;

    private static FractureAnalysis instance;
    
    private GridPane grid;

    public FractureAnalysis() {
        instance = this;
    }

    public static FractureAnalysis getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource(
                    "views/appFXML.fxml"));
            grid = root.load();
            controller = root.getController();
            Scene scene = new Scene(grid);
            list = new ArrayList();
            listView = new ListView();

            listView.setCellFactory(new Callback<ListView<DatasetModel>, ListCell<DatasetModel>>() {
                @Override
                public ListCell<DatasetModel> call(ListView<DatasetModel> myObjectListView) {
                    ListCell<DatasetModel> cell = new ListCell<DatasetModel>() {
                        @Override
                        protected void updateItem(DatasetModel myObject, boolean b) {
                            super.updateItem(myObject, b);
                            if (myObject != null) {
                                setText(myObject.getName());
                                setColumnStatistics(
                                        myObject.getFileName(), myObject.getSeparator(), 0);
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
                        file.setFilename(dm.getFileName());
                        file.setDatasetName(dm.getName());
                        file.setSeparator(dm.getSeparator());
                        file.setHeader(dm.getHeader());
                        file.setHeaderStrings(dm.getHeaderStrings());
                        controller.populateTable(dm.getFileName(), dm.getSeparator(), dm.getHeader());
                        setColumnStatistics(dm.getFileName(), dm.getSeparator(), dm.getCurrentColumn());
                        try {
                            columnsComboboxSummary(dm.getFileName(), dm.getSeparator());
                        } catch (IOException ex) {
                            Logger.getLogger(FractureAnalysis.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            grid.add(listView, 1, 3);
            primaryStage.setTitle(strAppName);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void updateListView() {
        list.add(new DatasetModel(file.getDatasetName(), file.getFileName(),
                file.getSeparator(), file.getHeader(), file.getHeaderStrings()));

        datasets.add(file.getDatasetName());
        ObservableList<DatasetModel> olDatasets
                = FXCollections.observableList(list);

        listView.setItems(null);
        listView.setItems(olDatasets);

    }
    
    public void setColumnStatistics(String filename, String separator, int columnIndex){
        if(FractureAnalysis.getInstance().file.getColumnsNumber()>0){
            ArrayList<Double> array = 
                    OpenDataset.openCSVFileToDouble(filename, separator, columnIndex, true);
            double avg = Average.arithmeticAverage(array);
            Label lAvg = (Label) grid.lookup("#lAvgValue");
            lAvg.setText(String.valueOf(avg));
        }        
    }
    
    private void columnsComboboxSummary(String filename, String separator) throws IOException{
        ObservableList<String> ol = 
                FXCollections.observableList(
                        DatasetProperties.getHeaders(filename, separator));
        
        ComboBox cbSColumn = (ComboBox)grid.lookup("#cbSColumn");
        cbSColumn.setItems(ol);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(FractureAnalysis.class, args);
    }
}
