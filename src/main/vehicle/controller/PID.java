package main.vehicle.controller;

public class PID extends SISO {

    private double proportional, derivative, integration;

    private double oldval = 0; 
    private double rate = 0;
    private double sum = 0;

    public PID() {}

    public void setConstants(double proportional, double derivative, double integration) {
        this.proportional = proportional;
        this.derivative = derivative;
        this.integration = integration;
    }

    @Override
    public void update() {
        this.rate = (input.val-oldval)/this.ode.getStepSize();
        this.sum = input.val*this.ode.getStepSize();
        output.val = this.proportional*input.val + this.derivative*this.rate + this.integration*this.sum;
        this.oldval = input.val;
    }

    public double getRate() {
        return this.rate;
    }

    public double getSum() {
        return this.sum;
    }
}