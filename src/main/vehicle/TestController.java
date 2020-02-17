package main.vehicle;

public class TestController {

    double deflection, trimDeflection;
    public double dt;

    public TestController() {
        this.deflection=0;
    }

    public void setDeflection(double deflection) {
        this.deflection = deflection;
    }

    public double getDeflection() {
        return this.deflection;
    }

    public double changeDeflection(double d) {
        this.deflection += d;
        return this.deflection;
    }

    public double getElevatorDeflection(double altitude_desired, double altitude, double vertical_speed, double pitch_rate) {
        double desiredRateOfClimb = 0.005*(altitude_desired-altitude);
        if(desiredRateOfClimb > 6) {
            desiredRateOfClimb = 6;
        } else if(desiredRateOfClimb < -3) {
            desiredRateOfClimb = -3;
        }
        double delta = desiredRateOfClimb + vertical_speed;
        this.trimDeflection += 5e-5*delta*dt;
        this.deflection = this.trimDeflection-0.25*pitch_rate;
        saturationLimit();
        return this.deflection;
    }

    public double getElevatorDeflection(double vertical_speed, double vertical_acceleration, double pitch_rate) {
        // this.deflection = -0.0005*vertical_speed - 0.00001*vertical_acceleration - 0.001*pitch_rate;
        this.trimDeflection += 5e-5*vertical_speed*dt;
        this.deflection = this.trimDeflection-0.2*pitch_rate;
        saturationLimit();
        return this.deflection;
    }

    private void saturationLimit() {
        if (this.deflection > 25) {
            this.deflection = 25;
        }
        if (this.deflection < -25) {
            this.deflection = -25;
        }
    }

    public double getElevatorDeflection(double pitch_rate) {
        // this.deflection = -0.0005*vertical_speed - 0.00001*vertical_acceleration - 0.001*pitch_rate;
        this.deflection = -0.1*pitch_rate;
        saturationLimit();
        return this.deflection;
    }


}