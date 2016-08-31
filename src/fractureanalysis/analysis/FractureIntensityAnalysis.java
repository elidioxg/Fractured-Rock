package fractureanalysis.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FractureIntensityAnalysis {

    private double fractureIntensity = 0.;
    private double averageSpacing = 0.;
    private ArrayList<Fracture> cumulativeDistribution;

    public FractureIntensityAnalysis(ScanLine scanline) {
        setFractureIntensity(scanline.fracturesCount(),                
                scanline.getLenght());
        setAverageSpacing(scanline.fracturesCount(),
                scanline.getLenght());
        estimateDistribution(generateFractures(scanline), scanline.getLenght());
    }

    private void setFractureIntensity(int fractures_number,
            double scanlineLength) {
        this.fractureIntensity = fractures_number / scanlineLength;
    }

    public double getFractureIntensity() {
        return this.fractureIntensity;
    }

    private void setAverageSpacing(int fractures_number,
            double scanlineLength) {
        this.averageSpacing = scanlineLength / fractures_number;
    }

    public double getAverageSpacing() {
        return this.averageSpacing;
    }
    
    public ArrayList<Fracture> getArrayDistribution(){
        return this.cumulativeDistribution;
    }

    private ArrayList<Fracture> generateFractures(ScanLine scanline){
        ArrayList<Double> sp = scanline.getSpList();
        ArrayList<Double> ap = scanline.getApList();
        ArrayList<Fracture> array = new ArrayList();
        for(int i=0; i< scanline.fracturesCount(); i++){
            Fracture frac = new Fracture(ap.get(i), sp.get(i));
            array.add(frac);
        }
        return array;
    }
    
    private void estimateDistribution(ArrayList<Fracture> array,
            double scanlineLenght) {
        //list
        Collections.sort(array, new ApertureComparator());
        //sort
        for (int i = 0; i < array.size(); i++) {       
            array.get(i).setCumulativeNumber(i + 1);
        }
        //simplify
        for (int i = 0; i < array.size() - 1; i++) {
            if (array.get(i).getAperture() == array.get(i + 1).getAperture()) {
                array.remove(i);
            }
        }
        //normalize
        for (int i = 0; i < array.size(); i++) {
            array.get(i).setNormalizedValue(array.get(i).getCumulativeNumber() / scanlineLenght);
        }
        this.cumulativeDistribution = array;
    }

    private class ApertureComparator implements Comparator<Fracture> {
        @Override
        public int compare(Fracture o1, Fracture o2) {
            return o1.getAperture() < o2.getAperture() ? 
                    -1 : o1.getAperture() == o2.getAperture() ? 0 : 1;
        }
    }
}
