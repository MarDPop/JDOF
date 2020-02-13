package main.util;

public class Matrix3 {

    public double[][] axis = new double[3][3];

    private static double[][] EYE = new double[][]{{1,0,0},{0,1,0},{0,0,1}}; 

    private final static int[][] MINOR_IDX = new int[][]{{1,2},{0,2},{0,1}};

    public Matrix3(){ }

    public Matrix3(double[][] A) {
        this.axis = A;
    }

    public Matrix3(Matrix3 A) {
        for(int i = 0; i < 3; i++) {
            System.arraycopy(A.axis[i],0,this.axis[i],0,3);
        }
    }

    public void set(Matrix3 A) {
        for(int i = 0; i < 3; i++) {
            System.arraycopy(A.axis[i],0,this.axis[i],0,3);
        }
    }

    public Matrix3 add(double a) {
        Matrix3 B = new Matrix3();
        for(int i = 0; i < 3; i++) {
            B.axis[i][0] = this.axis[i][0] + a;
            B.axis[i][1] = this.axis[i][1] + a;
            B.axis[i][2] = this.axis[i][2] + a;
        }
        return B;
    }

    public Matrix3 add(Matrix3 A) {
        Matrix3 B = new Matrix3();
        for(int i = 0; i < 3; i++) {
            B.axis[i][0] = this.axis[i][0] + A.axis[i][0];
            B.axis[i][1] = this.axis[i][1] + A.axis[i][1];
            B.axis[i][2] = this.axis[i][2] + A.axis[i][2];
        }
        return B;
    }

    public void scale(double a) {
        for(int i = 0; i < 3; i++) {
            this.axis[i][0] *= a;
            this.axis[i][1] *= a;
            this.axis[i][2] *= a;
        }
    }

    public Matrix3 multiply(double a) {
        Matrix3 B = new Matrix3();
        for(int i = 0; i < 3; i++) {
            B.axis[i][0] = this.axis[i][0]*a;
            B.axis[i][1] = this.axis[i][1]*a;
            B.axis[i][2] = this.axis[i][2]*a;
        }
        return B;
    }

    public Cartesian multiply(Cartesian x) {
        Cartesian b = new Cartesian();
        b.x = this.axis[0][0]*x.x + this.axis[0][1]*x.y + this.axis[0][2]*x.z ;
        b.y = this.axis[1][0]*x.x + this.axis[1][1]*x.y + this.axis[1][2]*x.z ;
        b.z = this.axis[2][0]*x.x + this.axis[2][1]*x.y + this.axis[2][2]*x.z ;
        return b;
    }

    public Matrix3 multiply(Matrix3 B) {
        Matrix3 C = new Matrix3();
        for(int i = 0; i < 3; i++) {
            for(int k = 0; k < 3; k++) {
                for(int j = 0; j < 3; j++) {
                    C.axis[i][j] += this.axis[i][k]*B.axis[k][j];
                }
            }
        }
        return C;
    }

    public double get(int i, int j) {
        return this.axis[i][j];
    }

    public double det() {
        return axis[0][0]*(axis[1][1]*axis[2][2]-axis[1][2]*axis[2][1])-axis[0][1]*(axis[1][0]*axis[2][2]-axis[1][2]*axis[2][0])+axis[0][2]*(axis[1][0]*axis[2][1]-axis[1][1]*axis[2][0]);
    }

    public Matrix3 transpose() {
        Matrix3 B = new Matrix3();
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                B.axis[i][j] = this.axis[j][i];
            }
        }
        return B;
    }

    public Matrix3 inverse() {
        Matrix3 C = new Matrix3();
        int sign = 1;
        double det = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                C.axis[i][j] = sign*this.minor(i,j);
                sign *= -1;
                if(i == 0) {
                    det += this.axis[0][j]*C.axis[0][j];
                }
            }
        }
        
        C = C.transpose();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                C.axis[i][j] /= det;
            }
        }
        return C;
    }

    private double minor(int i, int j) {
        return  this.axis[MINOR_IDX[i][0]][MINOR_IDX[j][0]]*this.axis[MINOR_IDX[i][1]][MINOR_IDX[j][1]] -
                this.axis[MINOR_IDX[i][0]][MINOR_IDX[j][1]]*this.axis[MINOR_IDX[i][1]][MINOR_IDX[j][0]];
    }

    /**
     * 
     * @param axis unit vector
     * @param angle
     * @return
     */
    public static Matrix3 axisAngle(Cartesian axis, double angle) {
        Matrix3 out = new Matrix3();
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        double c1 = 1-c;
        out.axis[0][0] = c+axis.x*axis.x*c1;
        out.axis[0][1] = axis.x*axis.y*c1 - axis.z*s;
        out.axis[0][2] = axis.x*axis.z*c1 + axis.y*s;
        out.axis[1][0] = axis.y*axis.x*c1 + axis.z*s;
        out.axis[1][1] = c+axis.y*axis.y*c1;
        out.axis[1][2] = axis.y*axis.z*c1 - axis.x*s;
        out.axis[2][0] = axis.z*axis.x*c1 - axis.y*s;
        out.axis[2][1] = axis.z*axis.y*c1 + axis.x*s;
        out.axis[2][2] = c+axis.z*axis.z*c1;
        return out;
    }

    public void getXRotationMatrix(double angle) {
        this.axis[0][0] = 1;
        this.axis[1][1] = Math.cos(angle);
        this.axis[1][2] = -Math.sin(angle);
        this.axis[2][1] = -this.axis[2][1];
        this.axis[2][2] = this.axis[1][1];
    }

    public void getYRotationMatrix(double angle) {
        this.axis[1][1] = 1;
        this.axis[0][0] = Math.cos(angle);
        this.axis[0][2] = Math.sin(angle);
        this.axis[2][0] = -this.axis[2][1];
        this.axis[2][2] = this.axis[1][1];
    }

    public void getZRotationMatrix(double angle) {
        this.axis[2][2] = 1;
        this.axis[0][0] = Math.cos(angle);
        this.axis[0][1] = -Math.sin(angle);
        this.axis[1][0] = -this.axis[2][1];
        this.axis[1][1] = this.axis[1][1];
    }

    public static Matrix3 getYXRotation(double yaw, double pitch) {
        Matrix3 out = new Matrix3();
        double c1 = Math.cos(yaw);
        double s1 = Math.sin(yaw);
        double c2 = Math.cos(pitch);
        double s2 = Math.sin(pitch);
        out.axis[0][0] = c1;
        out.axis[0][1] = 0;
        out.axis[0][2] = s1;
        out.axis[1][0] = s1*s2;
        out.axis[1][1] = c2;
        out.axis[1][2] = -c1*s2;
        out.axis[2][0] = -c2*s1;
        out.axis[2][1] = s2;
        out.axis[2][2] = c1*c2;
        return out;
    }

    public static Matrix3 getXYRotation(double yaw, double pitch) {
        Matrix3 out = new Matrix3();
        double c1 = Math.cos(yaw);
        double s1 = Math.sin(yaw);
        double c2 = Math.cos(pitch);
        double s2 = Math.sin(pitch);
        out.axis[0][0] = c1;
        out.axis[0][1] = s1*s2;
        out.axis[0][2] = s1*c2;
        out.axis[1][0] = 0;
        out.axis[1][1] = c2;
        out.axis[1][2] = -s2;
        out.axis[2][0] = -s1;
        out.axis[2][1] = c1*s2;
        out.axis[2][2] = c1*c2;
        return out;
    }

    public void rotationMatrixIntrinsic(double yaw, double pitch, double roll) {
        double c1 = Math.cos(yaw);
        double s1 = Math.sin(yaw);
        double c2 = Math.cos(pitch);
        double s2 = Math.sin(pitch);
        double c3 = Math.cos(roll);
        double s3 = Math.sin(roll);
        this.axis[0][0] = c1*c2;
        this.axis[0][1] = c1*s2*s3-c3*s1;
        this.axis[0][2] = s1*s3+c1*c3*s2;
        this.axis[1][0] = c2*s1;
        this.axis[1][1] = c1*c3+s1*s2*s3;
        this.axis[1][2] = c3*s1*s2-c1*s3;
        this.axis[2][0] = -s2;
        this.axis[2][1] = c2*s3;
        this.axis[2][2] = c2*c3;
    }

    public static Matrix3 I() {
        return new Matrix3(EYE);
    }
}