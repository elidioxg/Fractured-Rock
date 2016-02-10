package fractureanalysis;

import fractureanalysis.controller.AppController;
import fractureanalysis.data.CSVFile;
import fractureanalysis.model.DatasetModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class FractureAnalysis extends Application {

    private final String strAppName = "Application Name";
    public CSVFile file = new CSVFile();    
    public Stage stage;
    public Stage testStage;

    public ListView listView;

    private List<DatasetModel> list;
    public ArrayList<String> datasets = new ArrayList<>();

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
                    file.setFilename(dm.getFileName());
                    file.setDatasetName(dm.getName());
                    file.setSeparator(dm.getSeparator());
                    file.setHeader(dm.getHeader());
                    file.setHeaderStrings(dm.getHeaderStrings());
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
        list.add(new DatasetModel(file.getDatasetName(), file.getFileName(),
                file.getSeparator(), file.getHeader(), file.getHeaderStrings()));
        datasets.add(file.getDatasetName());
        ObservableList<DatasetModel> olDatasets
                = FXCollections.observableList(list);
        listView.setItems(null);
        listView.setItems(olDatasets);        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(FractureAnalysis.class, args);
    }
}
