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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author elidioxg
 */
public class Stage_variogramController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    protected ComboBox cbDatasets, cbContent, cbX, cbY;

    @FXML
    protected TextField tfSill, tfStep, tfDistTol, tfAngle, tfAngleTol,
            tfSerieName;

    @FXML
    protected CheckBox cbReflect;

    @FXML
    protected LineChart lcVariogram;

    @FXML
    protected void clear() {
        lcVariogram.getData().clear();
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
            double sillValue = 0.;
            if (!tfSill.getText().trim().isEmpty()) {
                sillValue = Double.valueOf(tfSill.getText().trim());
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

            Matrix variogram = Variogram.variogram2D(x, y, content, sillValue, stepSize,
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
    protected TextField tfFitSill, tfFitRange, tfFitStep;

    @FXML
    protected void plotModel() throws Exception {
        if (cbModel.getSelectionModel().getSelectedIndex() >= 0) {
            if (!tfFitSill.getText().isEmpty()) {
                if (!tfFitRange.getText().isEmpty()) {
                    if (!tfFitStep.getText().isEmpty()) {
                        double vSill = Double.valueOf(tfFitSill.getText());
                        double vRange = Double.valueOf(tfFitRange.getText());
                        double vStep = Double.valueOf(tfFitStep.getText());
                        Matrix matrix = null;
                        String serieName = "";
                        switch (cbModel.getSelectionModel().getSelectedIndex()) {
                            case 0:
                                matrix = Models.spherical(vSill, vRange, vStep);
                                serieName = "Spherical";
                                break;
                            case 1:
                                matrix = Models.exponential(vSill, vRange, vStep);
                                serieName = "Exponential";
                                break;
                            case 2:
                                matrix = Models.gaussian(vSill, vRange, vStep);
                                serieName = "Gaussian";
                                break;
                            case 3:
                                matrix = Models.power(vSill, vStep);
                                serieName = "Power";
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

    }
}
