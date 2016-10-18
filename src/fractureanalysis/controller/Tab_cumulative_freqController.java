/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
            String sep = FractureAnalysis.getInstance().file.getSeparator();
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
                y.set(i, cumulative/sum*100);
                System.out.println("vector y value: "+y.get(i).doubleValue());
            }
            lcPoints.getData().addAll(PlotSeries.plotLineSeries(x, y));
            lcLine.getData().addAll(PlotSeries.plotLineSeries(x, y));
        }
    }

}
