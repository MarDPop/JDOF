package main.util;

import main.util.Property;

/**
 * Class to hold a state of any object. (must be numerical)
 */
public class State {

    public Property<?>[] variables;

    public void add(Property<?> p) throws Exception {
        for(Property<?> q : variables) {
            if(q.name.equals(p)){
                throw new Exception("state property name already exists");
            }
        }
        Property<?>[] newarray = new Property<?>[this.variables.length+1];
        System.arraycopy(this.variables, 0, newarray, 0, this.variables.length);
        newarray[this.variables.length] = p;
        this.variables = newarray;
    }

    public Property<?> get(String name) throws Exception {
        for(Property<?> q : variables) {
            if(q.name.equals(name)){
                return q;
            }
        }
        throw new Exception("state property name not found");
    }

}