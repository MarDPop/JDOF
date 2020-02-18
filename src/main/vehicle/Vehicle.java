package main.vehicle;

import java.util.ArrayList;
import main.util.*;
import main.vehicle.controller.*;
import main.util.aero.Aerodynamics;
import main.util.aero.atmosphere.Atmosphere;
import main.util.integrator.Integrator;
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
    public ArrayList<Controller> controllers = new ArrayList<Controller>();

    /**
     * List of all components (item with mass and location) relevant for inertia
     */
    public ArrayList<Component> components = new ArrayList<Component>();

    // FOR TESTING ONLY 
    public TestController test = new TestController();

    /**
     * Empty constructor
     */
    public Vehicle() {  }

    public void generateFromFile(String file) {

    }

    /**
     * Sets the aerodynamic properties of vehicle
     * @param aero
     */
    public void setAerodynamics(Aerodynamics aero) {
        this.aero = aero;
        addAction(aero);
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
        addAction(engine);
    }

    public Engine getEngine(){
        return this.engine;
    }

    @Override
    public void computeActions() {
        // Controls
        for(Controller c : this.controllers) {
            c.update();
        }

        // Aerodynamics
        MyPair<Cartesian,Cartesian> components = aero.getAction();
        this.acceleration.set(components.a);
        this.angularAccleration.set(components.b);

        // Engine
        components = engine.getAction();
        this.acceleration.set(components.a);
        this.angularAccleration.set(components.b);
        
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

    /**
     * 
     * @param ode
     */
    public void setIntegrator(Integrator ode) {
        this.ode = ode;
        ode.setBody(this);
        for(Controller c : this.controllers) {
            c.setIntegrator(ode);
        }
    }

    @Override
    public void setAxisFromAngles() {
        double c1 = Math.cos(this.angles.z);
        double s1 = Math.sin(this.angles.z);
        double c2 = Math.cos(this.angles.y);
        double s2 = Math.sin(this.angles.y);
        double c3 = Math.cos(this.angles.x);
        double s3 = Math.sin(this.angles.x);
        this.axis.x.x = c1*c2;
        this.axis.y.x = c1*s2*s3-c3*s1;
        this.axis.z.x = s1*s3+c1*c3*s2;
        this.axis.x.y = c2*s1;
        this.axis.y.y = c1*c3+s1*s2*s3;
        this.axis.z.y = c3*s1*s2-c1*s3;
        this.axis.x.z = -s2;
        this.axis.y.z = c2*s3;
        this.axis.z.z = c2*c3;
    }

}