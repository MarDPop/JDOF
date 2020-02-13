package main.vehicle.component;

import main.util.Action;
import main.util.Cartesian;
import main.util.MyPair;

public abstract class Engine extends Component implements Action {

    /**
     * Throttle (0-1.0)
     */
    protected double throttle;

    /**
     * Current fuel rate
     */
    protected double fuelRate;

    /**
     * Thrust of engine
     */
    protected double thrust;

    public Engine() {}

    @Override
    public MyPair<Cartesian, Cartesian> getAction() {
        return new MyPair<Cartesian, Cartesian>(new Cartesian(),new Cartesian());
    }

}