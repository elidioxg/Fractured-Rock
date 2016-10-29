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
package fractureanalysis.stages;

import fractureanalysis.FractureAnalysis;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class Variogram1DStage {
    
    public Variogram1DStage(List<DatasetModel> datasets){
        instance = this;
        this.datasets = datasets;
    }
    
    private static Variogram1DStage instance;
    
    public List<DatasetModel> datasets;
    public List<DatasetModel> getDatasets(){
        return this.datasets;
    }
    
    public static Variogram1DStage getInstance(){
        return instance;
    }        

    public void createStage() throws IOException {
        FXMLLoader loader = 
                new FXMLLoader(getClass().getResource("views/stage_variogram1D.fxml"));
        
        Parent root = (Parent) loader.load();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("1D Variogram");
        stage.setScene(scene);
        stage.show();
    }

}
