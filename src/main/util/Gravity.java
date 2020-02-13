package main.util;

public class Gravity implements Action {

    public DoubleValue mass;

    @Override
    public MyPair<Cartesian, Cartesian> getAction() {
        return new MyPair<Cartesian, Cartesian>(new Cartesian(0,0,mass.val*-9.806),new Cartesian());
    }
    
}