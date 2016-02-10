/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractureanalysis.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveDataset {
    private void saveData(String filename, String separator, ArrayList[][] data){
        String header = "index"+separator+"fracture_size"+separator+
                "cumulative_number"+separator+"number_fractures/scanline_length\n";
        try {            
            FileWriter writer = new FileWriter(filename, false);
            writer.append(header);
            for(int i = 0; i< data.length-1; i++){
                String lineValues = String.valueOf(data[i][0].get(0))+
                        separator+String.valueOf(data[i][1].get(0))+
                                separator+String.valueOf(data[i][2].get(0))+
                                        separator+String.valueOf(data[i][3].get(0)+"\n");                
                writer.append(lineValues);
            }
            //adds the last value:
            String lineValue = String.valueOf(data[data.length-1][0].get(0))+
                        separator+String.valueOf(data[data.length-1][1].get(0))+
                                separator+String.valueOf(data[data.length-1][2].get(0))+
                                        separator+String.valueOf(data[data.length-1][3].get(0));                
            writer.append(lineValue);
            writer.flush();
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }                    
    }        
    
}
