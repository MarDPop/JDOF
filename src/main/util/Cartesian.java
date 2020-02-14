package main.util;

public class Cartesian {

    public double x;

    public double y;

    public double z;

    public Cartesian(){};

    public Cartesian(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Cartesian(Cartesian b) {
        this.x = b.x;
        this.y = b.y;
        this.z = b.z;
    }

    public Cartesian(double[] b) {
        this.x = b[0];
        this.y = b[1];
        this.z = b[2];
    }

    public void zero() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public void set(Cartesian b) {
        this.x = b.x;
        this.y = b.y;
        this.z = b.z;
    }

    public double[] toArray() {
        return new double[]{x,y,z};
    }

    @Override
    public String toString() {
        return "[ " + this.x + " , " + this.y + " , " + this.z + " ]";
    }

    public double distance() {
        return Math.sqrt(x*x+y*y+z*z);
    }

    public double dot(Cartesian b) {
        return this.x*b.x+this.y*b.y+this.z*b.z;
    }

    public void normalize() {
        double d = distance();
        this.x/=d;
        this.y/=d;
        this.z/=d;
    }

    public Cartesian cross(Cartesian b) {
        Cartesian c = new Cartesian();
        c.x = this.y*b.z-this.z*b.y;
        c.y = this.z*b.x-this.x*b.z;
        c.z = this.x*b.y-this.y*b.x;
        return c;
    }

    public Cartesian multiply(double a) {
        Cartesian c = new Cartesian();
        c.x = this.x*a;
        c.y = this.y*a;
        c.z = this.z*a;
        return c;
    }

    public void scale(double a) {
        this.x *= a;
        this.y *= a;
        this.z *= a;
    }

    public Cartesian plus(double a) {
        Cartesian c = new Cartesian();
        c.x = this.x+a;
        c.y = this.y+a;
        c.z = this.z+a;
        return c;
    }

    public void add(double a) {
        this.x += a;
        this.y += a;
        this.z += a;
    }

    public Cartesian minus(double a) {
        Cartesian c = new Cartesian();
        c.x = this.x-a;
        c.y = this.y-a;
        c.z = this.z-a;
        return c;
    }

    public Cartesian plus(Cartesian b) {
        Cartesian c = new Cartesian();
        c.x = this.x+b.x;
        c.y = this.y+b.y;
        c.z = this.z+b.z;
        return c;
    }

    public void add(Cartesian a) {
        this.x += a.x;
        this.y += a.y;
        this.z += a.z;
    }

    public Cartesian minus(Cartesian b) {
        Cartesian c = new Cartesian();
        c.x = this.x-b.x;
        c.y = this.y-b.y;
        c.z = this.z-b.z;
        return c;
    }

}