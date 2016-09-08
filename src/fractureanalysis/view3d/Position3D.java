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
    private final double minX = 0.;
    private final double maxX = 1000.;    
    private final double minY = 0.;
    private final double maxY = 1000.;
    private final double minZ = 0.;
    private final double maxZ = 1000.;

    private double X = 0.;
    private double Y = 0.;
    private double Z = 0.;
    
    public Position3D(){
    
    }
    
    public void setXYZ(double x, double y , double z){
        setX(x);
        setY(y);
        setZ(z);
    }

    public void setX(double x) {
        if (x >= minX && x <= maxX) {
            this.X = x;
        }
    }

    public void setY(double y) {
        if (y >= minY && y <= maxY) {
            this.Y = y;
        }
    }

    public void setZ(double z) {
        if (z >= minZ && z <= maxZ) {
            this.Z = z;
        }
    }
    
    public double getX(){
        return this.X;
    }
    
    public double getY(){
        return this.Y;
    }
    
    public double getZ(){
        return this.Z;
    }

}
