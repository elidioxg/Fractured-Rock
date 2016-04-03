package fractureanalysis.model;

/**
 * 
 * @author elidioxg
 */
public class DatasetModel {
    
    public String name;
    //public int listIndex;
    public String filename;
    public String separator=",";
    public boolean header = true;
    public String[] headerStrings;
    
    public DatasetModel(String valueX, String valueY){
        
    }
    
    public DatasetModel(String name, String filename, String separator,
            boolean header, String[] headerStrings){
        this.name = name;
        this.filename=filename;   
        this.separator = separator;
        this.header = header;
        this.headerStrings = headerStrings;
    }      
    
    public String getFileName() {
        return this.filename;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public void setFilename(String filename){
        this.filename=filename;
    }
    
    public String getSeparator(){
        return this.separator;               
    }
    
    public void setSeparator(String sep){
        this.separator  = sep;
    }
    
    public boolean getHeader(){
        return this.header;               
    }
    
    public void setHeader(boolean header){
        this.header  = header;
    }
    
    public String[] getHeaderStrings(){
        return this.headerStrings;
    }
    
    public void setHeaderStrings(String[] header){
        this.headerStrings = header;
    }
}

