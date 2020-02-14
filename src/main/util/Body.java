package main.util;

/**
 * Class describing a 6DOF body (all defaults are 'non dimensional')
 */
public class Body {

    // Position Vectors
    protected final Cartesian position = new Cartesian();
    protected final Cartesian velocity = new Cartesian();
    protected final Cartesian acceleration = new Cartesian();

    //Rotation Vectors
    protected final Cartesian eulerAngles = new Cartesian(); // roll pitch yaw (x,y,z axis rotation)
    protected final Cartesian angularVelocity = new Cartesian(); // remember that angular velocity is components of instantenous angular rate
    protected Cartesian angularAccleration = new Cartesian(); 

    /**
     * remember axis is CG center forward = x , right = y, down = z
     */
    protected Axis axis = new Axis(true);

    /**
     * Mass in kg. 
     */
    protected double mass = 1; 

    /**
     * Inertia matrix (standard in kg-m2)
     */
    protected Axis inertia = new Axis(true);

    /**
     *  Inverse Inertia for easier computing
     */
    protected Axis invInertia = new Axis(true);

    /**
     * All actions acting on body (used to compute acceleration)
     */
    protected Action[] actions = new Action[0];

    /**
     * meh
     */
    protected int nActions = 0;

    /**
     * Gravity (since body must have mass, gravity must be acting, but can assign a zero one if not needed)
     */
    protected Gravity g = new Gravity();

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
        setAxisFromAngles();
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
        this.nActions = this.actions.length;
    }

    /**
     * Resize and add action
     * @param a
     */
    public void addAction(Action a) {
        Action[] tempActions = new Action[this.actions.length+1];
        System.arraycopy(this.actions, 0, tempActions, 0, this.actions.length);
        tempActions[this.actions.length] = a;
        this.actions = tempActions;
        this.nActions = this.actions.length;
    }

    /**
     * Computes all actions and applies them to body
     */
    public void computeActions() {
        this.acceleration.zero();
        this.angularAccleration.zero();
        for(int i = nActions; i-- > 0;) {
            MyPair<Cartesian,Cartesian> components = actions[i].getAction();
            this.acceleration.add(components.a);
            this.angularAccleration.add(components.b);
        }
        this.acceleration.scale(1/this.mass);
        
        this.acceleration.add(g.get());
        // Generally valid assumption here that inertia change isn't a factor (missing dI/dt * omega and dm/dt v) this needs to be overridden if not the case
        this.angularAccleration = invInertia.multiply(this.angularAccleration); 
    }

    /**
     * Computes and sets body frame from roll pitch and yaw
     * @param roll
     * @param pitch
     * @param yaw
     */
    public void setAxisFromAngles(double roll, double pitch, double yaw) {
        this.axis.rotationMatrixIntrinsic(yaw,pitch,roll);
    }

    /**
     * 
     */
    protected void setAxisFromAngles() {
        this.axis.rotationMatrixIntrinsic(eulerAngles.z,eulerAngles.y,eulerAngles.x);
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
        this.position.set(position);
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
        this.velocity.set(velocity);
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
        this.acceleration.set(acceleration);
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
        this.eulerAngles.set(eulerAngles);
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
        this.angularVelocity.set(angularVelocity);
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
        this.angularAccleration.set(angularAccleration);
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