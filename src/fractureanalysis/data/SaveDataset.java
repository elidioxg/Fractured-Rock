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
package fractureanalysis.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveDataset {
    /**
     * Save ArrayList data as a CSV file
     * @param filename
     * @param separator
     * @param data 
     */
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
            
        }                    
    }        
    
}
