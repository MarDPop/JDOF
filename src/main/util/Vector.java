package main.util;

public class Vector {

    double[] val;

    boolean column = true;

    public Vector() {}

    public Vector(int n) {
        this.val = new double[n];
    }

    public Vector(double[] val) {
        this.val = val;
    }

    public Vector(double[] val, boolean column) {
        this.val = val;
        this.column = column;
    }

    public Vector(Vector b) {
        this.column = b.column;
        this.val = new double[b.val.length];
        System.arraycopy(b.val, 0, this.val, 0, b.val.length);
    }

    public int getRows() {
        if(column) {
            return val.length;
        } 
        return 1;
    }

    public int getCols() {
        if(column) {
            return 1;
        } 
        return val.length;
    }

    public boolean transpose() {
        column = !column;
        return column;
    }

    public Vector add(Vector b) {
        Vector c = new Vector(this);
        for(int i = 0; i < this.val.length; i++){
            c.val[i] += b.val[i];
        }
        return c;
    }

    public Vector add(double b) {
        Vector c = new Vector(this);
        for(int i = 0; i < val.length; i++){
            c.val[i] += b;
        }
        return c;
    }

    public Vector subtract(Vector b) {
        Vector c = new Vector(this);
        for(int i = 0; i < val.length; i++){
            c.val[i] -= b.val[i];
        }
        return c;
    }

    public Vector subtract(double b) {
        Vector c = new Vector(this);
        for(int i = 0; i < val.length; i++){
            c.val[i] -= b;
        }
        return c;
    }

    public Vector multiply(double b) {
        Vector c = new Vector(this);
        for(int i = 0; i < val.length; i++){
            c.val[i] *= b;
        }
        return c;
    }

    public double dot(Vector b) {
        double sum = 0;
        for(int i = 0; i < val.length; i++) {
            sum += b.val[i]*this.val[i];
        }
        return sum;
    }

    public double norm() {
        double sum = 0;
        for(int i = 0; i < val.length; i++) {
            sum += val[i]*val[i];
        }
        return Math.sqrt(sum);
    }

}