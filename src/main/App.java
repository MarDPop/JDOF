package main;

import javax.swing.*;
import java.awt.Color;

import main.vehicle.Vehicle;
import main.vehicle.component.*;
import main.util.aero.Aerodynamics;
import main.util.aero.TestAero;
import main.util.integrator.ODE_Euler;

import main.util.*;
import main.view.*;

public class App {
    
    public static final String VERSION_NO = "0.1.1";

    static MainFrame gui;

    public static void main(final String[] args) throws Exception {
        if(args[0].equals("test")) {
            Test.runAll();
        }
        /*
        gui = new MainFrame(VERSION_NO);

        gui.setSize(1200,800);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        */

        Vehicle v = new Vehicle();
        Aerodynamics aero = new TestAero(v);
        aero.setReferences(1, 2, 0.5);

        Axis inertia = new Axis(new double[][]{{100,0,0},{0,18,0},{0,0,100}});
        v.setInertia(25, inertia);
        v.setPosition(new Cartesian(0,0,-2000));
        v.setVelocity(new Cartesian(50,0,0));
        
        v.setAerodynamics(aero);
        v.addAction(new BasicEngine(v,68));
        
        ODE_Euler ode = new ODE_Euler();
        ode.setBody(v);
        ode.setEndTime(120);
        ode.setStepSize(0.01);

        ode.run();

        java.util.ArrayList<Double> x = new java.util.ArrayList<>();
        java.util.ArrayList<Double> h = new java.util.ArrayList<>();
        java.util.ArrayList<Double> speed = new java.util.ArrayList<>();
        for(double[] arr : ode.record.data){
            x.add(arr[1]);
            h.add(-arr[3]);
            speed.add(Math.sqrt(arr[4]*arr[4]+arr[5]*arr[5]+arr[6]*arr[6]));
        }

        java.util.HashMap<String,Object> o = new java.util.HashMap<>();
        o.put("Title","Path");
        
        JPlot plot = new JPlot(x,h,o);
        /*
        java.util.HashMap<String,Object> o2 = new java.util.HashMap<>();
        o.put("Color",Color.GREEN);
        plot.addPlot(x, speed, o2);
        */
    }
}