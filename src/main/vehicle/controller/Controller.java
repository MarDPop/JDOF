package main.vehicle.controller;

import java.util.Map;

import main.util.integrator.Integrator;

public abstract class Controller {

    public Map<String,Object> options;

    protected Integrator ode;

    public Controller() {}

    public void setOptions(Map<String,Object> options) {
        this.options = options;
    }

    /**
     * Need to set inputs and outputs (perhaps this has a better name)
     */
    public abstract void update();

    public void setIntegrator(Integrator ode) {
        this.ode = ode;
    }
}