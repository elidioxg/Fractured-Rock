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

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author elidioxg
 */
public class Block {
    /**
     * The index of position on surface
     */
    private final int column;
    private final int line;
    /**
     * Bounds of the block
     */
    private final double xi, yi;
    private final double blockSize;
    
    private Number content = 0.;
    
    private Color color;
    private final Color TEXT_COLOR = Color.BLACK;
    private final int FONT_SIZE = 9;
    
    private final int padding = 15;
    
    private final BlocksSurface owner;
    
    public Block(BlocksSurface owner, int column, int line){        
        this.column = column;
        this.line = line;
        this.owner = owner;
        this.blockSize = (owner.getSize()/owner.getNGridLines());
        xi = blockSize*column;        
        yi = blockSize*line;        
    }
    
    public double getX(){
        return xi;
    }
    
    public double getY(){
        return yi;
    }
    
    public double getSize(){
        return this.blockSize;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public void setContent(Number value){
        this.content = value;
    }
    
    public Number getContent(){
        return this.content;
    }
    
    public void draw(GraphicsContext context, BlocksSurface owner){
        context.setFill(getColor());
        context.fillRect(getX(), getY(), 
                getSize(), getSize());
        if(owner.showLabels()){      
            context.setFill(TEXT_COLOR);
            context.setFont(Font.font(FONT_SIZE));
            context.fillText(String.valueOf(content), getX()+padding, getY()+padding);
        }
    }
    
}
