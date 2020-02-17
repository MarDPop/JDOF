package main.util;

public class Quaternion {

    public double i;

    public double j;

    public double k;

    public double r;

    public Quaternion() {}

    public Quaternion(double i, double j, double k, double r) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.r = r;
    }

    public Quaternion(Quaternion p) {
        this.i = p.i;
        this.j = p.j;
        this.k = p.k;
        this.r = p.r;
    }

    public Cartesian vector() {
        return new Cartesian(i,j,k);
    }

    public double scalar() {
        return r;
    }

    public Quaternion conjugate() {
        return new Quaternion(-this.i,-this.j,-this.k,this.r);
    }

    public double norm() {
        return Math.sqrt(i*i+j*j+k*k+r*r);
    }

    public Quaternion plus(Quaternion p) {
        Quaternion q = new Quaternion();
        q.i = this.i + p.i;
        q.j = this.j + p.j;
        q.k = this.k + p.k;
        q.r = this.r + p.r;
        return q;
    }

    public Quaternion minus(Quaternion p) {
        Quaternion q = new Quaternion();
        q.i = this.i - p.i;
        q.j = this.j - p.j;
        q.k = this.k - p.k;
        q.r = this.r - p.r;
        return q;
    }

    public void scale(double a) {
        this.i *= a;
        this.j *= a;
        this.k *= a;
        this.r *= a;
    }

    public Quaternion reciprocal() {
        Quaternion q = this.conjugate();
        q.scale(1/(i*i+j*j+k*k+r*r));
        return q;
    }

    public Quaternion hamiltonProduct(Quaternion p) {
        Quaternion q = new Quaternion();
        q.r = this.r*q.r - this.i*p.i - this.j*p.j - this.k*p.k;
        q.i = this.r*q.i + this.i*p.r + this.j*p.k - this.k*p.j;
        q.j = this.r*q.j - this.i*p.k + this.j*p.r + this.k*p.i;
        q.k = this.r*q.k + this.i*p.j - this.j*p.i + this.k*p.r;
        return q;
    }

    public static Quaternion fromAxisAngle(Cartesian axis, double angle) {
        Quaternion q = new Quaternion();
        double s = Math.sin(angle/2);
        q.r = Math.cos(angle/2);
        q.i = axis.x*s;
        q.j = axis.y*s;
        q.k = axis.z*s;
        return q;
    }

    public Cartesian returnRollPitchYaw() {
        Cartesian out = new Cartesian();
        out.x = Math.atan2(2*(r*i+j*k),1-2*(i*i+j*j));
        out.y = Math.asin(2*(r*j-k*i));
        out.z = Math.atan2(2*(r*k+i*j),1-2*(j*j+k*k));
        return out;
    }

    public Axis toRotationMatrix() {
        Axis a = new Axis(true);
        
        return a;
    }

}