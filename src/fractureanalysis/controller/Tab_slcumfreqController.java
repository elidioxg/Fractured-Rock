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
package fractureanalysis.controller;

import contrib.LogarithmicAxis;
import fractureanalysis.Matrices.Matrix;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.analysis.FractureIntensityAnalysis;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.stages.FractureAnalysisStage;
import fractureanalysis.table.PopulateTable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author elidioxg
 */
public class Tab_slcumfreqController implements Initializable {
    
    @FXML
    protected GridPane gpPane;

    @FXML
    protected CheckBox cbSort, cbSimplify, cbNormalize, cbLogAxis;

    @FXML
    protected LineChart lcCumFreq;

    @FXML
    protected TableView tvValues;        
    
    @FXML
    protected void cbLogAction() throws Exception{
        ValueAxis xAxis, yAxis;
        if(cbLogAxis.isSelected()){
            xAxis = new LogarithmicAxis(0.001, 10);
            yAxis = new LogarithmicAxis(0.001, 100);
            yAxis.setAutoRanging(false);
        }else {
            xAxis = new NumberAxis("X",0, 10, 1);
            yAxis = new NumberAxis("Y", 100, 0, 5);
            yAxis.setAutoRanging(false);
        }
        lcCumFreq.visibleProperty().setValue(false);
        lcCumFreq = new LineChart(xAxis, yAxis);
        lcCumFreq.legendVisibleProperty().setValue(false);
        gpPane.add(lcCumFreq, 0, 2,4,4);
        sort();
    }

    @FXML
    protected void sort() throws IOException, Exception {
        if (cbSort.isSelected()) {
            cbSimplify.setSelected(false);
            cbNormalize.setSelected(false);
            Matrix matrix = FractureIntensityAnalysis.getSortedTable();
            //populat table
            String[] header = {"b", "cumulative number"};
            PopulateTable.populateTable(tvValues, matrix, header);
            //plot
            XYChart.Series serie = PlotSeries.plotLineSeries(matrix, 0, 1);           
            lcCumFreq.getData().clear();
            lcCumFreq.getYAxis().setAutoRanging(true);
            lcCumFreq.getData().add(serie);
            serie.getChart().getXAxis().setLabel("b");
            serie.getChart().getYAxis().setLabel("Cumulative Number");
        } else {
            cbSimplify.setSelected(false);
            cbNormalize.setSelected(false);
            Matrix matrix = FractureIntensityAnalysis.getApertureTable();
            String[] header = {"b"};
            PopulateTable.populateTable(tvValues, matrix, header);
            //plot chart
            //TODO: procedimento para frequencia acumulada deve ser feito
            //na classe FractureIntensityAnalysis ou em outra 
            ArrayList<Double> array = FractureAnalysisStage.getAnalysisFile().getScanLine().getApList();
            double sum = 0.;
            for (int i = 0; i < array.size(); i++) {
                sum += array.get(i);
            }
            double cum = 0;
            Vector x = new Vector(matrix.getLinesCount());
            Vector y = new Vector(matrix.getLinesCount());
            for (int i = 0; i < matrix.getLinesCount(); i++) {
                cum += matrix.get(0, i).doubleValue();
                x.set(i, i);
                y.set(i, cum / sum * 100);
            }
            XYChart.Series serie = PlotSeries.plotLineSeries(x, y);            
            lcCumFreq.getData().addAll(serie);            
        }
    }

    @FXML
    protected void simplify() throws IOException, Exception {
        if (cbSimplify.isSelected()) {
            cbSort.setSelected(true);
            cbNormalize.setSelected(false);
            Matrix matrix = FractureIntensityAnalysis.getSimplifiedMatrix();
            //populate table
            String[] header = {"b", "cumulative number"};
            PopulateTable.populateTable(tvValues, matrix, header);
            //plot
            XYChart.Series serie = PlotSeries.plotLineSeries(matrix, 0, 1);
            lcCumFreq.getData().clear();
            lcCumFreq.getYAxis().setAutoRanging(true);
            lcCumFreq.getData().add(serie);
            serie.getChart().getXAxis().setLabel("b");
            serie.getChart().getYAxis().setLabel("Cumulative Number");                   
        } else {
            cbNormalize.setSelected(false);
            sort();
        }
    }

    @FXML
    protected void normalize() throws Exception {
        if (cbNormalize.isSelected()) {
            cbSort.setSelected(true);
            cbSimplify.setSelected(true);
            Matrix matrix = FractureIntensityAnalysis.getNormalizedTable();
            //populat table
            String[] headers = {"b", "cumulative number", "normalized value"};
            PopulateTable.populateTable(tvValues, matrix, headers);
            //plot
            XYChart.Series serie = PlotSeries.plotLineSeries(matrix, 0, 2);
            lcCumFreq.getData().clear();
            lcCumFreq.getYAxis().setAutoRanging(true);
            lcCumFreq.getData().add(serie);
            serie.getChart().getXAxis().setLabel("b");
            serie.getChart().getYAxis().setLabel("Normalized Value");            
        } else {
            simplify();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
