package main.vehicle.component;

import main.util.MyPair;
import main.util.aero.AeroCoefficients;

/**
 * Deprecated
 */
public class ControlSurface {
    protected MyPair<Double,Double> start;// set as Span,Chord
    protected MyPair<Double,Double> end;

    protected int type;

    public static final int SIMPLE = 1;
    public static final int AILERON = 2;
    public static final int SLAT = 3;
    public static final int SPOILER = 4;
    public static final int FLAP = 5;
    public static final int RUDDER = 6;

    private double deflection;
    private double maxDeflection;
    private double minDeflection;
    private double maxRate;

    public ControlSurface() {}

    public void set(MyPair<Double,Double> a, MyPair<Double,Double> b, int type) {
        this.start = a;
        this.end = b;
        this.type = type;
    }

    public AeroCoefficients getDeltaCoefficients() {
        AeroCoefficients C = new AeroCoefficients();
        return C;
    }

    public AeroCoefficients getDeltaCoefficients(double deflection) {
        AeroCoefficients C = new AeroCoefficients();
        return C;
    }

    public int getType() {
        return type;
    }

    public void setDeflection(double deflection) {
        this.deflection = deflection;
    }

    public double getDeflection() {
        return this.deflection;
    }

    public MyPair<Double, Double> getStart() {
        return start;
    }

    public MyPair<Double, Double> getEnd() {
        return end;
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