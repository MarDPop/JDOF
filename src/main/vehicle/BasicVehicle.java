package main.vehicle;

import main.util.Cartesian;
import main.util.MyPair;
import main.util.aero.Aerodynamics;
import main.vehicle.component.Engine;

public class BasicVehicle extends Vehicle {

    public BasicVehicle(Aerodynamics aero, Engine engine) {
        this.aero = aero;
        this.engine = engine;
    }

    @Override
    public void computeActions() {
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
        this.acceleration.add(g.get());

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