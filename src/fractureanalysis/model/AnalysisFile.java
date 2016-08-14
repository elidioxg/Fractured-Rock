package fractureanalysis.model;

import java.util.ArrayList;

/**
 *
 * @author elidioxg
 */
public class AnalysisFile extends CSVFile {

    //private int columnAp;
    //private int columnSp;

    private ArrayList<Double> ap;
    private ArrayList<Double> sp;

    //This variable defines the distance from first point
    private ArrayList<Double> distances;
    
    private void setDistance(ArrayList<Double> sp){
        double distance=0.;
        for (int i = 0; i < sp.size(); i++) {
            if (i == 0) {
                this.distances.add(distance);
            } else {                
                for (int j = 0; j < i; j++) {
                    distance+=this.sp.get(j);                    
                }
            }
            this.distances.add(distance);
            System.out.println(" **  Distances:    "+this.distances);
        }
    }
    
    public ArrayList<Double> getArrayDistances(){
        return this.distances;
    }

    public void setArrayAp(ArrayList<Double> values) {
        this.ap = values;
    }

    public ArrayList<Double> getArrayAp() {
        return this.ap;
    }

    public void setArraySp(ArrayList<Double> values) {
        this.sp = values;
        setDistance(values);
    }

    public ArrayList<Double> getArraySp() {
        return this.sp;
    }

    /*public void setColumnAp(int n) {
        this.columnAp = n;
    }

    public int getColumnAp() {
        return this.columnAp;
    }

    public void setColumnSp(int n) {
        this.columnSp = n;
    }

    public int getColumnSp() {
        return this.columnSp;
    }
*/
}
