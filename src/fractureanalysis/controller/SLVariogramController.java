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
import fractureanalysis.analysis.FractureIntensity;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.AnalysisFile;
import fractureanalysis.plot.PlotFractureVariogram;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 *
 * @author elidioxg
 */
public class SLVariogramController implements Initializable {

    @FXML
    protected ComboBox cbSpVar, cbApVar;

    @FXML
    protected ListView lvDistances;

    @FXML
    protected ScatterChart chart;
    
    @FXML
    protected Label lFracInt, lAvgSpacing, lScanLen;

    @FXML
    protected void onClick() throws Exception {
        int indexSp = cbSpVar.getSelectionModel().getSelectedIndex();
        int indexAp = cbApVar.getSelectionModel().getSelectedIndex();  
        String filename = FractureAnalysis.getInstance().file.getFileName();
        String sep = FractureAnalysis.getInstance().file.getSeparator();        
        ArrayList<Double> ap = OpenDataset.openCSVFileToDouble(filename, sep, indexAp, true);
        ArrayList<Double> sp = OpenDataset.openCSVFileToDouble(filename, sep, indexSp, true);
        FractureAnalysis.getInstance().file.setArraysSpAp(ap, sp);
        Scene scene = (Scene) cbApVar.getScene();
        chart = (ScatterChart) scene.lookup("#variogram_chart");
        chart.getData().clear();
        lvDistances = (ListView) scene.lookup("#lvDistances");
        ObservableList<Double> ol;
        //add distances to listview, if is empty
        if (lvDistances.getItems().isEmpty()) {
//            ol = FXCollections.observableArrayList(array);
//            lvDistances.getItems().addAll(ol);
        } else {
            ol = FXCollections.observableArrayList(lvDistances.getItems());
            chart.getData().addAll(
                    PlotFractureVariogram.variogram1D(
                            FractureAnalysis.getInstance().file, ol));            
        }
        FractureIntensity fi = new FractureIntensity(
                FractureAnalysis.getInstance().file);
        lFracInt = (Label) scene.lookup("#lFracInt");
        lAvgSpacing = (Label) scene.lookup("#lAvgSpacing");
        lScanLen = (Label) scene.lookup("#lScanLen");
        lFracInt.setText(String.valueOf(fi.getFractureIntensity()));
        lAvgSpacing.setText(String.valueOf(fi.getAverageSpacing()));
        lScanLen.setText(String.valueOf(FractureAnalysis.getInstance().file.getSLLenght()));
    }

    @FXML
    protected Button bAddDist, bRmDist;

    @FXML
    protected TextArea taDist;

    @FXML
    protected void bAddClick() {
        String strDist = taDist.getText();
        lvDistances.getItems().addAll(FXCollections.singletonObservableList(strDist));
    }

    @FXML
    protected void bRmClick() {
        int rmIndex = lvDistances.getSelectionModel().getSelectedIndex();
        lvDistances.getItems().remove(rmIndex);
    }

    @FXML
    protected void clear() {

    }
    
    @FXML
    protected void auto(){
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
