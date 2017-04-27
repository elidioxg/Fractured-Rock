/*
 * Copyright (C) 2017 elidioxg
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
package fractureanalysis.util;

/**
 *
 * @author elidioxg
 */
public class ParseNumber {

    public static double parse(String number) {
        double result = Double.NaN;
        number = number.trim();
        if (number.isEmpty()) {            
            return result;
        }
        number = number.replace(",", ".").replace("\"", "");
        int count = number.length() - number.replace(".", "").length();
        if (count > 1) {           
            return result;
        }
        String aux[] = number.split("E");
        if (aux.length > 2) {            
            return result;
        }
        if (aux.length > 1) {            
            double power = 1;
            for(int i=0; i < Integer.parseInt(aux[1]); i++){
                power *= 10;
            }            
            if(Double.parseDouble(aux[1])<0){                
                result = Double.parseDouble(aux[0])/power;
            } else {
                result = Double.parseDouble(aux[0])*power;
            }            
        } else {            
            result = Double.parseDouble(number);
        }
        return result;
    }
}
