package main.util.aero;

import main.util.aero.AeroCoefficients;;

public abstract class AeroControl {

    /**
     * 
     */
    protected double deflection;

    /**
     * 
     */
    protected double deflectionRate;

    /**
     * 
     */
    protected double maxDeflection;

    /**
     * 
     */
    protected double minDeflection;

    /**
     * 
     */
    protected double maxRate;

    public AeroControl() {}

    public abstract AeroCoefficients getDeltaCoefficients();

    public abstract AeroCoefficients getDeltaCoefficients(double deflection, double deflectionRate);

    public void setDeflection(double deflection) {
        this.deflection = deflection;
    }

    public double getDeflection() {
        return this.deflection;
    }

    public double changeDeflection(double delta) {
        return this.deflection += delta;
    }

    public void move(double dt) {
        this.deflection += this.deflectionRate*dt;
    }

    public double getMaxDeflection() {
        return maxDeflection;
    }

    public void setMaxDeflection(double maxDeflection) {
        this.maxDeflection = maxDeflection;
    }

    public double getMinDeflection() {
        return minDeflection;
    }

    public void setMinDeflection(double minDeflection) {
        this.minDeflection = minDeflection;
    }

    public double getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(double maxRate) {
        this.maxRate = maxRate;
    }

}