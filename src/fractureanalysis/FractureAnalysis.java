/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractureanalysis;

import fractureanalysis.controller.AppController;
import fractureanalysis.model.DatasetModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 *
 * @author exg
 */
public class FractureAnalysis extends Application {

    private final String strAppName = "Application Name";
    //private final int appWidth = 800;
    //private final int appHeight = 600;                      

    public String datasetName = "";
    public String datasetFilename = "";
    public String separator = ",";
    public boolean hasHeader = true;
    public String[] headerStrings;

    public Stage stage;

    public ListView listView;

    private List<DatasetModel> list;

    private AppController controller;

    private static FractureAnalysis instance;

    public FractureAnalysis() {
        instance = this;
    }

    public static FractureAnalysis getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Map opt = getParameters().getNamed();
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource(
                    "views/appFXML.fxml"));
            GridPane grid = root.load();
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
                                System.out.println(myObject.getFileName());
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
                    datasetFilename = dm.getFileName();
                    datasetName = dm.getName();
                    separator = dm.getSeparator();
                    hasHeader = dm.getHeader();
                    headerStrings = dm.getHeaderStrings();
                    controller.populateTable(dm.filename, dm.separator, dm.header);
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
        list.add(new DatasetModel(datasetName, datasetFilename,
                separator, hasHeader, headerStrings));
        ObservableList<DatasetModel> olDatasets
                = FXCollections.observableList(list);
        listView.setItems(null);
        listView.setItems(olDatasets);
        //System.out.println("Header Strings: "+headerStrings);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(FractureAnalysis.class, args);
    }
}
