package main.util;

public class Gravity implements Action {

    private Body body;

    public Gravity(Body body) {
        this.body = body;
    }

    @Override
    public MyPair<Cartesian, Cartesian> getAction() {
        return new MyPair<Cartesian, Cartesian>(new Cartesian(0,0,body.getMass()*-9.806),new Cartesian());
    }
    
}