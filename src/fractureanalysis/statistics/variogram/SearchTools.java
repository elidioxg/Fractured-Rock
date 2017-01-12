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
public class SearchTools {
    
    
    /**
     * Is Target inside Search Window
     *      
     * @param search
     * @param targetX Coordinate of target point
     * @param targetY Coordinate of target point
     * @return 
     */
    public static boolean isInside(SearchWindow search, 
            double targetX, double targetY){
        
        double az = getAzimuth(search.getX(), search.getY(), targetX, targetY);        
        if(az >= (search.getAngle()-search.getAngleTolerance()) && 
                az<=(search.getAngle()+search.getAngleTolerance())){
            double dist = getDistance2D(search.getX(), search.getY(), targetX, targetY);
            if(dist>= (search.getStep() - search.getTolerance()) && 
                    dist<= (search.getStep()+search.getTolerance())){
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Is Target inside Search Window
     * 
     * @param originX Coordinate of origin point
     * @param originY Coordinate of origin point
     * @param step Step size of variogram calculation
     * @param tolerance 
     * @param angle
     * @param tolAngle 
     * @param mirror
     * @param targetX Coordinate of target point
     * @param targetY Coordinate of target point
     * @return 
     */
    public static boolean isInside(double originX, double originY, double step,
            double tolerance, double angle, double tolAngle, boolean mirror,
            double targetX, double targetY){
        
        double az = getAzimuth(originX, originY, targetX, targetY);        
        if(az >= (angle-tolAngle) && az<=(angle+tolAngle)){
            double dist = getDistance2D(originX, originY, targetX, targetY);
            if(dist>= (step - tolerance) && dist<= (step+tolerance)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get the distance from (Xo,Yo) to (Xf, Yf)
     * Xo, Yo = origin coordinates
     * Xf, Yf = target coordinates
     * 
     * @param originX
     * @param originY
     * @param targetX
     * @param targetY
     * @return 
     */
    public static double getDistance2D(double originX, double originY,
            double targetX, double targetY){
        double result = 
                Math.sqrt(Math.pow( 
                        (targetX - originX), 2) + Math.pow((targetY-originY), 2));
        return result;
    }

    /**
     * Get the Azimuth from North considering origin at (X,Y)=(0,0)
     *
     * @param X
     * @param Y
     * @return
     */
    public static double getAzimuth(double X, double Y) {
        double Xc = Math.sqrt(Math.pow(X, 2));
        double Yc = Math.sqrt(Math.pow(Y, 2));
        /**
         * Distance is Hipotenuse
         */
        double distance = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
        double result = Double.NaN;
        /**
         * X may be equal, bigger or smaller than Y
         */
        if (Xc == Yc) {
            result = Math.PI / 4.;
        } else if (Xc > Yc) {
            result = Math.asin(Yc / distance);
        } else if (Xc < Yc) {
            result = Math.asin(Xc / distance);
        }
        if (X >= 0) {
            if (Y < 0) {
                result += Math.PI/2.;
            }
        } else if (X < 0) {
            if (Y >= 0) {
                result += 3./2. * Math.PI;
            } else {
                result += Math.PI;
            }
        }
        result = radToDeg(result);
        return result;
    }
    
    /**
     * Get the Azimuth from North 
     *
     * @param originX
     * @param originY
     * @param X
     * @param Y
     * @return
     */
    public static double getAzimuth(double originX, double originY,
            double X, double Y) {        
        double Xc = Math.sqrt(Math.pow(X - originX, 2));
        double Yc = Math.sqrt(Math.pow(Y - originY, 2));
        /**
         * Distance is Hipotenuse
         */
        double distance = Math.sqrt(Math.pow(X- originX, 2) + Math.pow(Y- originY, 2));
        double result = Double.NaN;
        /**
         * X may be equal, bigger or smaller than Y
         */
        if (Xc == Yc) {
            result = Math.PI / 4.;
        } else if (Xc > Yc) {
            result = Math.asin(Yc / distance);
        } else if (Xc < Yc) {
            result = Math.asin(Xc / distance);
        }
        if (X >= 0) {
            if (Y < 0) {
                result += Math.PI/2.;
            }
        } else if (X < 0) {
            if (Y >= 0) {
                result += 3./2. * Math.PI;
            } else {
                result += Math.PI;
            }
        }
        result = radToDeg(result);
        return result;
    }
    
    /**
     * Convert Radians to Degrees
     * 
     * @param radians
     * @return 
     */
    private static double radToDeg(double radians){
        return radians*180./Math.PI;
    }

}
