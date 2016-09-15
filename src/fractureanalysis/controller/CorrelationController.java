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
import fractureanalysis.statistics.Correlation;
import fractureanalysis.statistics.Covariance;
import java.net.URL;
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
    
    @FXML 
    protected ComboBox cbVarA,cbVarB;
    
    @FXML 
    protected Label lCovariance, lPearson;
            
    @FXML
    protected void comboboxChange() throws Exception{
        int indexA = cbVarA.getSelectionModel().getSelectedIndex();
        int indexB = cbVarB.getSelectionModel().getSelectedIndex();
        String filename = FractureAnalysis.getInstance().file.getFileName();
        String sep = FractureAnalysis.getInstance().file.getSeparator();
        if(indexA>=0 & indexB>=0){
            Vector arrayA = OpenDataset.openCSVFileToVector(
                    filename, sep, indexA, true);
            Vector arrayB = OpenDataset.openCSVFileToVector(
                    filename, sep, indexB, true);
            double covariance = Covariance.covariance(arrayA, arrayB);
            lCovariance.setText(String.valueOf(covariance));
            double pearson = Correlation.pearsonCoeff(arrayA, arrayB);
            lPearson.setText(String.valueOf(pearson));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
