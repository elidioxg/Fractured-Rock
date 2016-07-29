package fractureanalysis.model;

public class CSVFile {
    private String datasetName = "";
    private String datasetFilename = "";
    private String separator = ",";
    private boolean hasHeader = true;
    private String[] headerStrings;     
    private int columnsNumber = 0;
    
    public void CSVFile(){
    
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
    
    public void setColumnsNumber(int number){
        this.columnsNumber = number;
    }
    
    public int getColumnsNumber(){
        return this.columnsNumber;
    }
}
