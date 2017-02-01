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

import javafx.scene.paint.Color;

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
    
    private double content = 0.;
    
    private static Color color;
    
    private final BlocksSurface owner;
    
    public Block(BlocksSurface owner, int column, int line){        
        this.column = column;
        this.line = line;
        this.owner = owner;
        this.blockSize = (this.owner.getSize()/this.owner.getNGridLines());
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
}
