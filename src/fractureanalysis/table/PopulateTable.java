/*
 * Copyright (C) 2017 elidioxg
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
package fractureanalysis.table;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.Matrices.Matrix;
import fractureanalysis.controller.AppController;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.model.Separator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 *
 * @author elidioxg
 */
public class PopulateTable {
    
    public static void populateTable(
            TableView table, 
            Matrix matrix, 
            String[] header) throws FileNotFoundException, IOException {
        
        table.getItems().clear();
        table.getColumns().clear();
        table.setPlaceholder(new Label("Loading..."));
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        TableUtils tu = new TableUtils();
                        for (int i = 0; i < header.length; i++) {
                            table.getColumns().add(
                                    tu.createColumn(i, header[i]));
                        }
                    }
                });

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < matrix.getLinesCount(); i++) {
                            String[] str = new String[matrix.getColumnsCount() - 1];
                            for (int j = 0; j < matrix.getColumnsCount(); j++) {
                                try {
                                    str[j] = String.valueOf(matrix.get(j, i).doubleValue());
                                } catch (Exception ex) {
                                    Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ObservableList data = FXCollections.observableArrayList();
                                for (String value : str) {
                                    data.add(new SimpleStringProperty(value));
                                }
                                table.getItems().add(data);
                            }
                        }
                    }
                });
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public static void populateTable(TableView table, DatasetModel dm) throws FileNotFoundException, IOException {
        table.getItems().clear();
        table.getColumns().clear();
        table.setPlaceholder(new Label("Loading..."));
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                BufferedReader br = null;
                br = new BufferedReader(new FileReader(dm.getFileName()));
                String dataLine = null;
                if (dm.getFileName().trim() != null) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (dm.getHeader()) {
                                TableUtils tu = new TableUtils();
                                for (int i = 0; i < dm.getHeaderArray().size(); i++) {
                                    table.getColumns().add(
                                            tu.createColumn(i, dm.getHeaderArray(i)));
                                }
                            } else {
                                TableUtils tu = new TableUtils();
                                for (int i = 0; i < dm.getHeaderArray().size(); i++) {
                                    table.getColumns().add(
                                            tu.createColumn(i, "Column " + String.valueOf(i + 1)));
                                }
                            }
                        }
                    });

                    if (dm.isGeoeas()) {
                        int jumpLines = dm.getColumnsCount() + 2;
                        for (int i = 0; i < jumpLines; i++) {
                            br.readLine();
                        }
                    } else {
                        if (dm.getHeader()) {
                            br.readLine();
                        }
                    }

                    while ((dataLine = br.readLine()) != null) {
                        if (dm.getSepString().trim().equals("")) {
                            while (dataLine.subSequence(0, 1).equals(" ")) {
                                dataLine = dataLine.substring(1);
                            }
                            dataLine = dataLine.replace(dm.getSepString() + dm.getSepString() + dm.getSepString() + dm.getSepString(), dm.getSepString());
                            dataLine = dataLine.replace(dm.getSepString() + dm.getSepString() + dm.getSepString(), dm.getSepString());
                            dataLine = dataLine.replace(dm.getSepString() + dm.getSepString(), dm.getSepString());
                        }

                        final String[] dataValues = dataLine.split(dm.getSepString());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // Add additional columns if necessary:
                                TableUtils tu = new TableUtils();
                                for (int columnIndex = table.getColumns().size();
                                        columnIndex < dataValues.length; columnIndex++) {
                                    table.getColumns().add(tu.createColumn(columnIndex, ""));
                                }
                                ObservableList<StringProperty> data = FXCollections
                                        .observableArrayList();
                                for (String value : dataValues) {
                                    data.add(new SimpleStringProperty(value));
                                }
                                table.getItems().add(data);
                            }
                        });

                    }

                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    /**
     * Populates the table on main stage. This table is used to view dataset
     * values.
     *
     * TODO: adaptar para receber formato Geoeas
     *
     * @param filename
     * @param separator
     * @param hasHeader
     */
    public void populateTable(TableView table, 
            final String filename, 
            final Separator separator,
            final boolean hasHeader) {
        table.getItems().clear();
        table.getColumns().clear();
        if (filename.trim() != null) {
            table.setPlaceholder(new Label("Loading..."));
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    BufferedReader br = new BufferedReader(new FileReader(filename));
                    final String headerLine = br.readLine();
                    final String[] headerValues = headerLine.split(separator.getChar());
                    ArrayList<String> array = new ArrayList<>(Arrays.asList(headerValues));
                    if (hasHeader) {
                        FractureAnalysis.getInstance().getDataset().setHeaderStrings(array);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                TableUtils tu = new TableUtils();
                                for (int i = 0; i < headerValues.length; i++) {
                                    table.getColumns().add(
                                            tu.createColumn(i, headerValues[i]));
                                }
                            }
                        });
                    } else {
                        Platform.runLater(() -> {
                            TableUtils tu = new TableUtils();
                            for (int i = 0; i < headerValues.length; i++) {
                                table.getColumns().add(
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
                                for (int columnIndex =table.getColumns().size();
                                        columnIndex < dataValues.length; columnIndex++) {
                                    table.getColumns().add(tu.createColumn(columnIndex, ""));
                                }
                                ObservableList<StringProperty> data = FXCollections
                                        .observableArrayList();
                                for (String value : dataValues) {
                                    data.add(new SimpleStringProperty(value));
                                }
                                table.getItems().add(data);
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
    
}
