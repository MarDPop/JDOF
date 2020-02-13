package main.vehicle.component;

import main.util.Cartesian;

public abstract class Component {

    /**
     * Mass in kg
     */
    private double mass;

    /**
     * location in m
     */
    protected Cartesian location;

    public Component() {};

    public void setLocation(Cartesian location) {
        this.location = location;
    }

    public void setLocation(double x, double y, double z) {
        this.location = new Cartesian(x,y,z);
    }

    public Cartesian getLocation() {
        return this.location;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return this.mass;
    }

    /**
     * Changes mass by dm
     * @param dm
     */
    public void changeMass(double dm) {
        this.mass += dm;
    }

}