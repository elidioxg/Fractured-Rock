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
import fractureanalysis.data.DatasetProperties;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.stages.HistogramStage;
import fractureanalysis.statistics.histogram.ClassInterval;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import fractureanalysis.statistics.histogram.Frequency;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author elidioxg
 */
public class Stage_histogramController implements Initializable {

    @FXML
    protected ComboBox cbDatasets, cbColumnIndex;

    @FXML
    protected TextField tfIntervals, tfMaxValue, tfMinValue;

    @FXML
    protected BarChart bcHistogram;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Handle action for Dataset Combobox. When the Dataset Combobox is changed,
     * the Column Index Combobox must be updated, and that is what this
     * procedure does.
     *
     * @throws Exception
     */
    @FXML
    protected void cbDatasetAction() throws Exception {
        if (!cbDatasets.getEditor().getText().isEmpty()) {
            int index = cbDatasets.getSelectionModel().getSelectedIndex();
            cbColumnIndex.setItems(
                    FXCollections.observableArrayList(
                            DatasetProperties.getHeaders(
                                    FractureAnalysis.getInstance().getDatasetList().
                                            get(index).getFileName(),
                                    FractureAnalysis.getInstance().getDatasetList().
                                            get(index).getSeparator()
                            )));
        } else {
            throw new Exception("Combobox empty");
        }
    }

    /**
     * Handle action for Column Index ComboBox. When this combobox is changed
     * the TextView with cutoff values must be updated. The cutoff values limit
     * the maximum and minimum values used for the histogram ploting.
     *
     * @throws Exception
     */
    @FXML
    protected void cbColumnAction() throws Exception {
        if (cbColumnIndex.getSelectionModel().getSelectedIndex() >= 0) {
            int datasetIndex = cbDatasets.getSelectionModel().getSelectedIndex();
            int columnIndex = cbColumnIndex.getSelectionModel().getSelectedIndex();
            DatasetModel dm;
            dm = HistogramStage.getInstance().getDatasets().get(datasetIndex);
            Vector vector;
            if (dm.isGeoeas()) {
                vector = OpenDataset.openGeoeasToVector(dm.getFileName(),
                        dm.getSeparator().getChar(), columnIndex);
            } else {
                vector = OpenDataset.openCSVFileToVector(
                        dm.getFileName(),
                        dm.getSeparator().getChar(), columnIndex, dm.getHeader());
            }
            double min = MinimumValue.getMinValue(vector);
            double max = MaximumValue.getMaxValue(vector);
            tfMinValue.setText(String.valueOf(min));
            tfMaxValue.setText(String.valueOf(max));
        } else {
            throw new Exception("Combobox selected index < 0");
        }
    }

    /**
     * Handle action for Plot Button on Histogram Stage
     *
     * @throws Exception
     */
    @FXML
    protected void plot() throws Exception {
        if (cbColumnIndex.getSelectionModel().getSelectedIndex() >= 0) {

            if (!tfIntervals.getText().isEmpty()) {
                int datasetIndex = cbDatasets.getSelectionModel().getSelectedIndex();
                int columnIndex = cbColumnIndex.getSelectionModel().getSelectedIndex();
                DatasetModel dm;
                dm = HistogramStage.getInstance().getDatasets().get(datasetIndex);
                Vector vector;
                if (dm.isGeoeas()) {
                    vector = OpenDataset.openGeoeasToVector(dm.getFileName(),
                            dm.getSeparator().getChar(), columnIndex);

                } else {
                    vector = OpenDataset.openCSVFileToVector(
                            dm.getFileName(),
                            dm.getSeparator().getChar(), columnIndex, dm.getHeader());
                }
                double min = Double.valueOf(tfMinValue.getText());
                double max = Double.valueOf(tfMaxValue.getText());
                int intervals = Integer.valueOf(tfIntervals.getText());
                ArrayList<ClassInterval> list = Frequency.classIntervals(min, max, intervals);
                Frequency.countObsFrequency(vector, list);
                XYChart.Series series = new XYChart.Series();
                series.setName(cbColumnIndex.getEditor().getText());
                for (int i = 0; i < list.size(); i++) {
                    series.getData().add(
                            new XYChart.Data(list.get(i).getLabel(),
                                    list.get(i).getObsFrequency()));
                }
                bcHistogram.getData().clear();
                bcHistogram.setTitle(cbColumnIndex.getEditor().getText());
                bcHistogram.getData().addAll(series);

            } else {
                throw new Exception("TextField empty");
            }
        } else {
            throw new Exception("Combobox selected index < 0");
        }
    }

    /**
     * Clear all data from the chart on Histogram Stage.
     */
    @FXML
    protected void clear() {

    }

}
