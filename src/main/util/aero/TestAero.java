package main.util.aero;

import main.util.aero.atmosphere.*;
import main.vehicle.*;

public class TestAero extends Aerodynamics {

    double K;

    public TestAero(Vehicle v) {
        this.atm = new BasicAtmosphere();
        this.vehicle = v;
    }

     @Override
    public void setReferences(double area, double span, double chord) {
        super.setReferences(area, span, chord);
        double AR = this.span_reference*this.span_reference/this.area_reference;
        this.K = 1.05/(3.142*AR); 
    }

    @Override
    public void calcAeroCoefficients() {
        double CL = 0.2 + 6*this.angleOfAttack;
        double CD = 0.05 + this.K*CL*CL;
        if(this.angleOfAttack > 0.25) {
            if(this.angleOfAttack < 1) {
                CL = 1.75-this.angleOfAttack*1.75;
            } else {
                CL = 0;
            }
        } 
        double CR = 0.1*this.sideSlipAngle;

        double CMy = -0.1*this.angleOfAttack;
        double CMz = -0.01*this.sideSlipAngle;
        double CMx = -0.0001*this.rollAngle;

        double dEl = 0;//this.vehicle.test.getElevatorDeflection(3000,vehicle.getPosition().z,vehicle.getVelocity().z,this.angleOfAttack );
        double dCMy = dEl*0.02;
        double dCL = dEl*-0.01;
        double dCD = dEl*0.005;

        this.C.set(0,CD+dCD);
        this.C.set(1,CR);
        this.C.set(2,CL+dCL);
        this.C.set(3,CMx);
        this.C.set(4,CMy+dCMy);
        this.C.set(5,CMz);

    }

}