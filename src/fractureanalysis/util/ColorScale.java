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
package fractureanalysis.util;

import javafx.scene.paint.Color;

/**
 *
 * @author elidioxg
 */
public class ColorScale {

    private final double minValue;
    private final double maxValue;
    private Color[] color = new Color[100];

    public ColorScale(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        greenYellowRed();
    }

    /**
     * Generate Green to Red Scale
     */
    private void greenYellowRed() {
        for (int i = 0; i < 100; i++) {
            double H = i;
            double S = 0.9; // Saturation
            double B = 0.9; // Brightness
            color[i] = Color.hsb((float) H, (float) S, (float) B);
        }
    }

    public Color getColor(double value) throws Exception {
        if (value > this.maxValue || value < this.minValue) {
            throw new Exception("Value out of range.");
        }
        /**
         * Get the index of color considering the range of values and the color
         * scale
         */
        double index = (value - this.minValue) / (this.maxValue - this.minValue) * 100;
        /**
         * Invert values
         */
        int aux = (int) ((int) 100 - index);
        /**
         * Index = 100 don't exist
         */
        if (aux == 100) {
            aux = 99;
        }
        return this.color[aux];
    }

}
