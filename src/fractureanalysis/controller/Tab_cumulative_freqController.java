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
import fractureanalysis.plot.PlotSeries;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author elidioxg
 */
public class Tab_cumulative_freqController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    protected ComboBox cbColumn;

    @FXML
    protected LineChart lcPoints, lcLine;
    @FXML
    protected void cbAction() throws Exception {
        lcPoints.getData().clear();
        lcLine.getData().clear();
        int selected = cbColumn.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            String filename = FractureAnalysis.getInstance().file.getFileName();
            String sep = FractureAnalysis.getInstance().file.getSeparator().getSep();
            boolean header = FractureAnalysis.getInstance().file.getHeader();
            Vector vector = OpenDataset.openCSVFileToVector(filename, sep, selected, header);
            double sum = vector.sum();
            System.out.println("Sum: "+sum);
            double cumulative = 0.;
            Vector x = new Vector(vector.size());
            Vector y = new Vector(vector.size());
            for (int i = 0; i < vector.size(); i++) {
                cumulative+=vector.get(i).doubleValue();
                System.out.println("Cum: "+cumulative);
                x.set(i,i);
                //x.add(i);
                y.set(i, cumulative/sum*100);
                //y.add(cumulative/sum*100);
                System.out.println("vector y value: "+y.get(i).doubleValue());
            }
            lcPoints.getData().addAll(PlotSeries.plotLineSeries(x, y));
            lcLine.getData().addAll(PlotSeries.plotLineSeries(x, y));
        }
    }

}
