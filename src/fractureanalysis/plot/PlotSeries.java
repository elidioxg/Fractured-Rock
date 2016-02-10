/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractureanalysis.plot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

public class PlotSeries {
    
    public XYChart.Series plotLineSeries(String filename, String separator,
            String serieLabel, int columnX, int columnY) {
        
        XYChart.Series<Number, Number> series = new XYChart.Series();
        series.setName(serieLabel);

        Task<List<XYChart.Data<Number, Number>>> task;
        task = new Task<List<XYChart.Data<Number, Number>>>() {
            @Override
            protected List<XYChart.Data<Number, Number>> call() throws Exception {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                List<XYChart.Data<Number, Number>> chartData = new ArrayList<>();
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    final String[] dataValues = dataLine.split(separator);
                    chartData.add(new XYChart.Data(dataValues[columnX],
                            dataValues[columnY]));
                }
                return chartData;
            }
        };
        task.setOnSucceeded(e -> series.getData().addAll(task.getValue()));

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return series;
    }
    
    public XYChart.Series<Number, String> plotBarSeries(String filename, String separator, 
            String serieName, int columnX, int columnY){
        XYChart.Series<Number, String> series = new XYChart.Series();
        series.setName(serieName);

        Task<List<XYChart.Data<Number, String>>> task;
        task = new Task<List<XYChart.Data<Number, String>>>() {
            @Override
            protected List<XYChart.Data<Number, String>> call() throws Exception {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                List<XYChart.Data<Number, String>> chartData = new ArrayList<>();
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    final String[] dataValues = dataLine.split(separator);
                    chartData.add(new XYChart.Data(
                            Double.valueOf(dataValues[columnX]),
                            dataValues[columnY]));
                }
                return chartData;
            }
        };
        task.setOnSucceeded(e -> series.getData().addAll(task.getValue()));

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return series;
    }
    
}
