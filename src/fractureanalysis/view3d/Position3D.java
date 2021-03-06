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
package fractureanalysis.view3d;

/**
 *
 * @author elidioxg
 */
public class Position3D {

    /**
     * Position3D represents a point on 3D scene. 
     */
    
    /**
     * This variables limit the range of values used for 3D plot.
     */
    private final double minX = -100000.;
    private final double maxX = 100000.;
    private final double minY = -100000.;
    private final double maxY = 100000.;
    private final double minZ = -100000.;
    private final double maxZ = 100000.;

    private double X = 0.;
    private double Y = 0.;
    private double Z = 0.;

    /**
     * If X, Y, Z is not defined on constructor, the default position will be 0,
     * 0, 0
     */
    public Position3D() {

    }

    /**
     * Constructor with the X, Y, Z values defined
     * 
     * @param x
     * @param y
     * @param z
     * @throws Exception If x, y or z exceeds the minimum or maximum value constants
     */
    public Position3D(double x, double y, double z) throws Exception {
        setX(x);
        setY(y);
        setZ(z);
    }

    /**
     * Define X, Y and Z values for this position
     *
     * @param x
     * @param y
     * @param z
     * @throws Exception
     */
    public void setXYZ(double x, double y, double z) throws Exception {
        setX(x);
        setY(y);
        setZ(z);
    }

    public void setX(double x) throws Exception {
        if (x >= minX && x <= maxX) {
            this.X = x;
        } else {
            throw new Exception("Value 'X' out of range");
        }
    }

    public void setY(double y) throws Exception {
        if (y >= minY && y <= maxY) {
            this.Y = y;
        } else {
            throw new Exception("Value 'Y' out of range");
        }
    }

    public void setZ(double z) throws Exception {
        if (z >= minZ && z <= maxZ) {
            this.Z = z;
        } else {
            throw new Exception("Value 'Z' out of range");
        }
    }

    public double getX() {
        return this.X;
    }

    public double getY() {
        return this.Y;
    }

    public double getZ() {
        return this.Z;
    }

}
