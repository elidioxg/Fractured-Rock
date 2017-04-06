/*
 * Copyright (C) 2017 fedora
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

/**
 *
 * @author fedora
 */
public class GeoeasFormat {
    
    private String title = "";
    private ArrayList<String> headers = new ArrayList();
    private int nColumns = 0;
    private int nRows = 0;
    
    public String getTitle(){
        return this.title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public ArrayList<String> getHeaders(){
        return this.headers;
    }
    
    public void addHeader(String header){
        this.headers.add(header);
    }
    
    public void setHeaders(ArrayList<String> headers){
        this.headers = headers;
    }
    
    public int getRowsCount(){
        return this.nRows;
    }
    
    public void setRowsCount(int count){
        this.nRows = count;
    }
    
    public int getColumnsCount(){
        return this.nColumns;
    }
    
    public void setColumnsCount(int count){
        this.nColumns = count;
    }
    
}
