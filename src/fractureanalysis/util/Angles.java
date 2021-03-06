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

/**
 *
 * @author elidioxg
 */
public class Angles {

    private static final double CORRECTION_FACTOR = 0;

    public static double parseAngle(double angle) {
        while (angle > 360.) {
            angle -= 360.;
        }
        while (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public static double getCorrection(double value) {
        return(parseAngle(360-(value+CORRECTION_FACTOR)));
    }

    public static double getRevAngle(double angle) {
        return (parseAngle(angle + 180.));
    }

}
