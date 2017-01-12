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
package fractureanalysis.statistics.variogram;

/**
 *
 * @author elidioxg
 */
public class SearchWindow {
    /**
     * Position of origin of the Window Search
     */
    private final double posX;
    private final double posY;
    /**
     * Angle Direction
     */
    private final double angleDir;
    /**
     * Angle Tolerance
     */
    private final double angleTol;
    /**
     * Step size
     */
    private final double step;
    /**
     * Tolerance Distance
     */
    private final double distTol;
    /**
     * Is the Window Search reflected on opposite side?
     */
    private boolean reflected = false;

    /**
     * Constructor
     *
     * @param posX
     * @param posY
     * @param angleDir
     * @param angleTol
     * @param step
     * @param distTol
     * @param ref
     */
    public SearchWindow(double posX, double posY, double angleDir,
            double angleTol, double step, double distTol, boolean ref) {
        this.posX = posX;
        this.posY = posY;
        this.angleDir = angleDir;
        this.angleTol = angleTol;
        this.step = step;
        this.distTol = distTol;
        this.reflected = ref;
    }
    
    public double getX(){
        return this.posX;
    }
    
    public double getY(){
        return this.posY;
    }
    
    public double getAngle(){
        return this.angleDir;
    }
    
    public double getAngleTolerance(){
        return this.angleTol;
    }
    
    public double getStep(){
        return this.step;
    }
    
    public double getTolerance(){
        return this.distTol;
    }
    
    public boolean reflected(){
        return this.reflected;
    }
}
