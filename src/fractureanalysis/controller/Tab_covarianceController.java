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

import fractureanalysis.FractureAnalysis;
import fractureanalysis.Matrices.Matrix;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.distance.EuclideanDistance;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.model.Separator;
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    protected void cbChange() throws Exception {
        if (cbX.getSelectionModel().getSelectedIndex() >= 0) {
            if (cbY.getSelectionModel().getSelectedIndex() >= 0) {
                if (cbContent.getSelectionModel().getSelectedIndex() >= 0) {
                    DatasetModel dataset = FractureAnalysis.getInstance().getDataset();
                    if (dataset != null) {
                        scLocation.getData().clear();
                        lcCovariance.getData().clear();
                        int indexX = cbX.getSelectionModel().getSelectedIndex();
                        int indexY = cbY.getSelectionModel().getSelectedIndex();
                        int indexContent = cbContent.getSelectionModel().getSelectedIndex();

                        String filename = dataset.getFileName();
                        Separator sep = dataset.getSeparator();
                        boolean header = dataset.getHeader();

                        Vector x;
                        Vector y;
                        Vector content;
                        if (dataset.isGeoeas()) {
                            x = OpenDataset.openGeoeasToVector(filename, sep.getChar(), indexX);
                            y = OpenDataset.openGeoeasToVector(filename, sep.getChar(), indexY);
                            content = OpenDataset.openGeoeasToVector(filename, sep.getChar(), indexContent);
                        } else {
                            x = OpenDataset.openCSVFileToVector(
                                    filename, sep.getChar(), indexX, header);
                            y = OpenDataset.openCSVFileToVector(
                                    filename, sep.getChar(), indexY, header);
                            content = OpenDataset.openCSVFileToVector(
                                    filename, sep.getChar(), indexContent, header);
                        }

                        ScatterChart.Series serie = new XYChart.Series();

                        for (int i = 0; i < x.size(); i++) {
                            serie.getData().add(new XYChart.Data<>(x.get(i), y.get(i)));
                        }
                        scLocation.getData().add(serie);
                        
                        Matrix distances = EuclideanDistance.getDistances(x, y);                                                 
                                                
                        //XYChart.Series serie2 = PlotSeries.plotLineSeries(distancesVec, cov);                 
                        //lcCovariance.getData().add(serie2);
                        
                        
                    }
                }
            }
        }

    }

}
