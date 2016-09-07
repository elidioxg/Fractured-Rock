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

import fractureanalysis.Matrices.Vector;
import fractureanalysis.Vectors.Matrix;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.plot.PlotSeries;
import fractureanalysis.stages.VariogramStage;
import fractureanalysis.statistics.Variogram;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
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
    protected TextField tfInitial, tfStep, tfMax, tfMinAngle, tfMaxAngle;
            
    @FXML
    protected LineChart lcVariogram;
    
    @FXML
    protected void plot() throws Exception{
        int index = cbDatasets.getSelectionModel().getSelectedIndex();
        DatasetModel dm = (DatasetModel)VariogramStage.getInstance().getDatasets().get(index);
        //Matrix matrix = OpenDataset.openCSVFileToMatrix(dm.getFileName(), 
          //      dm.getSeparator(), dm.getHeader());
        
        int xIndex = cbX.getSelectionModel().getSelectedIndex();
        Vector x = OpenDataset.openCSVFileToVector(dm.getFileName(), 
                dm.getSeparator(), xIndex,dm.getHeader());
        int yIndex = cbY.getSelectionModel().getSelectedIndex();
        Vector y = OpenDataset.openCSVFileToVector(dm.getFileName(), 
                dm.getSeparator(), yIndex, dm.getHeader());        
        int contentIndex = cbContent.getSelectionModel().getSelectedIndex();        
        Vector content = OpenDataset.openCSVFileToVector(dm.getFileName(), 
                dm.getSeparator(), contentIndex,dm.getHeader());
        
        double initDist = Double.valueOf(tfInitial.getText().trim());
        double stepSize = Double.valueOf(tfStep.getText().trim());
        double maxDist = Double.valueOf(tfMax.getText().trim());
        //double minAngle = Double.valueOf(tfMinAngle.getText().trim());
        //double maxAngle = Double.valueOf(tfMaxAngle.getText().trim());        
        Matrix result = Variogram.variogram2D(x, y, content, initDist, 
                stepSize, maxDist);
        
        
        //Matrix result = Variogram.variogram2D(matrix, initDist, stepSize, 
          //      maxDist, xIndex, yIndex, contentIndex);
          ArrayList<Double> alX = new ArrayList<>();
          ArrayList<Double> alY = new ArrayList<>();
        for(int i=0; i<result.getLinesCount(); i++){
            alX.add(result.get(0, i).doubleValue());
            alY.add(result.get(1, i).doubleValue());            
        }        
        lcVariogram.getData().addAll(PlotSeries.plotLineSeries(alX, alY));
        
    }
    
}
