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
import fractureanalysis.analysis.FractureIntensityAnalysis;
import fractureanalysis.analysis.ScanLine;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.statistics.LinearRegression.LinearRegression;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author elidioxg
 */
public class ScanlineController implements Initializable {

    /**
     * Views: views/tab_distribution.fxml
     */
    @FXML
    protected ComboBox cbSpVar, cbApVar;    

    @FXML
    protected LineChart scFractureIntensity, lcAux;

    @FXML
    protected Label lFracInt, lAvgSpacing, lScanLen;

    /**
     * Handle actions for aperture column index combobox 
     * @throws Exception 
     */
    @FXML
    protected void cbApAction() throws Exception {
        int indexAp = cbApVar.getSelectionModel().getSelectedIndex();
        if (indexAp >= 0) {
            int indexSp = FractureAnalysis.getInstance().file.getSpColumn();
            if (indexSp >= 0) {
                estimateFractures(indexAp, indexSp);
            }
        }
    }

    /**
     * Handle actions for spacement column index combobox 
     * @throws Exception 
     */
    @FXML
    protected void cbSpAction() throws Exception {
        int indexSp = cbSpVar.getSelectionModel().getSelectedIndex();
        if (indexSp >= 0) {
            int indexAp = FractureAnalysis.getInstance().file.getApColumn();
            if (indexAp >= 0) {
                estimateFractures(indexAp, indexSp);
            }
        }
    }

    /**
     * Estimate the variogram of fractures.
     * 
     * If there is already defined values of distance on the ListView, 
     * this procedure will use them. If the ListView is empty, this
     * procedure will create a list of distances to plot according 
     * to minimum and maximum values on dataset.
     * 
     * The last step is plot a linear regression line to the chart.
     * 
     * @param indexAp
     * @param indexSp
     * @throws Exception 
     */
    private void estimateFractures(int indexAp, int indexSp) throws Exception {

        String filename = FractureAnalysis.getInstance().file.getFileName();
        String sep = FractureAnalysis.getInstance().file.getSeparator().getChar();
        boolean header = FractureAnalysis.getInstance().file.getHeader();
        Vector vectorAp = OpenDataset.openCSVFileToVector(filename, sep, indexAp, header);
        Vector vectorSp = OpenDataset.openCSVFileToVector(filename, sep, indexSp, header);
        ArrayList<Fracture> fracturesList = new ArrayList();
        if (vectorAp.size() == vectorSp.size()) {
            for (int i = 0; i < vectorAp.size(); i++) {
                Fracture f = new Fracture(vectorAp.get(i).doubleValue(),
                        vectorSp.get(i).doubleValue());
                fracturesList.add(f);
            }
        } else {
            throw new Exception("Vector Ap must have same size of Vector Sp");
        }
        ScanLine scanline = new ScanLine(fracturesList);
        FractureAnalysis.getInstance().file.setScanLine(scanline);
        Scene scene = (Scene) cbApVar.getScene();                
        
        FractureIntensityAnalysis fi = new FractureIntensityAnalysis(
                scanline);
        lFracInt = (Label) scene.lookup("#lFracInt");
        lAvgSpacing = (Label) scene.lookup("#lAvgSpacing");
        lScanLen = (Label) scene.lookup("#lScanLen");
        lFracInt.setText(String.valueOf(fi.getFractureIntensity()));
        lAvgSpacing.setText(String.valueOf(fi.getAverageSpacing()));
        lScanLen.setText(String.valueOf(scanline.getLenght()));
        //distribution tab
        scFractureIntensity = (LineChart) scene.lookup("#scFractureIntensity");
        lcAux = (LineChart) scene.lookup("#lcAux");
        ArrayList<Fracture> al = fi.getArrayDistribution();
        ArrayList<Double> cumulative = new ArrayList<>();
        ArrayList<Double> aperture = new ArrayList<>();
        for (Fracture values : al) {
            cumulative.add(Math.log10(Double.valueOf(values.getCumulativeNumber())));
            aperture.add(Math.log10(values.getAperture()));
//            cumulative.add(Double.valueOf(values.getCumulativeNumber()));
//            aperture.add(values.getAperture());
        }
        scFractureIntensity.getData().addAll(PlotSeries.plotLineSeries(aperture, cumulative));        
        /**
         * Add linear regression
         */ 
        LinearRegression lr = new LinearRegression(aperture, cumulative);
        double min = MinimumValue.getMinValue(aperture);
        double max = MaximumValue.getMaxValue(aperture);
        double first = lr.getValueAt(min);
        double last = lr.getValueAt(max);
        //double min = 0.001;
        //double max = 10;
        //double first = lr.getValueAt(0.001);
       // double last = lr.getValueAt(10);
        XYChart.Series serieRegression = new XYChart.Series();
        serieRegression.getData().add(new XYChart.Data<>(min, first));
        serieRegression.getData().add(new XYChart.Data<>(max, last));
        scFractureIntensity.getData().add(serieRegression);
        lcAux.getData().add(serieRegression);
    }

    @FXML
    protected TextField tfApValueAt;

    @FXML
    protected Label lValue;

    @FXML
    protected void estimate() {
        /*if (lr != null) {
            if ((lr.getInitialValue() != 0) & (lr.getInclination() != 0)) {
                double value = Double.parseDouble(tfApValueAt.getText());
                lr.getValueAt(value);
                lValue.setText(String.valueOf(value));
            }
        }*/
    }    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
