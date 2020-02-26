package main.vehicle;

import main.util.aero.BasicControl;
import main.util.aero.AeroCoefficients;
import main.util.aero.BasicAerodynamics;
import main.vehicle.component.BasicEngine;

public class BasicVehicle extends Vehicle {

    public static final int ELEVATOR = 0;
    public static final int AILERON = 1;
    public static final int RUDDER = 2;

    public BasicVehicle(AeroCoefficients nominal, double thrust) {
        BasicAerodynamics aero = new BasicAerodynamics();
        aero.nominal = new AeroCoefficients(nominal);

        aero.controls.add(new BasicControl());
        aero.controls.add(new BasicControl());
        aero.controls.add(new BasicControl());

        BasicEngine engine = new BasicEngine(this,thrust);

        this.aero = aero;
        this.engine = engine;
    }

    public BasicControl getElevator() {
        return ((BasicAerodynamics)aero).controls.get(0);
    }

    public BasicControl getAileron() {
        return ((BasicAerodynamics)aero).controls.get(1);
    }

    public BasicControl getRudder() {
        return ((BasicAerodynamics)aero).controls.get(2);
    }

}