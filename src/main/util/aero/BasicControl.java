package main.util.aero;

public class BasicControl extends AeroControl {

    private AeroCoefficients delta;

    public BasicControl(){};

    /**
     * @param delta the delta to set
     */
    public void setDelta(AeroCoefficients delta) {
        this.delta = delta;
    }

    public AeroCoefficients getDelta() {
        return this.delta;
    }

    @Override
    public AeroCoefficients getDeltaCoefficients() {
        return delta.multiply(this.deflection.val);
    }

}