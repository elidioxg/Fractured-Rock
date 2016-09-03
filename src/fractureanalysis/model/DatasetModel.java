package fractureanalysis.model;

import java.util.ArrayList;
import java.util.Arrays;

public class DatasetModel {

    private String datasetName = "";
    private String datasetFilename = "";
    private String separator = ",";
    private boolean hasHeader = true;
    private ArrayList<String> headerStrings;
    private int columnsCount = 0;
    private int rowsCount = 0;
    private int spColumn = -1;
    private int apColumn = -1;

    public DatasetModel() {

    }

    public DatasetModel(String datasetName, String filename, String separator,
            boolean hasHeader, ArrayList<String> headerStrings, int columns, int rows) {
        this.datasetName = datasetName;
        this.datasetFilename = filename;
        this.separator = separator;
        this.hasHeader = hasHeader;
        this.headerStrings = headerStrings;
        this.columnsCount = columns;
        this.rowsCount = rows;

    }

    public void setApColumn(int column) {
        this.apColumn = column;
    }
    
    public int getApColumn(){
        return this.apColumn;
    }
    
    public void setSpColumn(int column) {
        this.spColumn = column;
    }
    
    public int getSpColumn(){
        return this.spColumn;
    }

    public void setDatasetName(String name) {
        this.datasetName = name;
    }

    public String getDatasetName() {
        return this.datasetName;
    }

    public void setFilename(String filename) {
        this.datasetFilename = filename;
    }

    public String getFileName() {
        return this.datasetFilename;
    }

    public void setSeparator(String sep) {
        this.separator = sep;
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setHeader(boolean header) {
        this.hasHeader = header;
    }

    public boolean getHeader() {
        return this.hasHeader;
    }

    public void setHeaderStrings(ArrayList<String> strHeader) {
        this.headerStrings = strHeader;
    }   
    
    public ArrayList<String> getHeaderArray(){
        return this.headerStrings;
    }

    public String getHeaderArray(int colIndex) {
        return this.headerStrings.get(colIndex);
    }

    public void setColumnsCount(int number) {
        this.columnsCount = number;
    }

    public int getColumnsCount() {
        return this.columnsCount;
    }

    public void setRowsCount(int count) {
        this.rowsCount = count;
    }

    public int getRowsCount() {
        return this.rowsCount;
    }
}
