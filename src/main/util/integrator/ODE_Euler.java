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
        record.header = new String[]{"time","X","Y","Z","Vx","Vy","Vz","Ax","Ay","Az","Roll","Pitch","Yaw","AOA","CL","Pitch Moment","Q","Elevator"};
    }

    @Override
    public void setBody(Body body) {
        super.setBody(body);
        this.n = body.getState().length;
    }

    @Override 
    public void output() {
        if( this.record != null) {
            double[] row = new double[20];
            row[0] = this.time;
            row[1] = this.body.getPosition().x;
            row[2] = this.body.getPosition().y;
            row[3] = this.body.getPosition().z;
            row[4] = this.body.getVelocity().x;
            row[5] = this.body.getVelocity().y;
            row[6] = this.body.getVelocity().z;
            row[7] = this.body.getAcceleration().x;
            row[8] = this.body.getAcceleration().y;
            row[9] = this.body.getAcceleration().z;
            row[10] = (this.body.getEulerAngles().x % MyMath.TWOPI)*MyMath.RAD2DEG;
            row[11] = (this.body.getEulerAngles().y % MyMath.TWOPI)*MyMath.RAD2DEG;
            row[12] = (this.body.getEulerAngles().z % MyMath.TWOPI)*MyMath.RAD2DEG;
            row[13] = ((Vehicle)this.body).getAero().getAngleOfAttack()*MyMath.RAD2DEG;
            row[14] = ((Vehicle)this.body).getAero().getAeroCoefficients().getCL();
            row[15] = this.body.getAngularAcceleration().y;
            row[16] = ((Vehicle)this.body).getAero().getDynamicPressure();
            row[17] = ((Vehicle)this.body).test.getDeflection();
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