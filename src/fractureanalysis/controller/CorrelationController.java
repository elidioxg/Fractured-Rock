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
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.statistics.Pearson;
import fractureanalysis.statistics.Covariance;
import fractureanalysis.statistics.Kendall;
import fractureanalysis.statistics.Spearman;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 *
 * @author elidioxg
 */
public class CorrelationController implements Initializable {

    /**
     * Handle actions for Tab Correlation on Main Stage
     *
     * See views/tab_correlation.fxml
     */
    @FXML
    protected ComboBox cbVarA, cbVarB;

    @FXML
    protected Label lCovariance, lPearson, lSpearman, lKendall;

    /**
     * When the user change some of the combobox on tab correlation(main stage),
     * this procedure is executed.
     *
     * @throws Exception
     */
    @FXML
    protected void comboboxChange() throws Exception {
        int indexA = cbVarA.getSelectionModel().getSelectedIndex();
        int indexB = cbVarB.getSelectionModel().getSelectedIndex();
        DatasetModel dataset = FractureAnalysis.getInstance().getDataset();
        if (dataset != null) {
            String filename = dataset.getFileName();
            String sep = dataset.getSeparator().getChar();
            boolean header = dataset.getHeader();
            if (indexA >= 0 & indexB >= 0) {
                Vector arrayA;
                Vector arrayB;
                if (dataset.isGeoeas()) {
                    arrayA = OpenDataset.openGeoeasToVector(filename, sep, indexA);
                    arrayB = OpenDataset.openGeoeasToVector(filename, sep, indexB);
                } else {
                    arrayA = OpenDataset.openCSVFileToVector(
                            filename, sep, indexA, header);
                    arrayB = OpenDataset.openCSVFileToVector(
                            filename, sep, indexB, header);
                }

                double covariance = Covariance.covariance(arrayA, arrayB);
                lCovariance.setText(String.valueOf(covariance));
                double pearson = Pearson.pearsonCoeff(arrayA, arrayB);
                lPearson.setText(String.valueOf(pearson));
                double spearman = Spearman.calc(arrayA, arrayB);
                lSpearman.setText(String.valueOf(spearman));
                double kendall = Kendall.calc(arrayA, arrayB);
                lKendall.setText(String.valueOf(kendall));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
