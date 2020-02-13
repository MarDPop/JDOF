package main.vehicle;

import main.util.*;
import java.util.HashMap;

/**
 * Class to hold a state of any object. (must be numerical)
 */
public class State {

    /**
     * Array of axis 
     */
    public Axis[] axis;

    /**
     * Array of cartesian vectors (Cartesian is already object so already a reference)
     */
    public Cartesian[] vectors;

    /**
     * Array of state double (float) values
     */
    public DoubleValue[] properties;

    /**
     * Array of state integer values (typically indexes)
     */
    public IntegerValue[] indexes;

    /**
     * Array of state boolean values (typically flags)
     */
    public BooleanValue[] flags;

    /**
     * this hashmap is to "name" state objects. 
     * NOTE: while this can be used to actually retrieve
     * the state variables the issue is that you have two performance 
     * hits vs java array: one on retrieval and one on explicit cast.
     * Since it is intended to "hardcode" the state linking anyway,
     * indexes must be explicitly known anyway.
     */
    public final HashMap<String,Object> variables = new HashMap<String,Object>();

    /**
     * Time 
     */
    private double time;

    /**
     * Empty Contructor
     */
    public State() {}

    /**
     * Copy constructor
     * @param b
     */
    public State(State b) {

    }

    // Resize 
    public void add(Cartesian obj, String name) throws Exception {
        if(variables.containsKey(name)){
            throw new Exception("state property name already exists");
        }
        variables.put(name,obj);
        Cartesian[] newarray = new Cartesian[this.vectors.length+1];
        System.arraycopy(this.vectors, 0, newarray, 0, this.vectors.length);
        newarray[this.vectors.length] = obj;
        this.vectors = newarray;
    }

    // Resize 
    public void add(Axis obj, String name) throws Exception {
        if(variables.containsKey(name)){
            throw new Exception("state property name already exists");
        }
        variables.put(name,obj);
        Axis[] newarray = new Axis[this.axis.length+1];
        System.arraycopy(this.axis, 0, newarray, 0, this.axis.length);
        newarray[this.axis.length] = obj;
        this.axis = newarray;
    }

    // Resize 
    public void add(DoubleValue obj, String name) throws Exception {
        if(variables.containsKey(name)){
            throw new Exception("state property name already exists");
        }
        variables.put(name,obj);
        DoubleValue[] newarray = new DoubleValue[this.properties.length+1];
        System.arraycopy(this.properties, 0, newarray, 0, this.properties.length);
        newarray[this.properties.length] = obj;
        this.properties = newarray;
    }

    // Resize 
    public void add(IntegerValue obj, String name) throws Exception {
        if(variables.containsKey(name)){
            throw new Exception("state property name already exists");
        }
        variables.put(name,obj);
        IntegerValue[] newarray = new IntegerValue[this.indexes.length+1];
        System.arraycopy(this.indexes, 0, newarray, 0, this.indexes.length);
        newarray[this.indexes.length] = obj;
        this.indexes = newarray;
    }

    // Resize 
    public void add(BooleanValue obj, String name) throws Exception {
        if(variables.containsKey(name)){
            throw new Exception("state property name already exists");
        }
        variables.put(name,obj);
        BooleanValue[] newarray = new BooleanValue[this.flags.length+1];
        System.arraycopy(this.flags, 0, newarray, 0, this.flags.length);
        newarray[this.flags.length] = obj;
        this.flags = newarray;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return this.time;
    }

}