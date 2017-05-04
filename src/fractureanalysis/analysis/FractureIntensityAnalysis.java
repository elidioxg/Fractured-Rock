package fractureanalysis.analysis;

import fractureanalysis.Matrices.Matrix;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FractureIntensityAnalysis {

    private double fractureIntensity = 0.;
    private double averageSpacing = 0.;
    private ArrayList<Fracture> cumulativeDistribution;
    private static Matrix apertureMatrix;
    private static Matrix sortMatrix;
    private static Matrix simpMatrix;
    private static Matrix cumMatrix;        

    /**
     * This constructor defines the Fracture Intensity and Average Spacing from
     * scanline data, also estimate the cumulative distribution of the
     * fractures.
     *
     * @param scanline
     * @throws java.lang.Exception
     */
    public FractureIntensityAnalysis(ScanLine scanline) throws Exception {
        setFractureIntensity(scanline.fracturesCount(),
                scanline.getLenght());
        setAverageSpacing(scanline.fracturesCount(),
                scanline.getLenght());
        estimateDistribution(generateFractures(scanline));        
    }

    public ArrayList<Fracture> getFracturesList(){
        return this.cumulativeDistribution;
    }

    /**
     * Fractures number and scanline lenght must be defined for Fracture
     * Intensity parameter. The Fracture Intensity parameter is divide fractures
     * number by the scanline lenght.
     *
     * @param fractures_number
     * @param scanlineLength
     */
    private void setFractureIntensity(int fractures_number,
            double scanlineLength) {
        this.fractureIntensity = fractures_number / scanlineLength;
    }

    /**
     * The Fracture Intensity parameter is defined on constructor.
     *
     * @return
     */
    public double getFractureIntensity() {
        return this.fractureIntensity;
    }

    /**
     * Set value of Average Spacing for this Analysis. The Average Spacing is
     * the reverse of Fracture Intensity.
     *
     * @param fractures_number
     * @param scanlineLength
     */
    private void setAverageSpacing(int fractures_number,
            double scanlineLength) {
        this.averageSpacing = scanlineLength / fractures_number;
    }

    /**
     * Get the Average Spacing of the fractures from the scanline. The Average
     * Spacing of the analysis is defined on constructor of this class.
     *
     * @return
     */
    public double getAverageSpacing() {
        return this.averageSpacing;
    }

    /**
     * This function returns a ArrayList with the Cumulative Distribution of the
     * fractures in the scanline defined on constructor of this class.
     *
     * @return
     */
    public ArrayList<Fracture> getArrayDistribution() {
        return this.cumulativeDistribution;
    }

    /**
     * Use the scanline to generate the list of fractures. This list will be
     * used to estime cumulative distribution of the fractures in the scanline
     * defined on constructor of this class.
     *
     * @param scanline
     * @return
     */
    private ArrayList<Fracture> generateFractures(ScanLine scanline) {
        ArrayList<Double> sp = scanline.getSpList();
        ArrayList<Double> ap = scanline.getApList();
        ArrayList<Fracture> array = new ArrayList();        
        for (int i = 0; i < scanline.fracturesCount(); i++) {
            Fracture frac = new Fracture(ap.get(i), sp.get(i));
            array.add(frac);
        }        
        return array;
    }

    /**
     * Follow some steps to estimate the cumulative distribution of the
     * fractures on scanlines. The scanline is parameter on constructor of this
     * class, and this procedure is called on construction.
     *
     * @param array List of fractures from scanline
     */
    private void estimateDistribution(ArrayList<Fracture> array) throws Exception {
        ArrayList<Fracture> sorted = listAndSort(array);       
        ArrayList<Fracture> simplified = simplify(sorted);                        
        ArrayList<Fracture> normalized = normalize(simplified);
        apertureMatrix = new Matrix(2, array.size());
        for(int i = 0; i < apertureMatrix.getLinesCount(); i++){
            apertureMatrix.set(0, i, sorted.get(i).getSpacement());
            apertureMatrix.set(1, i, sorted.get(i).getAperture());
        }
        sortMatrix = new Matrix(2, sorted.size());
        for(int i = 0; i < sortMatrix.getLinesCount(); i++){
            sortMatrix.set(0, i, sorted.get(i).getAperture());
            sortMatrix.set(1, i, sorted.get(i).getCumulativeNumber());
        }
        simpMatrix = new Matrix(2, simplified.size());
        for(int i = 0; i < simpMatrix.getLinesCount(); i++){
            simpMatrix.set(0, i, simplified.get(i).getAperture());
            simpMatrix.set(1, i, simplified.get(i).getCumulativeNumber());
        }
        cumMatrix = new Matrix(3, normalized.size());
        for (int i = 0; i < cumMatrix.getLinesCount(); i++) {
            cumMatrix.set(0, i, normalized.get(i).getAperture());
            cumMatrix.set(1, i, normalized.get(i).getCumulativeNumber());
            cumMatrix.set(2, i, normalized.get(i).getNormalizedValue());
        }        
        this.cumulativeDistribution = normalized;        
    }

    private ArrayList<Fracture> listAndSort(ArrayList<Fracture> array) {        
        Collections.sort(array, new ApertureComparator());        
        for (int i = 0; i < array.size(); i++) {
            array.get(i).setCumulativeNumber(i + 1);
        }
        return array;
    }

    private ArrayList<Fracture> simplify(ArrayList<Fracture> array) {
        ArrayList<Fracture> toRemove = new ArrayList<>();
        for (int i = 0; i < array.size() - 1; i++) {                        
            if (Double.compare(array.get(i).getAperture(), array.get(i + 1).getAperture())==0) {           
                toRemove.add(array.get(i));
            }
        }
        ArrayList<Fracture> result = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            if(!toRemove.contains(array.get(i))){
                result.add(array.get(i));
            }            
        }        
        return result;
    }

    private ArrayList<Fracture> normalize(ArrayList<Fracture> array) {
        double scanlineLenght = 0.;
        for (int i = 0; i < array.size(); i++) {
            scanlineLenght += array.get(i).getAperture();
        }
        for (int i = 0; i < array.size(); i++) {
            array.get(i).setNormalizedValue(array.get(i).getCumulativeNumber() / scanlineLenght);
        }
        return array;
    }
    
    public static Matrix getApertureTable(){
        return apertureMatrix;
    }
    
    public static Matrix getSortedTable(){
        return sortMatrix;
    }
    
    public static Matrix getSimplifiedMatrix(){
        return simpMatrix;
    }
    
    public static Matrix getNormalizedTable(){
        return cumMatrix;
    }        

    /**
     * Sort the Fractures by Aperture size, from bigger to smaller.
     */
    private class ApertureComparator implements Comparator<Fracture> {

        @Override
        public int compare(Fracture o1, Fracture o2) {
            return o1.getAperture() < o2.getAperture()
                    ? 1 : o1.getAperture() == o2.getAperture() ? 0 : -1;
        }
    }
}
