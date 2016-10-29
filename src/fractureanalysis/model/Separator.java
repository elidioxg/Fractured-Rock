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

/**
 *
 * @author elidioxg
 */
public class Separator {

    private String[] strSep = {"colon", "semicolon", "TAB", "Blank space"};
    private String[] sep = {"\t", ";", ",", " "};
    private final int defaultSep = 2;
    private String current;
    //private int currentIndex;

    public Separator() {
        this.current = strSep[defaultSep];
        //this.currentIndex = defaultSep;
    }

    public Separator(int index) {
        this.current = strSep[index];
        //this.currentIndex = index;
    }

    /**
     * Add a custom separator
     *
     * @param sep
     */
    public Separator(String sep) {
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            if (sep.trim() == strSep[i]) {
                this.current = strSep[i];
                found = true;
                //this.currentIndex = i;
                break;
            }
            if (!found) {
                this.current = "sep";
                //this.currentIndex = -1;
            }
        }
    }

    public void setSep(String sep) {
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            if (sep.trim() == strSep[i]) {
                this.current = strSep[i];
                found = true;
                //this.currentIndex = i;
                break;
            }
            if (!found) {
                this.current = "sep";
                //this.currentIndex = -1;
            }
        }

    }

    public String getSep() {
        return this.current;
    }

}
