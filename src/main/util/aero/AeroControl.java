package main.util.aero;

import main.util.DoubleValue;
import main.util.aero.AeroCoefficients;;

public abstract class AeroControl {

    /**
     * 
     */
    protected DoubleValue deflection;

    /**
     * 
     */
    protected DoubleValue deflectionRate;

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

    public void setDeflection(double deflection) {
        this.deflection.val = deflection;
    }

    public double getDeflection() {
        return this.deflection.val;
    }

    public double changeDeflection(double delta) {
        return this.deflection.val += delta;
    }

    public void move(double dt) {
        this.deflection.val += this.deflectionRate.val*dt;
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