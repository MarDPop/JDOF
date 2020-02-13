package main.util;

public class Matrix {

    double[][] val;

    public Matrix() {}

    public Matrix(int m, int n) {
        this.val = new double[m][n];
    }

    public Matrix(double[][] val) {
        this.val = val;
    }

    public Matrix(Matrix B) {
        this.val = new double[B.getRows()][B.getCols()];
        for(int i = 0; i < B.getRows(); i++) {
            System.arraycopy(B.val[i],0,this.val,0,this.val.length);
        }
    }

    public int getRows() {
        return this.val.length;
    }

    public int getCols() {
        return this.val[0].length;
    }

    public Vector getRow(int i) {
        double[] row = new double[getCols()];
        System.arraycopy(val[i], 0, row, 0, row.length);
        return new Vector(row,false);
    }

    public Vector getCol(int j) {
        double[] col = new double[getRows()];
        for(int i = 0; i < col.length; i++) {
            col[i] = this.val[i][j];
        }
        return new Vector(col);
    }

    public Matrix transpose() {
        double[][] mat = new double[getCols()][getRows()];
        for(int i = 0; i < getRows(); i++) {
            for(int j = 0; j < getCols(); j++) {
                mat[j][i] = this.val[i][j];
            }
        }
        return new Matrix(mat);
    }

    public Matrix add(Matrix B){
        Matrix C = new Matrix(this);
        for(int i = 0; i < getRows(); i++) {
            for(int j = 0; j < getCols(); j++) {
                C.val[i][j] += B.val[i][j];
            }
        }
        return C;
    }

    public Matrix add(double b){
        Matrix C = new Matrix(this);
        for(int i = 0; i < getRows(); i++) {
            for(int j = 0; j < getCols(); j++) {
                C.val[i][j] += b;
            }
        }
        return C;
    }

    public Matrix subtract(Matrix B){
        Matrix C = new Matrix(this);
        for(int i = 0; i < getRows(); i++) {
            for(int j = 0; j < getCols(); j++) {
                C.val[i][j] -= B.val[i][j];
            }
        }
        return C;
    }

    public Matrix subtract(double b){
        Matrix C = new Matrix(this);
        for(int i = 0; i < getRows(); i++) {
            for(int j = 0; j < getCols(); j++) {
                C.val[i][j] -= b;
            }
        }
        return C;
    }

    public Matrix multiply(double b){
        Matrix C = new Matrix(this);
        for(int i = 0; i < getRows(); i++) {
            for(int j = 0; j < getCols(); j++) {
                C.val[i][j] *= b;
            }
        }
        return C;
    }

    public Vector multiply(Vector b){
        // note this assumes vector is column
        Vector c = new Vector(getRows());
        for(int i = 0; i < getRows(); i++) {
            for(int j = 0; j < getCols(); j++) {
                c.val[i] += this.val[i][j]*b.val[j];
            }
        }
        return c;
    }

    public Matrix multiply(Matrix B) throws Exception {
        Matrix C = new Matrix(this.getRows(),B.getCols());
        int inner;
        if(this.getCols() != B.getRows()) {
            throw new Exception("inner dimensions do not match");
        } else {
            inner = this.getCols();
        }
        for(int i = 0; i < this.getRows(); i++) {
            for(int k = 0; k < inner; k++) {
                for(int j = 0; j < B.getCols(); j++) {
                    C.val[i][j] += this.val[i][k]*B.val[k][j];
                }
            }
        }
        return C;
    }

}