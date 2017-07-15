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

import fractureanalysis.Vectors.Vector;
import fractureanalysis.statistics.MaximumValue;
import fractureanalysis.statistics.MinimumValue;
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

    private final double distTol;
    private final double initAngle;
    private final double finalAngle;
    private final boolean reflect;
    private final Vector vecX, vecY, vecZ;
    private final double totalDistance;
    private final int POINT_SIZE = 4;
    private final Canvas canvas = new Canvas(WIDTH, LENGHT);

    public CanvasSearchWindow(Vector x, Vector y, Vector z, double distTol,
            double angle, double angleTol, boolean reflect) {
        this.distTol = distTol;
        initAngle = Angles.parseAngle(angle - (angleTol / 2) + Angles.getCorrection());
        finalAngle = Angles.parseAngle(angleTol);
        this.reflect = reflect;
        this.vecX = x;
        this.vecY = y;
        this.vecZ = z;
        double distX = MaximumValue.getMaxValue(vecX) - MinimumValue.getMinValue(vecX);
        double distY = MaximumValue.getMaxValue(vecY) - MinimumValue.getMinValue(vecY);
        if (distX < distY) {
            totalDistance = distY;
        } else {
            totalDistance = distX;
        }
        drawScene();
        drawPoints();
        drawSearchWindow();
    }

    private void drawScene() {
        canvas.getGraphicsContext2D().setStroke(Paint.valueOf("BLACK"));
        canvas.getGraphicsContext2D().setFill(Paint.valueOf("BLUE"));
        canvas.getGraphicsContext2D().strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }

    private void drawPoints() {
        for (int i = 0; i < vecX.size(); i++) {
            int posx = (int) ((vecX.get(i).doubleValue() - MinimumValue.getMinValue(vecX)) 
                    * canvas.getWidth() / totalDistance);
            int posy = (int) ((vecY.get(i).doubleValue() - MinimumValue.getMinValue(vecY)) 
                    * canvas.getWidth() / totalDistance);

            canvas.getGraphicsContext2D().strokeRect(
                    posx - POINT_SIZE / 2,
                    canvas.getHeight() - (posy - POINT_SIZE / 2),
                    POINT_SIZE / 2,
                    POINT_SIZE / 2);
        }
    }

    private void drawSearchWindow() {
        double radiusX = canvas.getWidth() * distTol / totalDistance;
        double radiusY = canvas.getHeight() * distTol / totalDistance;
        double centerX = (canvas.getWidth() / 2) - (radiusX / 2);
        double centerY = (canvas.getHeight() / 2) - (radiusY / 2);
        canvas.getGraphicsContext2D().strokeArc(
                centerX, centerY,
                radiusX, radiusY,
                this.initAngle, this.finalAngle,
                ArcType.ROUND);
        if (this.reflect) {
            canvas.getGraphicsContext2D().strokeArc(
                    centerX, centerY,
                    radiusX, radiusY,
                    -this.initAngle, -this.finalAngle,
                    ArcType.ROUND);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }
    
}
