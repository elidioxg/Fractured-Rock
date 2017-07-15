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
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;

/**
 *
 * @author elidioxg
 */
public class CanvasSearchWindow {

    private final double WIDTH = 400;
    private final double LENGHT = 400;

    private final double initAngle;
    private final double finalAngle;
    private final Canvas canvas = new Canvas(WIDTH, LENGHT);

    public CanvasSearchWindow(double angle, double tolerance, boolean reflect) {
        initAngle = Angles.parseAngle(angle - (tolerance / 2) + Angles.getCorrection());
        finalAngle = Angles.parseAngle(tolerance);        
        draw(initAngle, finalAngle, reflect);
    }

    private void draw(double init, double end, boolean ref) {
        double radiusX = canvas.getWidth() / 1.5;
        double radiusY = canvas.getHeight() / 1.5;
        double centerX = (canvas.getWidth() / 2) - (radiusX / 2);
        double centerY = (canvas.getHeight() / 2) - (radiusY / 2);
        canvas.getGraphicsContext2D().setStroke(Paint.valueOf("BLACK"));
        canvas.getGraphicsContext2D().setFill(Paint.valueOf("WHITE"));
        canvas.getGraphicsContext2D().strokeArc(
                centerX, centerY,
                radiusX, radiusY,
                init, end,
                ArcType.ROUND);
        if (ref) {
            canvas.getGraphicsContext2D().strokeArc(
                    centerX, centerY,
                    radiusX, radiusY,
                    -init, -end,
                    ArcType.ROUND);
        }
    }
    
    public Canvas getCanvas(){
        return canvas;
    }

    public double getInitAngle() {
        return initAngle;
    }

    public double getFinalAngle() {
        return finalAngle;
    }

}
