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
import fractureanalysis.model.DatasetModel;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.plot.PlotVariogramSeries;
import fractureanalysis.stages.VariogramStage;
import fractureanalysis.statistics.variogram.Models;
import fractureanalysis.statistics.variogram.Variogram;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author elidioxg
 */
public class Stage_variogramController implements Initializable {

    @FXML
    protected ComboBox cbDatasets, cbContent, cbX, cbY;

    @FXML
    protected TextField tfNugget, tfStep, tfDistTol, tfAngle, tfAngleTol,
            tfSerieName;

    @FXML
    protected CheckBox cbReflect;

    @FXML
    protected LineChart lcVariogram;
    
    @FXML
    protected ScatterChart scLoc;

    @FXML
    protected void clear() {
        lcVariogram.getData().clear();
    }

    @FXML
    protected void cbXYChange() throws Exception {
        int indexX = cbX.getSelectionModel().getSelectedIndex();
        int indexY = cbY.getSelectionModel().getSelectedIndex();
        if (indexX >= 0 && indexY >= 0) {
            DatasetModel dataset = (DatasetModel) cbDatasets.getSelectionModel().getSelectedItem();
            scLoc.getData().clear();
            String filename = dataset.getFileName();            
            String sep = dataset.getSeparator().getChar();
            Vector x,y;
            if (dataset.isGeoeas()) {
                x = OpenDataset.openGeoeasToVector(filename, 
                        sep, 
                        indexX);
                y = OpenDataset.openGeoeasToVector(filename, 
                        sep, 
                        indexY);                
            } else {
                x = OpenDataset.openCSVFileToVector(
                        filename, 
                        sep, 
                        indexX, 
                        dataset.getHeader());
                y = OpenDataset.openCSVFileToVector(
                        filename, sep, indexY, 
                        dataset.getHeader());                
            }

            ScatterChart.Series serie = new XYChart.Series();

            for (int i = 0; i < x.size(); i++) {
                serie.getData().add(new XYChart.Data<>(x.get(i), y.get(i)));
            }
            scLoc.getData().add(serie);
        }
    }

    /**
     * Plot the a variogram serie on Variogram Stage using the defined
     * properties by the user. The properties the user can define are: which
     * columns represent the vectors X and Y, minimum and maximum angle of
     * tolerance, the step size, initial distance and maximum distance.
     *
     * @throws Exception
     */
    @FXML
    protected void plot() throws Exception {
        int index = cbDatasets.getSelectionModel().getSelectedIndex();
        DatasetModel dm = (DatasetModel) VariogramStage.getInstance().getDatasets().get(index);
        //Matrix matrix = OpenDataset.openCSVFileToMatrix(dm.getFileName(), 
        //      dm.getSeparator(), dm.getHeader());
        int xIndex = cbX.getSelectionModel().getSelectedIndex();
        int yIndex = cbY.getSelectionModel().getSelectedIndex();
        int contentIndex = cbContent.getSelectionModel().getSelectedIndex();
        if (xIndex >= 0 & yIndex >= 0 & contentIndex >= 0) {
            Vector x, y, content;
            if (dm.isGeoeas()) {
                x = OpenDataset.openGeoeasToVector(dm.getFileName(),
                        dm.getSeparator().getChar(), xIndex);
                y = OpenDataset.openGeoeasToVector(dm.getFileName(),
                        dm.getSeparator().getChar(), yIndex);
                content = OpenDataset.openGeoeasToVector(dm.getFileName(),
                        dm.getSeparator().getChar(), contentIndex);
            } else {
                x = OpenDataset.openCSVFileToVector(dm.getFileName(),
                        dm.getSeparator().getChar(), xIndex, dm.getHeader());
                y = OpenDataset.openCSVFileToVector(dm.getFileName(),
                        dm.getSeparator().getChar(), yIndex, dm.getHeader());
                content = OpenDataset.openCSVFileToVector(dm.getFileName(),
                        dm.getSeparator().getChar(), contentIndex, dm.getHeader());
            }
            double nuggetValue = 0.;
            if (!tfNugget.getText().trim().isEmpty()) {
                nuggetValue = Double.valueOf(tfNugget.getText().trim());
            }
            double stepSize = Double.valueOf(tfStep.getText().trim());
            double distTol = Double.valueOf(tfDistTol.getText().trim());
            double angle = 0.;
            if (!tfAngle.getText().trim().isEmpty()) {
                angle = Double.valueOf(tfAngle.getText().trim());
            }
            double angleTol = 360.;
            if (!tfAngleTol.getText().trim().isEmpty()) {
                angleTol = Double.valueOf(tfAngleTol.getText().trim());
            }
            // boolean ref = cbReflect.isSelected();

            Matrix variogram = Variogram.variogram2D(x, y, content, nuggetValue, stepSize,
                    distTol, angle, angleTol);
            variogram.print();
            ArrayList<Double> alX = new ArrayList<>();
            ArrayList<Double> alY = new ArrayList<>();
            for (int i = 0; i < variogram.getLinesCount(); i++) {
                alX.add(variogram.get(0, i).doubleValue());
                alY.add(variogram.get(1, i).doubleValue());
            }
            lcVariogram.getData().addAll(PlotSeries.plotLineSeries(alX, alY));
        }
    }

    @FXML
    protected ComboBox cbModel;

    @FXML
    protected TextField tfFitSill, tfFitRange;

    @FXML
    protected void plotModel() throws Exception {
        if (cbModel.getSelectionModel().getSelectedIndex() >= 0) {
            if (!tfFitSill.getText().isEmpty()) {
                if (!tfFitRange.getText().isEmpty()) {

                    double vSill = Double.valueOf(tfFitSill.getText());
                    double vRange = Double.valueOf(tfFitRange.getText());
                    Matrix matrix = null;
                    String serieName = "";
                    switch (cbModel.getSelectionModel().getSelectedIndex()) {
                        case 0:
                            matrix = Models.spherical(vSill, vRange);
                            serieName = "Spherical";
                            break;
                        case 1:
                            matrix = Models.exponential(vSill, vRange);
                            serieName = "Exponential";
                            break;
                        case 2:
                            matrix = Models.gaussian(vSill, vRange);
                            serieName = "Gaussian";
                            break;
                        default:
                            break;
                    }
                    lcVariogram = (LineChart) cbModel.getScene().lookup("#lcVariogram");
                    lcVariogram.getData().add(
                            PlotVariogramSeries.plotModel(matrix, 0, 1, serieName));

                }
            }
        }

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
