package main.util.aero;

public class TestAero extends Aerodynamics {

    @Override
    public void getAeroCoefficients() {
        double CL = 6*this.angleOfAttack;
        double CD = 0.2 + 0.002*CL*CL;
        if(this.angleOfAttack > 0.25) {
            if(this.angleOfAttack < 1) {
                CL = 1.75-this.angleOfAttack*1.75;
            } else {
                CL = 0;
            }
        } 
        double CR = 0.1*this.sideSlipAngle;

        double CMy = -0.01*this.angleOfAttack;
        double CMz = -0.01*this.sideSlipAngle;
        double CMx = -0.001*this.rollAngle;

        double dEl = this.vehicle.test.getElevatorDeflection(3000,vehicle.getPosition().z,vehicle.getVelocity().z,this.angleOfAttack );
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