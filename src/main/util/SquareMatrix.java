package main.util;

public class SquareMatrix extends Matrix {

    public SquareMatrix() {}

    public SquareMatrix(int m) {
        super(m,m);
    }

    public SquareMatrix(double[][] mat) {
        super(mat);
    }

    public SquareMatrix(SquareMatrix B) {
        super(B);
    }

    /**
     * Analytical determinant (slow but good for checking)
     * @return determinant
     */
    public double analyticalDeterminant() {
        double sum = 0;
        if(this.val.length == 2) {
            return this.val[0][0]*this.val[1][1]-this.val[1][0]*this.val[0][1];
        }
        int sign = 1;
        for(int j = 0; j < this.val.length; j++) {
            sum += sign*this.val[0][j]*submatrix(0,j).analyticalDeterminant();
            sign *= -1;
        }
        return sum;
    }

    /**
     * retrieves the submatrix excluding rows i and column j
     * @param i
     * @param j
     * @return
     */
    public SquareMatrix submatrix(int i, int j) {
        SquareMatrix sub = new SquareMatrix(this.val.length-1);
        int j2 = sub.val.length-j;
        int j1 = j+1;
        for(int row = 0; row < this.val.length; row++) {
            if(row < i) {
                System.arraycopy(this.val[i],0,sub.val[i],0,j); 
                System.arraycopy(this.val[i],j1,sub.val[i],j,j2); 
            } else {
                System.arraycopy(this.val[i],0,sub.val[i-1],0,j); 
                System.arraycopy(this.val[i],j1,sub.val[i-1],j,j2); 
            }
        }
        return sub;
    }

    @Override
    public SquareMatrix transpose() {
        int n = this.val.length;
        double[][] mat = new double[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                mat[j][i] = this.val[i][j];
            }
        }
        return new SquareMatrix(mat);
    }

    public SquareMatrix analyticalInverse() {
        SquareMatrix C = new SquareMatrix(this.val.length);
        double det = 0;
        int signFlip = 1;
        for(int i = 0; i < this.val.length; i++){
            int sign = signFlip;
            for(int j = 0; j < this.val[0].length; j++) {
                C.val[i][j] = sign*this.submatrix(i,j).analyticalDeterminant();
                sign *= -1;
                if(i == 0) {
                    det += this.val[0][j]*C.val[0][j];
                }
            }
            signFlip *= -1;
        }
        
        return (SquareMatrix)C.transpose().multiply(1/det);
    }

}