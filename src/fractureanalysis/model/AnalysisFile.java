package fractureanalysis.data;

import java.util.ArrayList;

/**
 *
 * @author elidioxg
 */
public class AnalysisFile extends CSVFile {
    
    private int columnAp;
    private int columnSp;
    
    private ArrayList<Double> ap;
    private ArrayList<Double> sp;

    public void setArrayAp(ArrayList<Double> values){
        this.ap = values;
    }
    
    public ArrayList<Double> getArrayAp(){
        return this.ap;
    }
    
    public void setArraySp(ArrayList<Double> values){
        this.sp = values;
    }
    
    public ArrayList<Double> getArraySp(){
        return this.sp;
    }
    
    public void setColumnAp(int n){
        this.columnAp = n;
    }
    public int getColumnAp(){
        return this.columnAp;
    }
    
    public void setColumnSp(int n){
        this.columnSp = n;
    }
    public int getColumnSp(){
        return this.columnSp;
    }
}
