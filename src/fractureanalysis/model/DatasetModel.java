package fractureanalysis.model;

public class DatasetModel {
    private String datasetName = "";
    private String datasetFilename = "";
    private String separator = ",";
    private boolean hasHeader = true;
    private String[] headerStrings;     
    private int columnsCount = 0;
    private int rowsCount = 0;
    
    public DatasetModel(){
    
    }
    
    public DatasetModel(String datasetName, String filename, String separator,
            boolean hasHeader, String[] headerStrings, int columns, int rows){
        this.datasetName = datasetName;
        this.datasetFilename = filename;
        this.separator = separator;
        this.hasHeader = hasHeader;
        this.headerStrings = headerStrings;
        this.columnsCount = columns;
        this.rowsCount = rows;
        
    }
    
    public void setDatasetName(String name){
        this.datasetName = name;
    }
    
    public String getDatasetName(){
        return this.datasetName;
    }
    
    public void setFilename(String filename){
        this.datasetFilename = filename;
    }
   
    public String getFileName(){
        return this.datasetFilename;
    }
    
    public void setSeparator(String sep) {
        this.separator = sep;
    }
    
    public String getSeparator(){
        return this.separator;
    }
    
    public void setHeader(boolean header){
        this.hasHeader = header;
    }
    
    public boolean getHeader(){
        return this.hasHeader;
    }
    
    public void setHeaderStrings(String[] strHeader){
        this.headerStrings = strHeader;
    }
    
    public String[] getHeaderStrings(){
        return this.headerStrings;
    }
    
    public String getHeaderStrings(int colIndex){
        return this.headerStrings[colIndex];
    }
    
    public void setColumnsCount(int number){
        this.columnsCount = number;
    }
    
    public int getColumnsCount(){
        return this.columnsCount;
    }
    
    public void setRowsCount(int count){
        this.rowsCount = count;
    }
    
    public int getRowsCount(){
        return this.rowsCount;
    }
}
