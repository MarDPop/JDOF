package main.view;

import main.util.*;

/**
 * perspective Camera class, look direction is z axis intrinsically rotated through X and Y axis 
 */
public class Camera {

    /**
     * Location of camera in scene
     */
    private Cartesian location;

    private Cartesian oldLocation;

    /**
     * Rotation Matrix of Camera (each row is x axis, y axis and z axis in scene respectively) 
     */
    private Matrix3 rotationMat = Matrix3.I();

    /**
     * Old rotation matrix
     */
    private Matrix3 oldRotation;

    /**
     * Field of view angle (angle of view) in horizontal direction
     */
    private double horizontalAngle;

    /**
     * Focal length in pixels
     */
    private double focalLength;

    /**
     * field of view ratio ()
     */
    private double focalLengthRatio;

    /**
     * Screen Width (pixels)
     */
    int screenWidth;

    /**
     * Screen height (pixels)
     */
    int screenHeight;

    /**
     * Center of screen x (pixels)
     */
    int centerX;
    /**
     * Center of screen y (pixels)
     */
    int centerY;


    double distance = 1;

    /**
     * vector for temporary storage
     */
    private Cartesian vector;

    public Camera() { }

    public void setScreenDimensions(int width, int height ) {
        this.screenWidth = height;
        this.screenHeight = width;
        this.centerX = width/2;
        this.centerY = height/2;
        this.focalLength = focalLengthRatio*width*0.5;
    }

    public int[] projectToScreenFrame(Cartesian point) {
        Cartesian a = point.minus(this.location);
        Cartesian proj = rotationMat.multiply(a); // essentially dot product to each axis
        double lRatio = this.focalLength/-proj.z;
        return new int[]{(int)(lRatio*proj.x),(int)(lRatio*proj.y)};
    }

    public void setHorizontalAngle(double angle) {
        this.horizontalAngle = angle;
        this.focalLengthRatio  = 1/Math.tan(angle);
    }

    public void setLocation(Cartesian location) {
        this.location = location;
    }

    public void rotate(double yaw, double pitch) {
        Matrix3 rotation = Matrix3.getXYRotation(-yaw*horizontalAngle,pitch*horizontalAngle);
        this.rotationMat = new Matrix3(rotation.multiply(oldRotation)); // rotation orientation
        vector = new Cartesian(rotationMat.axis[2][0]*distance,rotationMat.axis[2][1]*distance,rotationMat.axis[2][2]*distance); // rotate position
        this.location = this.oldLocation.plus(vector);
        // System.out.println(oldLocation.x +" " + oldLocation.y + " " + oldLocation.z);
        // this.location = this.oldLocation.add(this.rotationMat.mult(this.vector));
    }

    public void shift(double x, double y) {
        Cartesian change = new Cartesian();
        change.x = x*this.rotationMat.get(0,0) + y*this.rotationMat.get(1,0);
        change.y = x*this.rotationMat.get(0,1) + y*this.rotationMat.get(1,1);
        change.z = x*this.rotationMat.get(0,2) + y*this.rotationMat.get(1,2);
        change.scale(distance);
        this.location = this.oldLocation.plus(change);
    }

    public void zoom(int units) {
        Cartesian vectorFromGround = this.location.minus(getPointOnGround(centerX, centerY)); 
        this.distance = vectorFromGround.distance();
        vectorFromGround.scale(units/100.0);
        this.location.add(vectorFromGround);
    }
    
    public void startRotation(int x, int y) {
        this.oldRotation = new Matrix3(this.rotationMat);
        this.oldLocation = getPointOnGround(centerX, centerY); // for now just rotate through center 
        this.distance = vector.distance();
    }

    public void startShift() {
        this.oldLocation = new Cartesian(this.location);
    }

    public Cartesian getLocation() {
        return this.location;
    }

    public Cartesian getPointOnGround(int x, int y) {
        x = centerX-x;
        y -= centerY;

        double d = 1/Math.sqrt(x*x+y*y+focalLength*focalLength);
        Cartesian pointer = new Cartesian();
        pointer.x = (this.rotationMat.axis[0][0]*x + this.rotationMat.axis[1][0]*y + this.rotationMat.axis[2][0]*focalLength)*d;
        pointer.y = (this.rotationMat.axis[0][1]*x + this.rotationMat.axis[1][1]*y + this.rotationMat.axis[2][1]*focalLength)*d;
        pointer.z = (this.rotationMat.axis[0][2]*x + this.rotationMat.axis[1][2]*y + this.rotationMat.axis[2][2]*focalLength)*d;

        double ratio = this.location.z/pointer.z;
        this.vector = new Cartesian(ratio*pointer.x,ratio*pointer.y,this.location.z);

        return location.minus(this.vector);
    }

}