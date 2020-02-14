package main.util.integrator;

import main.util.Body;
import main.util.MyMath;
import main.vehicle.*;
import main.util.SimpleRecorder;

public class ODE_Euler extends Integrator {

    int n;

    public SimpleRecorder record = null;

    public ODE_Euler () { 
        this.record = new SimpleRecorder("testfile.csv");
        record.header = new String[]{"time","X","Y","Z","U","V","W","Roll","Pitch","Yaw","AOA","CL","Pitch Moment","Q"};
    }

    @Override
    public void setBody(Body body) {
        super.setBody(body);
        this.n = body.getState().length;
    }

    @Override 
    public void output() {
        if( this.record != null) {
            double[] row = new double[14];
            row[0] = this.time;
            row[1] = this.body.getPosition().x;
            row[2] = this.body.getPosition().y;
            row[3] = this.body.getPosition().z;
            row[4] = this.body.getVelocity().x;
            row[5] = this.body.getVelocity().y;
            row[6] = this.body.getVelocity().z;
            row[7] = (this.body.getEulerAngles().x % MyMath.TWOPI)*MyMath.RAD2DEG;
            row[8] = (this.body.getEulerAngles().y % MyMath.TWOPI)*MyMath.RAD2DEG;
            row[9] = (this.body.getEulerAngles().z % MyMath.TWOPI)*MyMath.RAD2DEG;
            row[10] = ((Vehicle)this.body).getAero().getAngleOfAttack()*MyMath.RAD2DEG;
            row[11] = ((Vehicle)this.body).getAero().getAeroCoefficients().getCL();
            row[12] = this.body.getAngularAcceleration().y;
            row[13] = ((Vehicle)this.body).getAero().getDynamicPressure();
            record.data.add(row);
        }
    }

    @Override
    public void step() {
        body.computeActions();
        double[] state = body.getState();
        double[] dstate = body.getStateRate();
        for(int i = n; i-- > 0;) {
            state[i] += dstate[i]*dt;
        }
        body.setState(state);
        time += dt; 
    }

    @Override
    public void finish() {
        this.record.output();
    }

}