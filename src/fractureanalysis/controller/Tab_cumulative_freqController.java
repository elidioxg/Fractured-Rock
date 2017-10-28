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

import contrib.LogarithmicAxis;
import fractureanalysis.FractureAnalysis;
import fractureanalysis.Vectors.Vector;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author elidioxg
 */
public class Tab_cumulative_freqController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    protected GridPane gpCumFreq;

    @FXML
    protected CheckBox cbLogAxis;

    @FXML
    protected ComboBox cbColumn;

    @FXML
    protected LineChart lcPoints;

    @FXML
    protected CheckBox cbSorted;   

    @FXML
    protected void cbAction() throws Exception {
        lcPoints.getData().clear();
        int selected = cbColumn.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            DatasetModel dataset = FractureAnalysis.getInstance().getDataset();
            if (dataset != null) {
                String filename = dataset.getFileName();
                String sep = dataset.getSeparator().getChar();
                boolean header = dataset.getHeader();
                boolean geoeas = dataset.isGeoeas();
                Vector vector;
                if (geoeas) {
                    vector = OpenDataset.openGeoeasToVector(filename, sep, selected);
                } else {
                    vector = OpenDataset.openCSVFileToVector(filename, sep, selected, header);
                }
                if (cbSorted.isSelected()) {
                    vector.sort();
                }
                double sum = vector.sum();
                double cumulative = 0.;
                Vector x = new Vector(vector.size());
                Vector y = new Vector(vector.size());
                for (int i = 0; i < vector.size(); i++) {
                    cumulative += vector.get(i).doubleValue();
                    x.set(i, i);
                    y.set(i, cumulative / sum * 100);
                }
                ValueAxis xAxis, yAxis;
                double min = 1;
                double max = MaximumValue.getMaxValue(x);
                if (cbLogAxis.isSelected()) {
                    xAxis = new LogarithmicAxis(min, max);
                    yAxis = new LogarithmicAxis(0.001, 100);
                    yAxis.setAutoRanging(false);
                } else {
                    xAxis = new NumberAxis("X", min,
                            max, 1);
                    yAxis = new NumberAxis("Y", 0, 100, 5);
                    yAxis.setAutoRanging(false);
                }
                lcPoints.visibleProperty().setValue(false);
                lcPoints = new LineChart(xAxis, yAxis);
                lcPoints.setAnimated(false);
                lcPoints.legendVisibleProperty().setValue(false);
                gpCumFreq.add(lcPoints, 0, 2, 4, 4);
                lcPoints.getData().addAll(PlotSeries.plotLineSeries(x, y));
            }
        }
    }

}
