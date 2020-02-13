package main.util;

public class Axis {

    public Cartesian x;

    public Cartesian y;

    public Cartesian z;

    public Axis(){ }

    public Axis(boolean I) {
        if(I) {
            this.x = new Cartesian(1,0,0);
            this.y = new Cartesian(0,1,0);
            this.z = new Cartesian(0,0,1);
        } else {
            this.x = new Cartesian();
            this.y = new Cartesian();
            this.z = new Cartesian();
        }
    }

    public Axis(double[][] A) {
        this.x = new Cartesian(A[0]);
        this.y = new Cartesian(A[1]);
        this.z = new Cartesian(A[2]);
    }

    public Axis(Cartesian x, Cartesian y, Cartesian z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Axis(Axis A) {
        this.x = new Cartesian(A.x);
        this.y = new Cartesian(A.y);
        this.z = new Cartesian(A.z);
    }

    public void set(Axis A) {
        this.x = new Cartesian(A.x);
        this.y = new Cartesian(A.y);
        this.z = new Cartesian(A.z);
    }

    public double[][] toArray() {
        return new double[][]{{x.x,x.y,x.z},{y.x,y.y,y.z},{z.x,z.y,z.z}};
    }

    public Axis add(double a) {
        Axis B = new Axis();
        B.x = this.x.plus(a);
        B.y = this.x.plus(a);
        B.z = this.x.plus(a);
        return B;
    }

    public Axis add(Axis A) {
        Axis B = new Axis();
        B.x = this.x.plus(A.x);
        B.y = this.y.plus(A.y);
        B.z = this.z.plus(A.z);
        return B;
    }

    public Axis multiply(double a) {
        Axis B = new Axis();
        B.x = this.x.multiply(a);
        B.y = this.y.multiply(a);
        B.z = this.z.multiply(a);
        return B;
    }

    public Cartesian multiply(Cartesian x) {
        Cartesian b = new Cartesian();
        b.x = this.x.x*x.x + this.x.y*x.y + this.x.z*x.z ;
        b.y = this.y.x*x.x + this.y.y*x.y + this.y.z*x.z ;
        b.z = this.z.x*x.x + this.z.y*x.y + this.z.z*x.z ;
        return b;
    }

    /**
     * Get's determinant
     * @return
     */
    public double det() {
        return x.x*(y.y*z.z-y.z*z.y) - x.y*(y.x*z.z-y.z*z.x) + x.z*(y.x*z.y-y.y*z.x);
    }

    public Axis transpose() {
        Axis B = new Axis();
        B.x = new Cartesian(this.x.x,this.y.x, this.z.x);
        B.y = new Cartesian(this.x.y,this.y.y, this.z.y);
        B.z = new Cartesian(this.x.z,this.y.z, this.z.z);
        return B;
    }

    /**
     * Cramer's rule inverse matrix
     * @return
     */
    public Axis inv() {
        Axis out = new Axis(false);
        out.x.x = y.y*z.z-y.z*z.y;
        out.y.x = -(y.x*z.z-y.z*z.x);
        out.z.x = y.x*z.y-y.y*z.x;
        double det = 1/(x.x*out.x.x+x.y*out.y.x+x.z*out.z.x);
        out.x.x *= det;
        out.y.x *= det;
        out.z.x *= det;

        out.x.y = -det*(x.y*z.z-x.z*z.y);
        out.y.y = det*(x.x*z.z-x.z*z.x);
        out.z.y = -det*(x.x*z.y-x.y*z.x);

        out.x.z = det*(x.y*y.z-x.z*y.y);
        out.y.z = -det*(x.x*y.z-x.z*y.x);
        out.z.z = det*(x.x*y.y-x.y*y.x);
        return out;
    }

    /**
     * Cramer's rule solution
     * @param b
     * @return
     */
    public Cartesian inv(Cartesian b) {
        Cartesian out = new Cartesian();
        out.x = y.y*z.z-y.z*z.y;
        out.y = y.x*z.z-y.z*z.x;
        out.z = y.x*z.y-y.y*z.x;
        double det = 1/( x.x*out.x - x.y*out.y + x.z*out.z);
        out.x = ( b.x*out.x - x.y*(b.y*z.z-y.z*b.z) + x.z*(b.y*z.y-y.y*b.z) ) * det;
        out.y = ( x.x*(b.y*z.z-y.z*b.z) - b.x*out.y + x.z*(y.x*b.z-b.y*z.x) ) * det;
        out.z = ( x.x*(y.y*b.z-b.y*z.y) - x.y*(y.x*b.z-b.y*z.x) + b.x*out.z ) * det;
        return out;
    }
    

    /**
     * 
     * @param axis unit vector
     * @param angle
     * @return
     */
    public static Axis axisAngle(Cartesian axis, double angle) {
        Axis out = new Axis();
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        double c1 = 1-c;
        out.x.x = c+axis.x*axis.x*c1;
        out.x.y = axis.x*axis.y*c1 - axis.z*s;
        out.x.z = axis.x*axis.z*c1 + axis.y*s;
        out.y.x = axis.y*axis.x*c1 + axis.z*s;
        out.y.y = c+axis.y*axis.y*c1;
        out.y.z = axis.y*axis.z*c1 - axis.x*s;
        out.z.x = axis.z*axis.x*c1 - axis.y*s;
        out.z.y = axis.z*axis.y*c1 + axis.x*s;
        out.z.z = c+axis.z*axis.z*c1;
        return out;
    }

    public void getXRotationMatrix(double angle) {
        this.x.x = 1;
        this.y.y = Math.cos(angle);
        this.y.z = -Math.sin(angle);
        this.z.y = -this.z.y;
        this.z.z = this.y.y;
    }

    public void getYRotationMatrix(double angle) {
        this.y.y = 1;
        this.x.x = Math.cos(angle);
        this.x.z = Math.sin(angle);
        this.z.x = -this.z.y;
        this.z.z = this.y.y;
    }

    public void getZRotationMatrix(double angle) {
        this.z.z = 1;
        this.x.x = Math.cos(angle);
        this.x.y = -Math.sin(angle);
        this.y.x = -this.z.y;
        this.y.y = this.y.y;
    }

    public static Axis getYXRotation(double yaw, double pitch) {
        Axis out = new Axis();
        double c1 = Math.cos(yaw);
        double s1 = Math.sin(yaw);
        double c2 = Math.cos(pitch);
        double s2 = Math.sin(pitch);
        out.x.x = c1;
        out.x.y = 0;
        out.x.z = s1;
        out.y.x = s1*s2;
        out.y.y = c2;
        out.y.z = -c1*s2;
        out.z.x = -c2*s1;
        out.z.y = s2;
        out.z.z = c1*c2;
        return out;
    }

    public static Axis getXYRotation(double yaw, double pitch) {
        Axis out = new Axis();
        double c1 = Math.cos(yaw);
        double s1 = Math.sin(yaw);
        double c2 = Math.cos(pitch);
        double s2 = Math.sin(pitch);
        out.x.x = c1;
        out.x.y = s1*s2;
        out.x.z = s1*c2;
        out.y.x = 0;
        out.y.y = c2;
        out.y.z = -s2;
        out.z.x = -s1;
        out.z.y = c1*s2;
        out.z.z = c1*c2;
        return out;
    }

    public void rotationMatrixIntrinsic(double yaw, double pitch, double roll) {
        double c1 = Math.cos(yaw);
        double s1 = Math.sin(yaw);
        double c2 = Math.cos(pitch);
        double s2 = Math.sin(pitch);
        double c3 = Math.cos(roll);
        double s3 = Math.sin(roll);
        this.x.x = c1*c2;
        this.x.y = c1*s2*s3-c3*s1;
        this.x.z = s1*s3+c1*c3*s2;
        this.y.x = c2*s1;
        this.y.y = c1*c3+s1*s2*s3;
        this.y.z = c3*s1*s2-c1*s3;
        this.z.x = -s2;
        this.z.y = c2*s3;
        this.z.z = c2*c3;
    }
}