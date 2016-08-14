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
import fractureanalysis.analysis.Variograms;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.AnalysisFile;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 *
 * @author elidioxg
 */
public class AnalysisController implements Initializable {

    @FXML
    protected ComboBox cbSpVar, cbApVar;

    @FXML
    protected ListView lvDistances;

    @FXML
    protected ScatterChart chart;
    
    @FXML
    protected void onClick() throws Exception {        
        int indexSp = cbSpVar.getSelectionModel().getSelectedIndex();
        int indexAp = cbApVar.getSelectionModel().getSelectedIndex();
        Scene scene = (Scene) cbApVar.getScene();
        chart = (ScatterChart) scene.lookup("#variogram_chart");
        chart.getData().clear();
        String filename = FractureAnalysis.getInstance().file.getFileName();
        String sep = FractureAnalysis.getInstance().file.getSeparator();
        if (indexSp >= 0 & indexAp >= 0) {
            AnalysisFile model = new AnalysisFile();
            ArrayList<Double> ap = OpenDataset.openCSVFileToDouble(
                    filename, sep, indexAp, true);
            ArrayList<Double> sp = OpenDataset.openCSVFileToDouble(
                    filename, sep, indexSp, true);
            model.setArrayAp(ap);
            model.setArraySp(sp);
            ArrayList<Double> dist = model.getArrayDistances();            
            
            lvDistances = (ListView) scene.lookup("#lvDistances");
            ObservableList<Double> ol;
            //add distances to listview, if is empty
            if (lvDistances.getItems().isEmpty()) {
                double minValue = (double) MinimumValue.getMinValue(dist);
                double maxValue = (double) MaximumValue.getMaxValue(dist);
                double step = (double) (maxValue - minValue) / 15;
                List array = new ArrayList();
                for (int i = 2; i <= 7; i++) {
                    array.add(minValue + (step*i));
                }
                ol = FXCollections.observableArrayList(array);
                lvDistances.getItems().addAll(ol);                
            } else {
                ol = FXCollections.observableArrayList(lvDistances.getItems());
            }
            ArrayList<Double> distance = new ArrayList();
            ArrayList<Double> variogramValue = new ArrayList();
            for(int i=0; i< ol.size(); i++){                
                Double value = Variograms.variogram1D(model, ol.get(i).floatValue());
                distance.add(ol.get(i));
                variogramValue.add(value);                
            }
            chart.getData().addAll(PlotSeries.plotLineSeries(distance, variogramValue));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
