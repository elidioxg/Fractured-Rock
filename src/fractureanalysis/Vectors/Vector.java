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
package fractureanalysis.Vectors;

/**
 *
 * @author elidioxg
 */
public class Vector {

    private int size = 0;
    private Number[] data = null;

    public Vector() {

    }

    public Vector(Number[] data, int size) {
        this.data = data;
        this.size = size;
    }

    public Vector(int size) {
        this.data = new Number[size];
        for (int i = 0; i < size; i++) {
            final int ii = i;
            data[i] = new Number() {
                @Override
                public int intValue() {
                    return (int) data[ii];
                }

                @Override
                public long longValue() {
                    return (long) data[ii];
                }

                @Override
                public float floatValue() {
                    return (float) data[ii];
                }

                @Override
                public double doubleValue() {
                    return (double) data[ii];
                }
            };
        }
    }

    public void setData(Number[] data, int size) {
        this.data = data;
        this.size = size;
    }
    
    public void set(int index, double value) throws Exception{
        if(!this.data.equals(null)){
            if(index<this.data.length){
                this.data[index] = value;
            } else {
                throw new Exception("Index higher than vector size");
            }
        } else {
            throw new Exception("Null vector data");
        }
        
    }

    public Number get(int index) {
        return this.data[index];
    }

    public int size() {
        return this.size;
    }

    public void add(double value) {
        Number[] vector = new Number[this.size++];
        System.arraycopy(this.data, 0, vector, 0, this.size - 2);
        final int ii = this.size;
        vector[this.size] = new Number() {
            @Override
            public int intValue() {
                return (int) vector[ii];
            }

            @Override
            public long longValue() {
                return (long) vector[ii];
            }

            @Override
            public float floatValue() {
                return (float) vector[ii];
            }

            @Override
            public double doubleValue() {
                return (double) vector[ii];
            }
        };
        vector[this.size] = value;
        this.data = vector;
    }

    public void sort() {        
        boolean finished = false;        
        while (!finished) {
            int counter = 0;
            for (int i = 1; i < this.data.length; i++) {
                if (this.data[i-1].doubleValue()<this.data[i].doubleValue()) {
                    Double temp = this.data[i-1].doubleValue();
                    this.data[i-1] = this.data[i].doubleValue();
                    this.data[i] = temp;
                    counter++;
                }
            }
            if(counter == 0){
                finished = true;
            }
        }
    }
    
    public double sum(){
        double result = 0.;
        for(int i=0; i< this.data.length; i++){
            result+=this.data[i].doubleValue();
        }
        return result;
    }

}
