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

import fractureanalysis.analysis.ScanLine;
import fractureanalysis.scene.MaterialProperties;
import javafx.scene.shape.DrawMode;

/**
 *
 * @author elidioxg
 */
public class DrawPlanes3D {
    
    private static double viewSize = 1000;
    private static double defaultPlaneMinWidth = 1.;
    
    public static void setViewSize(double size){
        viewSize = size;
    }
    
    public static double getViewSize(){
        return viewSize;
    }
    
    /**
     * Receives a scanline data to make a 3D plot of the fractures. 
     * @param scanline
     * @return 
     * @throws java.lang.Exception 
     */
    public static FracturePlane3D[] drawPlanes(ScanLine scanline) throws Exception {
        FracturePlane3D[] planes = new FracturePlane3D[scanline.fracturesCount()];
        setViewSize(scanline.getLenght());        
        double distance = 0.;              
        for (int i = 0; i < scanline.fracturesCount(); i++) {                                                       
            distance+= scanline.getSpList().get(i);
            double position = distance;
            double width = scanline.getApList().get(i);            
            if(width<defaultPlaneMinWidth){ width = defaultPlaneMinWidth;}
            planes[i] = new FracturePlane3D(width, position);               
            distance += scanline.getApList().get(i);                                   
            planes[i].setMaterial(MaterialProperties.setFractureMaterial());
            planes[i].setDrawMode(DrawMode.FILL);          
        }
        return planes;
    }
    
}
