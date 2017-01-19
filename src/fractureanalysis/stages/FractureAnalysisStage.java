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
package fractureanalysis.stages;

import fractureanalysis.model.DatasetModel;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class FractureAnalysisStage {
    
    private static FractureAnalysisStage instance;
    
    private static List<DatasetModel> datasets;
    
    public FractureAnalysisStage(List<DatasetModel> datasets){
        this.datasets = datasets;
        instance = this;
    }
    
    public static FractureAnalysisStage getInstance(){
        return instance;
    }
    
    public static List<DatasetModel> getDatasets(){
        return datasets;
    }
    
    public void createStage() throws IOException{
        if(!getDatasets().isEmpty()){
            FXMLLoader loader = new FXMLLoader(
                    fractureanalysis.FractureAnalysis.getInstance().getClass()
                            .getResource("views/tab_analysis_view.fxml")
            );
            Parent parent = (Parent) loader.load();
            /*
            List list = new ArrayList();
            for(int i = 0; i < getDatasets().get(0).getHeaderArray().size(); i++){
                list.add(getDatasets().get(0).getHeaderArray(i));
            }
            List datasetList = new ArrayList();
            for (int i = 0; i < getDatasets().size(); i++) {                
                datasetList.add(getDatasets().get(i).getDatasetName());
            }
            */
            Stage stageLine = new Stage();
            Scene scene = new Scene(parent);
            stageLine.setTitle("Fracture Intensity Analysis");
            stageLine.setScene(scene);
            stageLine.show();
            
        }
    }
    
}
