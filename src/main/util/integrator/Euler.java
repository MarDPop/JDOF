package main.util.integrator;

import main.util.Body;
import main.util.SimpleRecorder;

public class Euler extends Integrator {

    int n;

    public SimpleRecorder record = null;

    public Euler () { }

    @Override
    public void setBody(Body body) {
        super.setBody(body);
        this.n = body.getState().length;
        this.record = new SimpleRecorder(body.toString()+"_euler.csv");
        record.header = new String[]{"time","X","Y","Z","Roll","Pitch","Yaw"};
    }

    @Override 
    public void output() {
        if( this.record != null) {
            double[] row = new double[7];
            row[0] = this.time;
            row[1] = this.body.getPosition().x;
            row[2] = this.body.getPosition().y;
            row[3] = this.body.getPosition().z;
            row[4] = this.body.getEulerAngles().x;
            row[5] = this.body.getEulerAngles().y;
            row[6] = this.body.getEulerAngles().z;
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

}