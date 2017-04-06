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

import fractureanalysis.Matrices.Matrix;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.distance.EuclideanDistance;
import fractureanalysis.model.Separator;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.statistics.variogram.Models;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author elidioxg
 */
public class Tab_covarianceController implements Initializable {

    @FXML
    protected ComboBox cbX, cbY, cbContent;

    @FXML
    protected LineChart lcCovariance;

    @FXML
    protected ScatterChart scLocation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    protected void cbChange() throws Exception {
        if (cbX.getSelectionModel().getSelectedIndex() >= 0) {
            if (cbY.getSelectionModel().getSelectedIndex() >= 0) {
                if (cbContent.getSelectionModel().getSelectedIndex() >= 0) {
                    scLocation.getData().clear();
                    lcCovariance.getData().clear();
                    int indexX = cbX.getSelectionModel().getSelectedIndex();
                    int indexY = cbY.getSelectionModel().getSelectedIndex();
                    int indexContent = cbContent.getSelectionModel().getSelectedIndex();
                    int indexDataset
                            = fractureanalysis.FractureAnalysis.getInstance().listView.getSelectionModel().getSelectedIndex();
                    String filename = fractureanalysis.FractureAnalysis.getInstance().
                            getDatasetList().get(indexDataset).getFileName();
                    Separator sep = fractureanalysis.FractureAnalysis.getInstance().
                            getDatasetList().get(indexDataset).getSeparator();
                    boolean header = fractureanalysis.FractureAnalysis.getInstance().
                            getDatasetList().get(indexDataset).getHeader();
                    Vector x = OpenDataset.openCSVFileToVector(
                            filename, sep.getChar(), indexX, header);
                    Vector y = OpenDataset.openCSVFileToVector(
                            filename, sep.getChar(), indexY, header);
                    Vector content = OpenDataset.openCSVFileToVector(
                            filename, sep.getChar(), indexContent, header);
                    ScatterChart.Series serie = new XYChart.Series();

                    for (int i = 0; i < x.size(); i++) {
                        serie.getData().add(new XYChart.Data<>(x.get(i), y.get(i)));
                    }
                    scLocation.getData().add(serie);
                    Matrix distances = EuclideanDistance.getDistances(x, y);
                    distances.print();
                    double sill = 2000;
                    double range = 250.;
                    Matrix covLine = Models.spherical(sill, range, 30);
                    
                    System.out.println("CovLine1");
                    covLine.print();
                    Vector X = covLine.getColumn(0);
                    Vector Y = covLine.getColumn(1);
                    XYChart.Series serie2 =  PlotSeries.plotLineSeries(X, Y);
                    //XYChart.Series serie2 =  PlotSeries.plotLineSeries(covLine, 0, 1);
                    lcCovariance.getData().add(serie2);
                    //plot covariance function on graph
                    for (int i = 0; i < distances.getColumnsCount(); i++) {
                        for (int j = i + 1; j < distances.getColumnsCount(); j++) {
                            double h = distances.get(j, i).doubleValue();
                            //double cov = Covariance
                        }
                    }

                    for (int i = 0; i < distances.getColumnsCount(); i++) {
                        for (int j = 0; j < distances.getLinesCount(); j++) {
                            double aux = 2000. * Math.exp(-distances.get(i, j).doubleValue() / 250.);
                            distances.set(i, j, aux);
                        }
                    }
                    distances.print();

                }
            }
        }

    }

}
