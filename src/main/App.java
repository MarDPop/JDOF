package main;

import javax.swing.*;

import main.vehicle.Vehicle;
import main.vehicle.component.*;
import main.util.aero.Aerodynamics;
import main.util.aero.TestAero;
import main.util.integrator.ODE_Euler;

import main.util.*;
import main.view.*;

public class App {
    
    public static final String VERSION_NO = "0.1.0";

    static MainFrame gui;

    public static void main(final String[] args) throws Exception {
        if(args[0].equals("test")) {
            Test.runAll();
        }

        gui = new MainFrame(VERSION_NO);

        gui.setSize(1200,800);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);

        Vehicle v = new Vehicle();
        Aerodynamics aero = new TestAero(v);
        aero.setReferences(1, 2, 0.5);

        Axis inertia = new Axis(new double[][]{{100,0,0},{0,100,0},{0,0,100}});
        v.setInertia(40, inertia);
        v.setPosition(new Cartesian(0,0,2000));
        v.setVelocity(new Cartesian(100,0,0));
        
        v.setAerodynamics(aero);
        v.addAction(new BasicEngine(v,100));
        
        ODE_Euler ode = new ODE_Euler();
        ode.setBody(v);
        ode.setEndTime(100);
        ode.setStepSize(0.05);

        ode.run();

        java.util.ArrayList<Double> x = new java.util.ArrayList<>();
        java.util.ArrayList<Double> y = new java.util.ArrayList<>();
        for(double[] arr : ode.record.data){
            x.add(arr[1]);
            y.add(arr[3]);
        }

        java.util.HashMap<String,Object> o = new java.util.HashMap<>();
        o.put("Title","Path");
        JPlot plot = new JPlot(x,y,o);
    }
}