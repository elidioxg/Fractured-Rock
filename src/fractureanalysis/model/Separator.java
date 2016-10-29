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
package fractureanalysis.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author elidioxg
 */
public class Separator {

    private String[] strSep = {"Tab", "semicolon", "colon", "Blank space"};
    private final String[] sep = {"\t",";",","," "};
    private final int defaultSep = 0;
    private String current;
    private int currentIndex;

    public Separator() {
        this.current = sep[defaultSep];
        this.currentIndex = defaultSep;
    }

    public Separator(int index) {
        this.current = sep[index];
        this.currentIndex = index;
    }

    /**
     * Add a custom separator
     *
     * @param separator
     */
    public Separator(String separator) {
        
        boolean found = false;                
        String aux = sep[defaultSep];
        for (int i = 0; i < 4; i++) {
            if (separator== sep[i]) {                
                found = true;
                this.current = sep[i];
                this.currentIndex = i;
                break;
            }
            if (!found) {                
                this.currentIndex = -1;
            }
        }
        this.current = aux;
    }

    /**
     * Get the chars representing the separator
     * @return 
     */
    public String getChar() {
        System.out.println("***Current Separator:  "+this.current);
        return this.current;
    }

    /**
     * Get the name of separator(eg: Tab, semicolon) or the chars representing
     * the separator.
     * @return 
     */
    public String getString() {
        if (currentIndex >= 0) {
            return strSep[currentIndex];
        } else {
            return current;
        }
    }

}
