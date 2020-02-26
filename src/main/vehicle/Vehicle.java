package main.vehicle;

import main.util.*;
import main.vehicle.controller.*;
import main.util.aero.Aerodynamics;
import main.util.aero.atmosphere.Atmosphere;
import main.vehicle.component.*;

/**
 * Class containing vehicular properties Vehicles use NORTH EAST DOWN frame as a reference
 */
public class Vehicle extends Body {

    /**
     * Aerodynamic Properties (MAY BE REMOVED)
     */
    protected Aerodynamics aero;

    /**
     * Engine
     */
    protected Engine engine;

    /**
     * List of controllers for vehicle (relevant for GNC)
     */
    protected VehicleControl gnc;

    // FOR TESTING ONLY 
    public TestController test = new TestController();

    /**
     * Empty constructor
     */
    public Vehicle() {  }

    public void generateFromFile(String file) { }

    /**
     * Sets the aerodynamic properties of vehicle
     * @param aero
     */
    public void setAerodynamics(Aerodynamics aero) {
        this.aero = aero;
    }

    public Aerodynamics getAero(){
        return this.aero;
    }

    /**
     * Sets the aerodynamic properties of vehicle
     * @param aero
     */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine(){
        return this.engine;
    }

    /**
     * Sets the controller for vehicle
     * @param controller
     */
    public void setGNC(VehicleControl gnc) {
        this.gnc = gnc;
    }

    public VehicleControl getGNC(){
        return this.gnc;
    }

    @Override
    public void computeActions() {
        // Controls
        gnc.update();

        // Aerodynamics
        MyPair<Cartesian,Cartesian> components = aero.getAction();
        this.acceleration.set(components.a);
        this.angularAccleration.set(components.b);

        // Engine
        components = engine.getAction();
        this.acceleration.add(components.a);
        this.angularAccleration.add(components.b);
        
        this.acceleration.scale(1/this.mass);
        // Add gravity
        this.acceleration.z += Atmosphere.STD_GRAVITY;

        // Generally valid assumption here that inertia change isn't a factor (missing dI/dt * omega) this needs to be overridden if not the case
        // Remember this is in body frame right now, Euler angles are in Global North East Down frame
        this.angularAccleration = invInertia.multiply(this.angularAccleration);
        
        // Convert to global frame
        Cartesian x_component = this.axis.x.multiply(this.angularAccleration.x);
        Cartesian y_component = this.axis.y.multiply(this.angularAccleration.y);
        Cartesian z_component = this.axis.z.multiply(this.angularAccleration.z);
        this.angularAccleration.x = x_component.x + y_component.x + z_component.x;
        this.angularAccleration.y = x_component.y + y_component.y + z_component.y;
        this.angularAccleration.z = x_component.z + y_component.z + z_component.z;
    }
}