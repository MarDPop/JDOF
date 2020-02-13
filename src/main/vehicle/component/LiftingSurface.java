package main.vehicle.component;

import java.util.ArrayList;
import main.util.*;

/**
 * For a component that adds action
 */
public class LiftingSurface extends Component implements Action {

    public Geometry geometry;
    public ArrayList<ControlSurface> controls;

    public LiftingSurface() {}

    @Override
    public MyPair<Cartesian, Cartesian> getAction() {
        return new MyPair<Cartesian, Cartesian>(new Cartesian(),new Cartesian());
    }

}