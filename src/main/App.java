package main;

import main.vehicle.Vehicle;
import main.util.aero.TestAero;
import main.util.ode.ODE_Euler;

public class App {

    public static void main(final String[] args) throws Exception {
        if(args[0].equals("test")) {
            Test.runAll();
        }

        Vehicle v = new Vehicle();
        v.actions.add(new TestAero());

        ODE_Euler ode = new ODE_Euler();
        ode.setBody(v);
    }
}