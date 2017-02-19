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

import fractureanalysis.Matrices.Matrix;
import fractureanalysis.util.ColorScale;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author elidioxg
 */
public class BlocksSurface {
    /**
     * Size of canvas
     */
    private int canvasSize;    
    
    private Canvas surface;
    /*
     *Number of horizontal and vertical lines
     */
    private final int gridLines;
    /**
     * Number of Blocks (per column or line) is gridLines + 1
    */
    private final int nBlocks;
    
    private Block[][] blocks;
    
    private static double minContent;
    private static double maxContent;
    
    private static ArrayList<Double> xTicksLabels;
    private static ArrayList<Double> yTicksLabels;
    
    private GraphicsContext gc;
    
    private final Color SURFACE_COLOR = Color.WHITE;
    
    private boolean labels = true;
            
    public BlocksSurface(GraphicsContext context, int nGridLines, int axisLen){
        surface = new Canvas(axisLen, axisLen);
        this.canvasSize = axisLen;
        this.gridLines  = nGridLines;
        this.nBlocks = nGridLines+1;
        gc = context;
        gc.setFill(SURFACE_COLOR);
        gc.fillRect(0, 0, axisLen, axisLen);   
        blocks = new Block[nGridLines+1][nGridLines+1];        
        for(int i = 0 ; i < gridLines+1; i++){
            for(int j = 0 ; j < gridLines+1; j++){
                blocks[j][i] = new Block(this, j, i);                
                //blocks[j][i].setColor(teor, escala de teor);
            }
        }        
    }
    
    public int getSize(){
        return canvasSize;
    }
    
    public int getNGridLines(){
        return this.gridLines;
    }
    
    public void drawBlocks(Matrix data, ColorScale scale) throws Exception{
        gc.setLineWidth(1);
        for(int i = 0 ; i < gridLines+1; i++){
            for(int j = 0 ; j < gridLines+1; j++){
                blocks[i][j].setColor(
                        scale.getColor(data.get(i,j).doubleValue()));
                blocks[i][j].setContent(data.get(i,j));
                blocks[i][j].draw(gc, this);
            }
        }              
    }
    
    public boolean showLabels(){
        return this.labels;
    }
}
