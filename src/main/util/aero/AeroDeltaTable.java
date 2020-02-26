package main.util.aero;

import java.util.ArrayList;

/**
 * Class to describe increment to aerodynamic coefficients based on single input
 */
public class AeroDeltaTable {

    /**
     * values of variable of interest. Index corresponds to index of Coefficients.
     */
    public final ArrayList<Double> variable = new ArrayList<Double>();

    /**
     * Coefficients representing an increment value for a given value 
     */
    public final ArrayList<AeroCoefficients> coefficientDeltas = new ArrayList<AeroCoefficients>();
    
    /**
     * Empty constructor
     */
    public AeroDeltaTable(){}

    /**
     * Add a set of Coefficent deltas for a given value
     * @param parameter
     * @param coef
     */
    public void add(double parameter, AeroCoefficients coef) {
        // shouldn't be too many items so serial sort isn't half bad
        boolean found = false;
        for(int i = 0; i < variable.size(); i++) {
            if(parameter < variable.get(i)) {
                variable.add(i,parameter);
                coefficientDeltas.add(i,coef);
                found = true;
            }
        }
        if(!found) {
            variable.add(parameter);
            coefficientDeltas.add(coef);
        }
    }

    /**
     * Calculates the coefficient increment at a given value using linear interpolation
     * @param value
     * @return
     */
    public AeroCoefficients calcDelta(double value) {
        // shouldn't be too many items so serial sort isn't half bad
        int i = 0;
        while(i < variable.size()) {
            if(value < variable.get(i++)) {
                break;
            }
        }
        if(i == 0) {
            return new AeroCoefficients(coefficientDeltas.get(0));
        } 
        if(i < variable.size()) {
            // this could be micro optimized
            AeroCoefficients delta = coefficientDeltas.get(i).subtract(coefficientDeltas.get(i-1));
            delta.scale(-1,(value - variable.get(i-1))/(variable.get(i)-variable.get(i-1)));
            return coefficientDeltas.get(i-1).plus(delta);
        } else {
            return new AeroCoefficients(coefficientDeltas.get(i-1));
        }
    }

    /**
     * loads based on file
     * @param file
     */
    public void load(String file){

    }

}