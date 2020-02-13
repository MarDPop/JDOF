package main.util;

/**
 * Class describing a 6DOF body
 */
public class Body {

    // Position Vectors
    protected Cartesian position = new Cartesian();
    protected Cartesian velocity = new Cartesian();
    protected Cartesian acceleration = new Cartesian();

    //Rotation Vectors
    protected Cartesian eulerAngles = new Cartesian(); // roll pitch yaw (x,y,z axis rotation)
    protected Cartesian angularVelocity = new Cartesian(); // remember that angular velocity is components of instantenous angular rate
    protected Cartesian angularAccleration = new Cartesian(); 

    /**
     * remember axis is CG center forward = x , right = y, down = z
     */
    protected Axis axis = new Axis(true);

    /**
     * Mass in kg
     */
    protected double mass;

    /**
     * Inertia matrix (standard in kg-m2)
     */
    protected Axis inertia = new Axis(true);

    /**
     *  Inverse Inertia for easier computing
     */
    protected Axis invInertia;

    /**
     * All actions acting on body (used to compute acceleration)
     */
    protected Action[] actions;

    /**
     * Empty constructor
     */
    public Body() { }

    /**
     * returns state
     * @return
     */
    public double[] getState() {
        double[] state = new double[12];
        state[0] = this.position.x;
        state[1] = this.position.y;
        state[2] = this.position.z;
        state[3] = this.velocity.x;
        state[4] = this.velocity.y;
        state[5] = this.velocity.z;
        state[6] = this.eulerAngles.x;
        state[7] = this.eulerAngles.y;
        state[8] = this.eulerAngles.z;
        state[9] = this.angularVelocity.x;
        state[10] = this.angularVelocity.y;
        state[11] = this.angularVelocity.z;
        return state;
    }

    /**
     * returns rate of change of state
     * @return
     */
    public double[] getStateRate() {
        double[] dstate = new double[12];
        dstate[0] = this.velocity.x;
        dstate[1] = this.velocity.y;
        dstate[2] = this.velocity.z;
        dstate[3] = this.acceleration.x;
        dstate[4] = this.acceleration.y;
        dstate[5] = this.acceleration.z;
        dstate[6] = this.angularVelocity.x;
        dstate[7] = this.angularVelocity.y;
        dstate[8] = this.angularVelocity.z;
        dstate[9] = this.angularAccleration.x;
        dstate[10] = this.angularAccleration.y;
        dstate[11] = this.angularAccleration.z;
        return dstate;
    }

    /**
     * loads state from double array
     * @param state
     */
    public void setState(double[] state) {
        this.position.x = state[0];
        this.position.y = state[1];
        this.position.z = state[2];
        this.velocity.x = state[3];
        this.velocity.y = state[4];
        this.velocity.z = state[5];
        this.eulerAngles.x = state[6];
        this.eulerAngles.y = state[7];
        this.eulerAngles.z = state[8];
        this.angularVelocity.x = state[9];
        this.angularVelocity.y = state[10];
        this.angularVelocity.z = state[11];
    }

    /**
     * 
     * @param mass
     * @param inertia
     */
    public void setInertia(double mass, Axis inertia) throws IllegalArgumentException {
        if(inertia.x.x <= 0 || inertia.y.y <= 0 || inertia.z.z <= 0) {
            throw new IllegalArgumentException("Inertia Matrix must have positive and non-zero principal values");
        }
        if(mass <= 0) {
            throw new IllegalArgumentException("mass must be positive and non-zero");
        }
        this.mass = mass;
        this.inertia = inertia;
        this.invInertia = inertia.inv();
    }

    /**
     * Sets actions
     */
    public void setActions(Action[] actions) {
        this.actions = actions;
    }

    /**
     * Computes all actions and applies them to body
     */
    public void computeActions() {
        Cartesian sumForce = new Cartesian();
        Cartesian sumTorque = new Cartesian();
        for(Action action : this.actions) {
            MyPair<Cartesian,Cartesian> components = action.getAction();
            sumForce.add(components.a);
            sumTorque.add(components.b);
        }
        this.acceleration = sumForce.multiply(1/this.mass);
        this.angularAccleration = invInertia.multiply(sumTorque); // ASSUMPTION
    }

    /**
     * Gets mass
     * @return
     */
    public double getMass() {
        return this.mass;
    }

    /**
     * Gets mass
     * @return
     */
    public Axis getInertia() {
        return this.inertia;
    }

    /**
     * @return the position
     */
    public Cartesian getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Cartesian position) {
        this.position = position;
    }

    /**
     * @return the velocity
     */
    public Cartesian getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(Cartesian velocity) {
        this.velocity = velocity;
    }

    /**
     * @return the acceleration
     */
    public Cartesian getAcceleration() {
        return acceleration;
    }

    /**
     * @param acceleration the acceleration to set
     */
    public void setAcceleration(Cartesian acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * @return the eulerAngles
     */
    public Cartesian getEulerAngles() {
        return eulerAngles;
    }

    /**
     * @param eulerAngles the eulerAngles to set
     */
    public void setEulerAngles(Cartesian eulerAngles) {
        this.eulerAngles = eulerAngles;
    }

    /**
     * @return the angular_velocity
     */
    public Cartesian getAngularVelocity() {
        return angularVelocity;
    }

    /**
     * @param angular_velocity the angular_velocity to set
     */
    public void setAngularVelocity(Cartesian angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    /**
     * @return the torque
     */
    public Cartesian getAngularAcceleration() {
        return angularAccleration;
    }

    /**
     * @param torque the torque to set
     */
    public void getAngularAcceleration(Cartesian angularAccleration) {
        this.angularAccleration = angularAccleration;
    }

    /**
     * @return the axis
     */
    public Axis getAxis() {
        return axis;
    }

    /**
     * @param axis the axis to set
     */
    public void setAxis(Axis axis) {
        this.axis = axis;
    }


}