package fractureanalysis.table;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class TableUtils {

    public TableColumn<ObservableList<StringProperty>, String> createColumn(
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
                    TableColumn.CellDataFeatures<ObservableList<StringProperty>,
                            String> cellDataFeatures) {
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
    
    public TableColumn<ObservableList<StringProperty>, String>  createColumnNumber(
            final int columnIndex, String columnTitle) {
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
        //TableColumn<ObservableList<Number>, Number> column = new TableColumn<>();
        String title;
        if (columnTitle == null || columnTitle.trim().length() == 0) {
            switch(columnIndex){
                case 0: 
                    title = "id";
                    break;
                case 1:
                    title = "sp";
                    break;
                case 2:
                    title = "ap";
                    break;
                default:
                    title = "Column " + (columnIndex + 1);
                    break;
            }            
        } else {
            title = columnTitle;
        }
        column.setText(title);
        column.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObservableList<
                StringProperty>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<ObservableList<StringProperty>,
                            String> cellDataFeatures) {
                ObservableList<StringProperty> values = cellDataFeatures.getValue();
                if (columnIndex >= values.size()) {
                    return new SimpleStringProperty("");
                } else {
                    return cellDataFeatures.getValue().get(columnIndex);
                }
            }
        });
        /*
        column.setCellValueFactory((TableColumn.CellDataFeatures<ObservableList<Number>,
                Number> cellDataFeatures) -> {
            ObservableList<Number> values = cellDataFeatures.getValue();
            if (columnIndex >= values.size()) {
                return new 
                SimpleDoubleProperty(0.);
            } else {                               
                return new SimpleDoubleProperty(
                 (double) Double.valueOf(cellDataFeatures.getValue().
                         get(columnIndex).doubleValue()));
            }
        });
        */
        return column;
    }
    
    public void AddColumn(TableView table, ArrayList[][] array, String columnName, 
            int colIndex, int colsNumber) {        
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {                        
                        table.getColumns().add(createColumnNumber(colIndex, 
                                columnName));          
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {           
                                ArrayList<String> list = new ArrayList<>();
                                for(int i = 1; i<=array.length-1;i++){
                                    list.add(array[i][1].get(0).toString());
                                }
                                ObservableList<String> data = FXCollections
                                        .observableArrayList(list);                                
                                table.getItems().add(data);
                            }
                        });
                    }
                });
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }
    
    public void AddColumn(TableView table, ArrayList<Double> array, 
            String columnName, int colIndex) {        
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {                        
                        table.getColumns().add(createColumnNumber(colIndex, 
                                columnName));          
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {                                
                                ObservableList<Double> data = FXCollections
                                        .observableArrayList(array);                                
                                table.getItems().add(data);
                            }
                        });
                    }
                });
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

}
