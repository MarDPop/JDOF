package main.vehicle.controller;

import main.util.DoubleValue;

public abstract class SISO extends Controller {

    protected DoubleValue input, output;

    public SISO(){}

    public void setInput(DoubleValue input) {
        this.input = input;
    }

    public void setOutput(DoubleValue output) {
        this.output = output;
    }

}