package main.vehicle.component;

import main.util.Action;

public abstract class Engine extends Component implements Action {

    /**
     * Throttle (0-1.0)
     */
    protected double throttle;

    /**
     * Current fuel rate (in kg/s)
     */
    protected double fuelRate;

    /**
     * Thrust of engine (in Newtons)
     */
    protected double thrust;

    public Engine() {}

    /**
     * @return the throttle
     */
    public double getThrottle() {
        return throttle;
    }

    /**
     * @param throttle the throttle to set
     */
    public void setThrottle(double throttle) {
        if(throttle > 1.0) {
            this.throttle = 1.0;
        } else if (throttle < 0.0) {
            this.throttle = 0.0;
        } else {
            this.throttle = throttle;
        }
    }

    /**
     * @return the fuelRate
     */
    public double getFuelRate() {
        return fuelRate;
    }

    /**
     * @param fuelRate the fuelRate to set
     */
    public void setFuelRate(double fuelRate) {
        this.fuelRate = fuelRate;
    }

    /**
     * @return the thrust
     */
    public double getThrust() {
        return thrust;
    }

    /**
     * @param thrust the thrust to set
     */
    public void setThrust(double thrust) {
        this.thrust = thrust;
    }

}