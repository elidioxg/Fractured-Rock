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
package fractureanalysis.analysis;

import java.util.ArrayList;

/**
 *
 * @author elidioxg
 */
public class Variograms {
    
    /**
     * Defines value of variogram for maximum distance. 
     * Variogram for scanline(unidimensional).
     * 
     * @param scanline     
     * @param maxDistance
     * @return 
     */
    public static Double variogram1D(ScanLine scanline, 
            double maxDistance){        
        int pairs =0;
        ArrayList<Double> ap = scanline.getApList();
        ArrayList<Double> dist = scanline.getDistanceList();
        double sum = 0.;                
        for(int i=0 ; i<dist.size(); i++){
            for(int j=0; j< dist.size(); j++){
                if(i!=j){
                    if( (Math.pow(dist.get(i),2)- Math.pow(dist.get(j),2)) <=Math.pow(maxDistance, 2) ){
                        sum += Math.pow(ap.get(i) - ap.get(j),2);
                        pairs +=1;
                    }
                }
            }
        }       
        if(pairs==0){
            return 0.;
        }else{        
            return sum/(2*pairs);
        }
    }
    
}
