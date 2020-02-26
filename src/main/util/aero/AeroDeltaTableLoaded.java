package main.util.aero;

/**
 * Class to describe increment to aerodynamic coefficients based on single input
 */
public class AeroDeltaTableLoaded {

    final int n;

    /**
     * values of variable of interest. Index corresponds to index of Coefficients.
     */
    public final double[] variable;

    /**
     * Coefficients representing an increment value for a given value 
     */
    public final AeroCoefficients[] coefficientDeltas;
    
    /**
     * Empty constructor
     */
    public AeroDeltaTableLoaded(AeroDeltaTable table){
        this.n = table.variable.size();
        this.variable = new double[n];
        this.coefficientDeltas = new AeroCoefficients[n];
        for(int i = 0; i < n; i++) {
            this.variable[i] = table.variable.get(i);
            this.coefficientDeltas[i] = table.coefficientDeltas.get(i);
        }
        table = null; // clear memory
    }

    /**
     * Calculates the coefficient increment at a given value using linear interpolation
     * @param value
     * @return
     */
    public AeroCoefficients calcDelta(double value) {
        // shouldn't be too many items so serial sort isn't half bad
        int i = 0;
        while(i < variable.length) {
            if(value < variable[i++]) {
                break;
            }
        }
        if(i == 0) {
            return new AeroCoefficients(coefficientDeltas[0]);
        } 
        if(i < n) {
            // this could be micro optimized
            AeroCoefficients delta = coefficientDeltas[i].subtract(coefficientDeltas[i-1]);
            delta.scale((value - variable[i-1])/(variable[i]-variable[i-1]));
            return coefficientDeltas[i-1].plus(delta);
        } else {
            return new AeroCoefficients(coefficientDeltas[i-1]);
        }
    }
}