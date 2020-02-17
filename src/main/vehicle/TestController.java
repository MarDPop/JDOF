package main.vehicle;

public class TestController {

    double deflection;

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

    public double getElevatorDeflection(double altitude_desired, double altitude, double vertical_speed, double angle_of_attack) {
        double desiredRateOfClimb = 0.01*(altitude_desired-altitude);
        double aoa = angle_of_attack*57.2958;
        if(desiredRateOfClimb > 5) {
            desiredRateOfClimb = 5;
        } else if(desiredRateOfClimb < -3) {
            desiredRateOfClimb = -3;
        }
        double delta = desiredRateOfClimb - vertical_speed;
        if(aoa < 10) {
            this.deflection += 0.5*delta;
        } else {
            this.deflection = 10-aoa;
        }
        saturationLimit();
        return this.deflection;
    }

    public double getElevatorDeflection(double vertical_speed, double vertical_acceleration, double pitch_rate) {
        // this.deflection = -0.0005*vertical_speed - 0.00001*vertical_acceleration - 0.001*pitch_rate;
        this.deflection = -0.001*pitch_rate;
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