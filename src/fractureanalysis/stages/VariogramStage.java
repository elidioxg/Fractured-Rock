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
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author elidioxg
 */
public class VariogramStage {
    
    public void createStage() throws IOException {
        if (!FractureAnalysis.getInstance().file.getFileName().trim().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(
                    FractureAnalysis.getInstance().getClass().getResource(
                            "views/stage_variogram.fxml"));
            Parent parent = (Parent) loader.load();                        

            Stage stageLine = new Stage();
            Scene scene = new Scene(parent);
            stageLine.setTitle("2D Variogram");
            stageLine.setScene(scene);
            stageLine.show();
        }
    }
    
}
