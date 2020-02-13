package main.util.aero;

/**
 * Standard Aero Coefficients
 */
public class Coefficients {

    /*
    SA = Stability Axis
    BA = Body Axis
    GA = Global Axis
    0 - Drag (Force/SA/x)
    1 - Side (Force/SA/y)
    2 - Lift (Force/SA/z)
    3 - Roll (Moment/BA/x)
    4 - Pitch (Moment/BA/y)
    5 - Yaw (Moment/BA/z)
    */

    public final static int DRAG_COEFFICIENT = 0;
    public final static int SIDEFORCE_COEFFICIENT = 1;
    public final static int LIFT_COEFFICIENT = 2;
    public final static int ROLL_MOMENT_COEFFICIENT = 3;
    public final static int PITCH_MOMENT_COEFFICIENT = 4;
    public final static int YAW_MOMENT_COEFFICIENT = 5;

    public final boolean stabilityAxis;

    private final double[] coef = new double[6];

    /**
     * Empty Constructor
     */
    public Coefficients() {
        this.stabilityAxis = true;
    }

    /**
     * Default Constructor
     * @param CD
     * @param CR
     * @param CL
     * @param CMx
     * @param CMy
     * @param CMz
     */
    public Coefficients(double CD, double CR, double CL, double CMx, double CMy, double CMz, boolean stabilityAxis) {
        this.coef[0] = CD;
        this.coef[1] = CR;
        this.coef[2] = CL;
        this.coef[3] = CMx;
        this.coef[4] = CMy;
        this.coef[5] = CMz;
        this.stabilityAxis = stabilityAxis;
    }

    /**
     * Copy constructor
     * @param B
     */
    public Coefficients(Coefficients B) {
        System.arraycopy(B.coef, 0, this.coef, 0, 6);
        this.stabilityAxis = B.stabilityAxis;
    }

    /**
     * Adds coefficients
     * @param B
     * @return
     */
    public Coefficients add(Coefficients B) {
        Coefficients out = new Coefficients();
        for(int i = 0; i < 6; i++) {
            out.coef[i] = this.coef[i] + B.coef[i];
        }
        return out;
    }

    /**
     * Subtracts coefficients
     * @param B
     * @return
     */
    public Coefficients subtract(Coefficients B) {
        Coefficients out = new Coefficients();
        for(int i = 0; i < 6; i++) {
            out.coef[i] = this.coef[i] - B.coef[i];
        }
        return out;
    }

    /**
     * Scales a coefficient identified by index by amount d, if index -1 is chosen all coefficients are scaled
     * @param d
     * @param index
     */
    public void scale(int index, double mag) {
        if(index < 0) {
            for(int i = 0; i < 6; i++) {
                this.coef[i] *= mag;
            }
        } else {
            this.coef[index] *= mag;
        }
    }

    public Coefficients interpolate(Coefficients A, Coefficients B, double factor) {
        Coefficients C = new Coefficients(A);
        for(int i = 0; i < 6; i++) {
            C.coef[i] += factor*(B.coef[i] - A.coef[i]);
        }
        return C;
    }

    /**
     * 
     * @param index
     * @return
     */
    public double get(int index) {
        return coef[index];
    }

    /**
     * Gets Drag Coefficient
     * @return
     */
    public double getCD() {
        return coef[0];
    }

    /**
     * Gets Side Force Coefficient
     * @return
     */
    public double getCR() {
        return coef[1];
    }

    /**
     * Gets Lift Coefficient
     * @return
     */
    public double getCL() {
        return coef[2];
    }

    /**
     * Gets moment about body x axis (roll)
     * @return
     */
    public double getCMx() {
        return coef[3];
    }

    /**
     * Gets moment about body y axis (pitch)
     * @return
     */
    public double getCMy() {
        return coef[4];
    }

    /**
     * Gets moment about body z axis (yaw)
     * @return
     */
    public double getCMz() {
        return coef[5];
    }

    /**
     * Sets value at index
     * @param index
     * @param value
     */
    public void set(int index, double value) {
        this.coef[index] = value;
    }

}