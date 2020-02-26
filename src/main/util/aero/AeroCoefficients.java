package main.util.aero;

/**
 * Class containing Aero Coefficients 
 */
public class AeroCoefficients {

    /*
    NOTE:
    SA = Stability Axis
    BA = Body Axis
    GA = Global Axis
    0 - Drag (Force/SA/x) <- Default SA
    1 - Side (Force/SA/y) <- Default SA
    2 - Lift (Force/SA/z) <- Default SA
    3 - Roll (Moment/BA/x) <- Always
    4 - Pitch (Moment/BA/y) <- Always
    5 - Yaw (Moment/BA/z) <- Always
    */

    public final static int DRAG_COEFFICIENT = 0;
    public final static int SIDEFORCE_COEFFICIENT = 1;
    public final static int LIFT_COEFFICIENT = 2;
    public final static int ROLL_MOMENT_COEFFICIENT = 3;
    public final static int PITCH_MOMENT_COEFFICIENT = 4;
    public final static int YAW_MOMENT_COEFFICIENT = 5;

    /**
     * Denotes whether the coefficients are in stability axis or body axis (true = stability axis)
     */
    public final boolean stabilityAxis;

    // Coefficients
    private double CFx;
    private double CFy;
    private double CFz;
    private double CMx;
    private double CMy;
    private double CMz;

    /**
     * Empty Constructor 
     */
    public AeroCoefficients() {
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
    public AeroCoefficients(double CD, double CR, double CL, double CMx, double CMy, double CMz, boolean stabilityAxis) {
        this.CFx = CD;
        this.CFy = CR;
        this.CFz = CL;
        this.CMx = CMx;
        this.CMy = CMy;
        this.CMz = CMz;
        this.stabilityAxis = stabilityAxis;
    }

    /**
     * Copy constructor
     * @param B
     */
    public AeroCoefficients(AeroCoefficients B) {
        this.CFx = B.CFx;
        this.CFy = B.CFy;
        this.CFz = B.CFz;
        this.CMx = B.CMx;
        this.CMy = B.CMy;
        this.CMz = B.CMz;
        this.stabilityAxis = B.stabilityAxis;
    }

    /**
     * Adds coefficients
     * @param B
     * @return
     */
    public void add(AeroCoefficients B) {
        this.CFx += B.CFx;
        this.CFy += B.CFy;
        this.CFz += B.CFz;
        this.CMx += B.CMx;
        this.CMy += B.CMy;
        this.CMz += B.CMz;
    }

    /**
     * Adds coefficients
     * @param B
     * @return
     */
    public AeroCoefficients plus(AeroCoefficients B) {
        AeroCoefficients out = new AeroCoefficients();
        out.CFx = this.CFx + B.CFx;
        out.CFy = this.CFy + B.CFy;
        out.CFz = this.CFz + B.CFz;
        out.CMx = this.CMx + B.CMx;
        out.CMy = this.CMy + B.CMy;
        out.CMz = this.CMz + B.CMz;
        return out;
    }

    /**
     * Subtracts coefficients
     * @param B
     * @return
     */
    public AeroCoefficients subtract(AeroCoefficients B) {
        AeroCoefficients out = new AeroCoefficients();
        out.CFx = this.CFx - B.CFx;
        out.CFy = this.CFy - B.CFy;
        out.CFz = this.CFz - B.CFz;
        out.CMx = this.CMx - B.CMx;
        out.CMy = this.CMy - B.CMy;
        out.CMz = this.CMz - B.CMz;
        return out;
    }

    /**
     * Scales a coefficient identified by index by amount d, if index -1 is chosen all coefficients are scaled
     * @param d
     * @param index
     */
    public void scale(int index, double mag) {
        switch(index) {
            case 0:
                this.CFx *= mag;
                break;
            case 1:
                this.CFy *= mag;
                break;
            case 2:
                this.CFz *= mag;
                break;
            case 3:
                this.CMx *= mag;
                break;
            case 4:
                this.CMy *= mag;
                break;
            case 5:
                this.CMz *= mag;
                break;  
            default:
                scale(mag);
                break;  
        }
    }

    /**
     * Scales a coefficient identified by index by amount d, if index -1 is chosen all coefficients are scaled
     * @param d
     * @param index
     */
    public void scale(double mag) {
        this.CFx *= mag;
        this.CFy *= mag;
        this.CFz *= mag;
        this.CMx *= mag;
        this.CMy *= mag;
        this.CMz *= mag;
    }

    /**
     * Scales a coefficient identified by index by amount d, if index -1 is chosen all coefficients are scaled
     * @param d
     * @param index
     */
    public AeroCoefficients multiply(double mag) {
        AeroCoefficients out = new AeroCoefficients();
        out.CFx = this.CFx = mag;
        out.CFy = this.CFx = mag;
        out.CFz = this.CFx = mag;
        out.CMx = this.CFx = mag;
        out.CMy = this.CFx = mag;
        out.CMz = this.CFx = mag;
        return out;
    }

    /**
     * Used to linearly interpolate between two Aero Coefficients with a factor between 0 -> 1.0
     * @param A
     * @param B
     * @param factor 
     * @return
     */
    public static AeroCoefficients interpolate(AeroCoefficients A, AeroCoefficients B, double factor) {
        AeroCoefficients C = new AeroCoefficients(A);
        C.CFx += factor*(B.CFx - A.CFx);
        C.CFy += factor*(B.CFy - A.CFy);
        C.CFz += factor*(B.CFz - A.CFz);
        C.CMx += factor*(B.CMx - A.CMx);
        C.CMy += factor*(B.CMy - A.CMy);
        C.CMz += factor*(B.CMz - A.CMz);
        return C;
    }

    /**
     * 
     * @param index
     * @return
     */
    public double get(int index) {
        switch(index) {
            case 0:
                return this.CFx;
            case 1:
                return this.CFy;
            case 2:
                return this.CFx;
            case 3:
                return this.CMx;
            case 4:
                return this.CMy;
            case 5:
                return this.CMz;  
            default:
                return Double.NaN; 
        }
    }

    /**
     * Gets Drag Coefficient
     * @return
     */
    public double getCD() {
        return CFx;
    }

    /**
     * Gets Side Force Coefficient
     * @return
     */
    public double getCR() {
        return CFy;
    }

    /**
     * Gets Lift Coefficient
     * @return
     */
    public double getCL() {
        return CFz;
    }

    /**
     * Gets moment about body x axis (roll)
     * @return
     */
    public double getCMx() {
        return CMx;
    }

    /**
     * Gets moment about body y axis (pitch)
     * @return
     */
    public double getCMy() {
        return CMy;
    }

    /**
     * Gets moment about body z axis (yaw)
     * @return
     */
    public double getCMz() {
        return CMz;
    }

    /**
     * Sets value at index
     * @param index
     * @param value
     */
    public void set(int index, double value) {
        switch(index) {
            case 0:
                this.CFx = value;
                break;
            case 1:
                this.CFy = value;
                break;
            case 2:
                this.CFz = value;
                break;
            case 3:
                this.CMx = value;
                break;
            case 4:
                this.CMy = value;
                break;
            case 5:
                this.CMz = value;
                break;   
        }
    }

    /**
     * @return the cFx
     */
    public double getCFx() {
        return CFx;
    }

    /**
     * @param cFx the cFx to set
     */
    public void setCFx(double cFx) {
        CFx = cFx;
    }

    /**
     * @return the cFy
     */
    public double getCFy() {
        return CFy;
    }

    /**
     * @param cFy the cFy to set
     */
    public void setCFy(double cFy) {
        CFy = cFy;
    }

    /**
     * @return the cFz
     */
    public double getCFz() {
        return CFz;
    }

    /**
     * @param cFz the cFz to set
     */
    public void setCFz(double cFz) {
        CFz = cFz;
    }

    /**
     * @param cMx the cMx to set
     */
    public void setCMx(double cMx) {
        CMx = cMx;
    }

    /**
     * @param cMy the cMy to set
     */
    public void setCMy(double cMy) {
        CMy = cMy;
    }

    /**
     * @param cMz the cMz to set
     */
    public void setCMz(double cMz) {
        CMz = cMz;
    }

}