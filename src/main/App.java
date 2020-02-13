package main;

import main.vehicle.Vehicle;
import main.vehicle.component.*;
import main.util.aero.TestAero;
import main.util.ode.ODE_Euler;
import main.util.*;

public class App {

    public static void main(final String[] args) throws Exception {
        if(args[0].equals("test")) {
            Test.runAll();
        }

        Vehicle v = new Vehicle();
        v.actions.add(new TestAero());
        v.actions.add(new Gravity(v));
        v.actions.add(new BasicEngine(v,10000));

        ODE_Euler ode = new ODE_Euler();
        ode.setBody(v);
        ode.setEndTime(10);
        ode.setStepSize(0.25);

        ode.run();
    }
}