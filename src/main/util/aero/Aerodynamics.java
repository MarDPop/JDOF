package main.util.aero;

import main.util.*;
import main.vehicle.Vehicle;
import main.util.aero.atmosphere.*;
import java.util.ArrayList;

public abstract class Aerodynamics implements Action {

    /**
     * Area Reference (m2)
     */
    protected double area_reference;

    /**
     * Span Reference (m)
     */
    protected double span_reference;

    /**
     * Chord Reference (m)
     */
    protected double chord_reference;

    /**
     * Aerodynamic Coefficients to comput forces based on freestream
     */
    protected AeroCoefficients C = new AeroCoefficients();

    /**
     * Atmosphere reference
     */
    protected Atmosphere atm;

    /**
     * List of control surfaces (relevant for aerodynamics)
     */
    public ArrayList<AeroControl> controls = new ArrayList<AeroControl>();

    /**
     * Vehicle reference
     */
    protected Vehicle vehicle;

    /**
     * Freestream parameters
     * (pressure in N/m2)
     * (angles in rad)
     */
    protected double dynamicPressure, angleOfAttack, sideSlipAngle, rollAngle, mach, reynolds;

    /**
     * Empty Constructor
     */
    public Aerodynamics () {}

    /**
     * Set vehicle reference
     * @param vehicle
     */
    public void setVehicleReference(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Set atmosphere reference
     * @param atm
     */
    public void setAtmosphere(Atmosphere atm) {
        this.atm = atm;
    }

    /**
     * Sets the references for coefficients
     * @param area
     * @param span
     * @param chord
     */
    public void setReferences(double area, double span, double chord){
        this.area_reference = area;
        this.span_reference = span;
        this.chord_reference = chord;
    }

    /**
     * Main function that computes / retrieves the aero coefficients should be overridden
     */
    protected abstract void calcAeroCoefficients();

    /**
     * gets the forces and moments
     */
    @Override
    public MyPair<Cartesian, Cartesian> getAction() {
        // Set atmosphere
        this.atm.setAltitude(-vehicle.getPosition().z); // remember vehicles use North East Down

        // Get Freestream
        Cartesian wind = atm.getWind();
        Cartesian freestream = new Cartesian(this.vehicle.getVelocity());
        if(wind != null) {
            freestream.x -= wind.x;
            freestream.y -= wind.y;
            freestream.z -= wind.z;
        }

        // Speed variables
        double airspeed = freestream.distance();
        this.dynamicPressure = 0.5*atm.getDensity()*airspeed*airspeed;
        this.mach = airspeed/atm.getSpeedOfSound();
        this.reynolds = chord_reference*atm.getDensity()*airspeed/atm.getDynamicViscosity();
        // Constant for coeffients
        double cons = this.dynamicPressure*this.area_reference;

        // Vector(s)
        Cartesian vector_lift = vehicle.getAxis().y.cross(freestream);
        vector_lift.normalize();

        // Angles
        // NOTE: Stability axis is defined as: y = body axis y (right), x = freestream, z = x cross y
        this.angleOfAttack = Math.asin(freestream.dot(vehicle.getAxis().z)/airspeed); // remember z is down
        this.sideSlipAngle = Math.asin(freestream.dot(vehicle.getAxis().y)/airspeed);
        this.rollAngle = Math.asin(vehicle.getAxis().y.z);

        calcAeroCoefficients();

        // Get forces
        double drag = cons*C.getCD();
        double side = cons*C.getCR();
        double lift = cons*C.getCL();

        // Convert Forces to Global Frame
        Cartesian forces = vector_lift.multiply(lift).plus(vehicle.getAxis().y.multiply(side)).minus(freestream.multiply(drag/airspeed));

        // Moments (in Body axis)
        Cartesian moments = new Cartesian();
        moments.x = cons*this.span_reference*C.getCMx();
        moments.y = cons*this.chord_reference*C.getCMy();
        moments.z = cons*this.span_reference*C.getCMz();

        return new MyPair<Cartesian, Cartesian>(forces,moments);
    }

    // Getters

    public AeroCoefficients getAeroCoefficients() {
        return this.C;
    }

    public double getAngleOfAttack() {
        return this.angleOfAttack;
    }

    public double getSideSlip() {
        return this.sideSlipAngle;
    }

    public double getMach() {
        return this.mach;
    }

    public double getReynolds() {
        return this.reynolds;
    }

    public double getDynamicPressure() {
        return this.dynamicPressure;
    }

}