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
package fractureanalysis.controller;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.analysis.Fracture;
import fractureanalysis.analysis.ScanLine;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.plot.PlotVariogramSeries;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 *
 * @author elidioxg
 */
public class Stage_variogram1DController implements Initializable {

    @FXML
    protected ListView lvDistances;

    @FXML
    protected ScatterChart chart;
    
    @FXML
    protected ComboBox cbDistances, cbValues;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void plot() throws Exception {
        /*Scene scene = chart.getScene();
        chart = (ScatterChart) scene.lookup("#variogram_chart");
        chart.getData().clear();
        lvDistances = (ListView) scene.lookup("#lvDistances");
        //add distances to listview, if is empty
        if (lvDistances.getItems().isEmpty()) {
            auto();
        }*/
        String filename = FractureAnalysis.getInstance().getAnalysisFile().getFileName();
        String sep = FractureAnalysis.getInstance().getAnalysisFile().getSeparator().getChar();
        int indexAp = cbValues.getSelectionModel().getSelectedIndex();
        int indexSp = cbDistances.getSelectionModel().getSelectedIndex();
        boolean header = FractureAnalysis.getInstance().getAnalysisFile().getHeader();
        Vector vectorValues = OpenDataset.openCSVFileToVector(filename, sep, indexAp, header);
        Vector vectorDistances = OpenDataset.openCSVFileToVector(filename, sep, indexSp, header);
        ArrayList<Fracture> fracturesList = new ArrayList();
        if (vectorValues.size() == vectorDistances.size()) {
            for (int i = 0; i < vectorValues.size(); i++) {
                Fracture f = new Fracture(vectorValues.get(i).doubleValue(),
                        vectorDistances.get(i).doubleValue());
                fracturesList.add(f);
            }
        } else {
            throw new Exception("Vector Ap must have same size of Vector Sp");
        }
        ScanLine scanline = new ScanLine(fracturesList);
        ObservableList<Double> ol = FXCollections.observableArrayList(lvDistances.getItems());
        chart.getData().addAll(PlotVariogramSeries.variogram1D(
                scanline, ol));
    }

    @FXML
    protected Button bAddDist, bRmDist;

    @FXML
    protected TextArea taDist;

    @FXML
    protected void bAddClick() {
        ArrayList<Double> al = new ArrayList();
        double dist = Double.parseDouble(taDist.getText());
        al.add(dist);
        ObservableList<Double> ol = FXCollections.observableArrayList(al);
        lvDistances.getItems().addAll(ol);
    }

    @FXML
    protected void bRmClick() {
        int rmIndex = lvDistances.getSelectionModel().getSelectedIndex();
        if (rmIndex >= 0) {
            lvDistances.getItems().remove(rmIndex);
        }
    }

    @FXML
    protected void clear() {
        lvDistances.getSelectionModel().getSelectedItems().clear();
    }

    /**
     * Create a list of distances to be plotted on the variogram chart.
     */
    @FXML
    protected void auto() {
        ArrayList<Double> array = FractureAnalysis.getInstance().getAnalysisFile().getScanLine().getDistanceList();
        double max = MaximumValue.getMaxValue(array);
        double min = MinimumValue.getMinValue(array);
        double step = (max - min) / 10;
        ArrayList<Double> listDistances = new ArrayList();
        for (int i = 1; i <= 8; i++) {
            listDistances.add(min + (step * i));
        }
        ObservableList<Double> ol = FXCollections.observableArrayList(listDistances);
        lvDistances.getItems().addAll(ol);
    }

}
