package main.util;

public class Gravity {

    public Gravity() { }
    
    public Cartesian get() {
        return new Cartesian(0,0,9.806); // NOTE!!! DEFAULT gravity assumes north east down
    }
}