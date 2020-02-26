package main.vehicle.controller;

import java.util.ArrayList;

public abstract class VehicleControl extends Controller {

    public ArrayList<Controller> controls = new ArrayList<>();

    public VehicleControl() {}

    @Override
    public void update() {
        for(Controller c : this.controls) {
            c.update();
        }
    }

    public abstract void holdAll();

    public abstract void holdAltitude(double altitude);

    public abstract void holdHeading(double heading);

}