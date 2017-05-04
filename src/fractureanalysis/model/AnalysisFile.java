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
package fractureanalysis.model;

import fractureanalysis.analysis.ScanLine;

/**
 *
 * @author elidioxg
 */
public class AnalysisFile extends DatasetModel {
    
    /**
     * The AnalysisFile works with a Scanline class. The Scanline is represented
     * as a list of fractures and their properties. 
     * This class extends the DatasetModel class, that is a handler of a
     * specific dataset file, with all their properties.
     */
    private ScanLine scanline = new ScanLine(null);
    
    /**
     * Creates a null scanline.
     */
    public AnalysisFile(){
        
    }
    
    public AnalysisFile(DatasetModel dataset){        
        this.setColumnsCount(dataset.getColumnsCount());
        this.setDatasetName(dataset.getDatasetName());
        this.setFilename(dataset.getFileName());
        this.setGeoeasFormat(dataset.isGeoeas());
        this.setHeader(dataset.getHeader());
        this.setHeaderStrings(dataset.getHeaderArray());
        this.setRowsCount(dataset.getRowsCount());
        this.setSepString(dataset.getSeparator().getChar());
        this.setSeparator(dataset.getSeparator());
    }
    
    /**
     * Set the scanline for this class
     * @param scanline 
     */
    public void setScanLine(ScanLine scanline){
        this.scanline = scanline;
    }
    
    public ScanLine getScanLine(){
        return this.scanline;
    }

}
