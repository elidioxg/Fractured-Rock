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

import fractureanalysis.Matrices.LUP;
import fractureanalysis.Matrices.Matrix;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.math.EuclideanDistance;
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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    protected void cbChange() throws Exception {
        if (cbX.getSelectionModel().getSelectedIndex() >= 0) {
            if (cbY.getSelectionModel().getSelectedIndex() >= 0) {
                if (cbContent.getSelectionModel().getSelectedIndex() >= 0) {
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
                    for (int i = 0; i < distances.getColumnsCount(); i++) {
                        for (int j = 0; j < distances.getLinesCount(); j++) {
                            double aux = 2000. * Math.exp(-distances.get(i, j).doubleValue() / 250.);
                            distances.set(i, j, aux);
                        }
                    }

                    /*Matrix m = new Matrix(5);
                    m.set(0, 0, 1.81);
                    m.set(0, 1, -1.513);
                    m.set(0, 2, -.5588);
                    m.set(0, 3, 1.97);
                    m.set(0, 4, -1.088);
                    m.set(1, 0, -1.9178);
                    m.set(1, 1, -1.0880);
                    m.set(1, 2, 1.3005);
                    m.set(1, 3, 1.82589);
                    m.set(1, 4, -.2647);
                    m.set(2, 0, -1.088);
                    m.set(2, 1, 1.82589);
                    m.set(2, 2, -1.97606);
                    m.set(2, 3, 1.4902);
                    m.set(2, 4, -.5247);
                    m.set(3, 0, -1.92279);
                    m.set(3, 1, 1.058165);
                    m.set(3, 2, 1.340458);
                    m.set(3, 3, -1.795855);
                    m.set(3, 4, -.352151);
                    m.set(4, 0, 1.52511);
                    m.set(4, 1, 1.973);
                    m.set(4, 2, 1.02795);
                    m.set(4, 3, -6.43244);        
                    m.set(4, 4, -1.86021);        
                    m.print();*/
                    int size = 5;
                    Matrix m = new Matrix(size);
                    for (int i = 1; i <= size; i++) {
                        for (int j = 1; j <= size; j++) {
                            double value = Math.sin(i * j * j + i) * 2;
                            m.set(j-1, i-1, value);
                        }
                    }
                    m.print();
                    //LUP lup = new LUP(m);
                    Matrix dec = LUP.lupDecompose(m);
                    System.out.println("Decompose:");
                    dec.print();
                    //Matrix inv = lup.lupInverse();
                    //System.out.println("Inverse matrix:");
                    //inv.print();

                }
            }
        }

    }

}
