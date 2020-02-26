package main.util.aero;

/**
 * Linearly interpolates 
 */
public class AeroTable {

    public Object[] table;

    /**
     * number of variables
     */
    private int variables;

    /**
     * A sorted list of values for each variable
     */
    private double[][] variableRange;

    /**
     * The difference between hi and low values of range (1 less length)
     */
    private double[][] variableDeltas;

    /**
     * Index location for each variable
     */
    private int[] idx;

    /**
     * Fraction of lower end
     */
    private double[] fraction;

    /**
     * Flag to extrapolate or hold last value
     */
    boolean extrapolate = true; 

    public AeroTable() { }

    public AeroTable(Object[] table, double[][] variableRange) throws Exception {
        this.table = table;
        this.variableRange = variableRange;
        this.variables = variableRange.length;
        Object o = table[0];
        int test = 1; 
        while(o.getClass().isArray()) {
            if(((Object[]) o).length == this.variableRange[test].length) {
                
                this.variableDeltas[test] = new double[this.variableRange[test].length-1];
                for(int i = this.variableRange[test].length-1; i-- > 0;) {
                    this.variableDeltas[test][i] = this.variableRange[test][i+1]-this.variableRange[test][i];
                }
                
                o = ((Object[]) o)[0];
                test++;
            } else {
                throw new Exception("invalid dimensions");
            }
        }
        
    }

    public void load(String file) {

    }

    /**
     * Gets the Coefficients from an input array of values for variables
     * @param var
     * @return
     */
    public AeroCoefficients get(double[] var) {

        for(int i = 0; i < variables; i++) {
            double[] range = this.variableRange[i];
            if(range[idx[i]] < var[i] && range[idx[i]+1] > var[i]) {
                fraction[i] = (var[i] - range[idx[i]])/this.variableDeltas[i][idx[i]];
            } else {
                find(range,var[i],i);
            }
        }

        return getCoef(this.table,0);
    }

    private void find(double[] row, double val, int i) {
        // Check extremes
        if(val < row[0]) {
            idx[i] = 0;
            if(extrapolate) {
                fraction[i] = (val-row[0])/this.variableDeltas[i][0];
            } else {
                fraction[i] = 1;
            }
            return;
        }

        if(val > row[row.length-1]) {
            idx[i] = row.length-1;
            if(extrapolate) {
                fraction[i] = (val-row[idx[i]])/this.variableDeltas[i][idx[i]];
            } else {
                fraction[i] = 0;
            }
            return;
        } 
        
        if(val < row[idx[i]]){
            idx[i]--;
            while(val < row[idx[i]]) {
                idx[i]--;
            }
        } else {
            idx[i]++;
            while(val > row[idx[i]+1]) {
                idx[i]++;
            }
        }
        fraction[i] = (val-row[idx[i]])/this.variableDeltas[i][idx[i]];
    }

    private AeroCoefficients getCoef(Object[] table, int level) {
        if(level == variables-1) {
            return AeroCoefficients.interpolate((AeroCoefficients) table[idx[level]], (AeroCoefficients) table[idx[level]+1], fraction[level]);
        }
        return AeroCoefficients.interpolate(getCoef((Object[])table[idx[level]], level-1),getCoef((Object[])table[idx[level]+1], level-1),fraction[level]);
    }
}