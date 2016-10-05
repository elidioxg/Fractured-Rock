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
package fractureanalysis.statistics;

import fractureanalysis.Vectors.Vector;

public class Pearson {
    
    
    public static double pearsonCoeff(Vector vector1, Vector vector2) throws Exception{        
        double var1 = Variance.variance(vector1);
        double var2 = Variance.variance(vector2);
        double cov = Covariance.covariance(vector1, vector2);
        return cov/(Math.sqrt(var1*var2));        
    }
    
    /**
     * Pearson coefficient
     * @param variance1
     * @param variance2
     * @param covariance
     * @return 
     */
    public static double pearsonCoeff(double variance1, double variance2,
            double covariance){
        return covariance/Math.sqrt(variance1*variance2);    
    }
    
}
