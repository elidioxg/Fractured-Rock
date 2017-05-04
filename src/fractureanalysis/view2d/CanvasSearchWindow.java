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
package fractureanalysis.view2d;

import fractureanalysis.util.Angles;

/**
 *
 * @author elidioxg
 */
public class CanvasSearchWindow {     

    private final double initAngle;
    private final double finalAngle;

    public CanvasSearchWindow(double angle, double tolerance) {
        initAngle = Angles.parseAngle(angle - tolerance / 2);
        finalAngle = Angles.parseAngle(angle + tolerance / 2);
    }

    public double getInitAngle() {
        return initAngle;
    }

    public double getFinalAngle() {
        return finalAngle;
    }   

}
