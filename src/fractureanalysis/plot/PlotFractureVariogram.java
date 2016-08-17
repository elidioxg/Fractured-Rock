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
package fractureanalysis.plot;

import fractureanalysis.analysis.ScanLine;
import fractureanalysis.analysis.Variograms;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 *
 * @author elidioxg
 */
public class PlotFractureVariogram {

    /**
     * 
     * @param distanceList
     * @return
     * @throws Exception
     */
    public static XYChart.Series variogram1D(ScanLine scanline,
            ObservableList<Double> distanceList) throws Exception {

        ArrayList<Double> distance = new ArrayList();
        ArrayList<Double> variogramValue = new ArrayList();

        for (int i = 0; i < distanceList.size(); i++) {
            Double value = Variograms.variogram1D(scanline, distanceList.get(i));
            distance.add(distanceList.get(i));
            variogramValue.add(value);
        }

        return PlotSeries.plotLineSeries(distance, variogramValue);
    }
}
