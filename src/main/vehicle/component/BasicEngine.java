package main.vehicle.component;

import main.util.Cartesian;
import main.util.MyPair;
import main.vehicle.Vehicle;

public class BasicEngine extends Engine {

    /**
     * Reference to body axis
     */
    private Cartesian axis;

    private double maxThrust;

    public BasicEngine(Vehicle v, double maxThrust) {
        this.axis = v.getAxis().x;
        this.throttle = 1.0;
    }

    @Override
    public MyPair<Cartesian, Cartesian> getAction() {
        return new MyPair<Cartesian, Cartesian>(axis.multiply(this.maxThrust*this.throttle),new Cartesian());
    }

}