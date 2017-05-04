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

import fractureanalysis.analysis.FractureIntensityAnalysis;
import fractureanalysis.model.AnalysisFile;
import fractureanalysis.model.DatasetModel;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class FractureAnalysisStage {

    private static FractureAnalysisStage instance;

    private static DatasetModel dataset;
    private static AnalysisFile analysisFile;
    
    private static FractureIntensityAnalysis analysis;

    public FractureAnalysisStage(DatasetModel dataset) throws Exception {
        FractureAnalysisStage.dataset = dataset;
        analysisFile = new AnalysisFile(dataset);
        analysis = new FractureIntensityAnalysis(analysisFile.getScanLine());
        instance = this;
    }

    public static FractureAnalysisStage getInstance() {
        return instance;
    }

    public static DatasetModel getDataset() {
        return dataset;
    }
    
    public static AnalysisFile getAnalysisFile(){
        return analysisFile;
    }
    
    public static FractureIntensityAnalysis getAnalysis(){
        return analysis;
    }

    public void createStage() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                fractureanalysis.FractureAnalysis.getInstance().getClass()
                        .getResource("views/stage_analysis.fxml")
        );
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        /**
         * Get the headers of dataset
         */
        ArrayList<String> list = getDataset().getHeaderArray();
        
        /**
         * Add the headers of dataset to comboboxes
         */
        ComboBox cbSpVar = (ComboBox) parent.getScene().getRoot().lookup("#cbSpVar");
        ComboBox cbApVar = (ComboBox) parent.getScene().getRoot().lookup("#cbApVar");
        cbApVar.setItems(FXCollections.observableArrayList(list));
        cbSpVar.setItems(FXCollections.observableArrayList(list));

        Stage stage = new Stage();
        stage.setTitle("Fracture Intensity Analysis");
        stage.setScene(scene);
        stage.show();

    }

}
