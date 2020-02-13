package main.util.ode;

import main.util.Body;
import java.util.HashMap;

public abstract class ODE {

    // Time variables
    protected double time_end;
    protected double time;
    protected double dt;

    /**
     * Vehicle reference ODE acts on
     */
    protected Body body;

    /**
     * Options for ODE, key is option name
     */
    public HashMap<String,Object> options = new HashMap<String,Object>();

    public ODE() {}

    /**
     * Sets Vehicle
     * @param vehicle
     */
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Gets vehicle
     * @return vehicle
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * Gets time
     * @return
     */
    public double getTime() {
        return this.time;
    }

    /**
     * Sets the time
     * @param time
     */
    public void setEndTime(double time) {
        this.time = time;
    }

    /**
     * Gets time at which ode ends
     * @return
     */
    public double getEndTime() {
        return this.time_end;
    }

    /**
     * Sets the time at which ode ends
     * @param time_end
     */
    public void setTime(double time_end) {
        this.time_end = time_end;
    }

    /**
     * outputs the time step size
     * @return
     */
    public double getStepSize() {
        return this.dt;
    }

    /**
     * sets step size
     * @param dt
     */
    public void setStepSize(double dt) {
        this.dt = dt;
    }

    public void setup(){}

    public abstract void step();

    protected boolean nextStep() {
        // time step could be done here
        return time < time_end;
    }

    public void output() {}

    public final void run() {
        do {
            step();
            output();
        } while(nextStep());
    }
}