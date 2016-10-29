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
public class CorrelationController implements Initializable{
    
    /**
     * Handle actions for Tab Correlation on Main Stage
     * 
     * See views/tab_correlation.fxml
     */
    
    @FXML 
    protected ComboBox cbVarA,cbVarB;
    
    @FXML 
    protected Label lCovariance, lPearson, lSpearman, lKendall;
           
    /**
     * When the user change some of the combobox on tab correlation(main stage),
     * this procedure is executed.
     * 
     * @throws Exception 
     */
    @FXML
    protected void comboboxChange() throws Exception{
        int indexA = cbVarA.getSelectionModel().getSelectedIndex();
        int indexB = cbVarB.getSelectionModel().getSelectedIndex();
        String filename = FractureAnalysis.getInstance().file.getFileName();
        String sep = FractureAnalysis.getInstance().file.getSeparator().getChar();
        if(indexA>=0 & indexB>=0){
            Vector arrayA = OpenDataset.openCSVFileToVector(
                    filename, sep, indexA, true);
            Vector arrayB = OpenDataset.openCSVFileToVector(
                    filename, sep, indexB, true);
            double covariance = Covariance.covariance(arrayA, arrayB);
            lCovariance.setText(String.valueOf(covariance));
            double pearson = Pearson.pearsonCoeff(arrayA, arrayB);
            lPearson.setText(String.valueOf(pearson));
            //TODO: if dataset has header or not
            ArrayList<Double> a = OpenDataset.openCSVFileToDouble(filename, sep, indexA, true);
            ArrayList<Double> b = OpenDataset.openCSVFileToDouble(filename, sep, indexB, true);
            double spearman = Spearman.calc(a, b);
            lSpearman.setText(String.valueOf(spearman));
            double kendall = Kendall.calc(a, b);
            lKendall.setText(String.valueOf(kendall));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
