package main.util.aero;

import java.util.ArrayList;

public class BasicAerodynamics extends Aerodynamics {

    public AeroCoefficients nominal = new AeroCoefficients();

    public ArrayList<BasicControl> controls = new ArrayList<>();

    @Override
    protected void calcAeroCoefficients() {
        this.C = new AeroCoefficients(nominal);
        for(BasicControl c : this.controls) {
            this.C.add(c.getDeltaCoefficients());
        }
    }

}