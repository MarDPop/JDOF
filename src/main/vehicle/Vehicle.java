package main.vehicle;

import java.util.ArrayList;
import main.util.*;
import main.vehicle.component.Component;
import main.vehicle.controller.Controller;
import main.util.aero.Aerodynamics;

/**
 * Class containing vehicular properties
 */
public class Vehicle extends Body {

    /**
     * Aerodynamic Properties (MAY BE REMOVED)
     */
    protected Aerodynamics aero;

    /**
     * List of controllers for vehicle (relevant for GNC)
     */
    public ArrayList<Controller> controllers = new ArrayList<Controller>();

    /**
     * List of all actions (force and moment on CG) relevant for accleration
     */
    public ArrayList<Action> actions = new ArrayList<Action>();

    /**
     * List of all components (item with mass and location) relevant for inertia
     */
    public ArrayList<Component> components = new ArrayList<Component>();

    // FOR TESTING ONLY 
    public TestController test = new TestController();

    /**
     * Empty constructor
     */
    public Vehicle() { }

    public void generateFromFile(String file) {

    }

    /**
     * Sets the aerodynamic properties of vehicle
     * @param aero
     */
    public void setAerodynamics(Aerodynamics aero) {
        this.aero = aero;
        this.actions.add(aero);
    }


}