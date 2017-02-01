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
package fractureanalysis.model;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author elidioxg
 */
public class BlocksSurface {
    private static final int canvasSize = 300;
    
    private static double axisLen;    
    
    private Canvas surface;
    /*
     *Number of horizontal and vertical lines
     */
    private final int gridLines;
    
    private Block[][] blocks;
    
    private static double minContent;
    private static double maxContent;
    
    private static ArrayList<Double> xTicksLabels;
    private static ArrayList<Double> yTicksLabels;
    
    GraphicsContext gc;
            
    public BlocksSurface(int nGridLines, double axisLen){
        surface = new Canvas(canvasSize, canvasSize);
        this.gridLines  = nGridLines;
        gc = surface.getGraphicsContext2D();
        blocks = new Block[nGridLines][nGridLines];        
        for(int i = 0 ; i < gridLines; i++){
            for(int j = 0 ; j < gridLines; j++){
                blocks[j][i] = new Block(this, j, i);                
            }
        }        
    }
    
    public int getSize(){
        return BlocksSurface.canvasSize;
    }
    
    public int getNGridLines(){
        return this.gridLines;
    }
    
    public void drawBlocks(){
        /*for(int i = 0 ; i < gridLines; i++){
            for(int j = 0 ; j < gridLines; j++){
                
            }
        }*/
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        this.gc.fillRect(blocks[1][1].getX(), 
                blocks[1][1].getY(), 
                blocks[1][1].getSize(), 
                blocks[1][1].getSize());
    }
}
