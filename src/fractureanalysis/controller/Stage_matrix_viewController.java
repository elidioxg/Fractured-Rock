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
package fractureanalysis.controller;

import fractureanalysis.Matrices.Matrix;
import fractureanalysis.data.OpenDataset;
import fractureanalysis.model.BlocksSurface;
import fractureanalysis.model.DatasetModel;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
import fractureanalysis.util.ColorScale;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author elidioxg
 */
public class Stage_matrix_viewController implements Initializable {

    @FXML
    protected Canvas cSurface;

    @FXML
    protected ComboBox cbDatasets;

    private GraphicsContext gc;

    @FXML
    protected void cbChange() throws Exception {
        DatasetModel selected = (DatasetModel) cbDatasets.getSelectionModel().getSelectedItem();
        if (selected.getColumnsCount() == selected.getRowsCount()) {
            gc = cSurface.getGraphicsContext2D();
            Matrix matrix = OpenDataset.openCSVFileToMatrix(
                    selected.getFileName(),
                    selected.getSeparator().getChar(),
                    selected.getHeader());
            double min = MinimumValue.getMinValue(matrix);
            double max = MaximumValue.getMaxValue(matrix);
            ColorScale scale = new ColorScale(min, max);
            BlocksSurface surface
                    = new BlocksSurface(gc, matrix.getColumnsCount() - 1, gc.getCanvas().widthProperty().intValue());
            surface.drawBlocks(matrix, scale);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = cSurface.getGraphicsContext2D();
    }

}
