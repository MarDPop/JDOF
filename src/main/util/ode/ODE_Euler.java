package main.util.ode;

import main.util.Body;

public class ODE_Euler extends ODE {

    int n;

    public ODE_Euler () {
    }

    @Override
    public void setBody(Body body) {
        super.setBody(body);
        this.n = body.getState().length;
    }

    @Override
    public void step() {
        double[] state = body.getState();
        double[] dstate = body.getStateRate();
        for(int i = 0; i < n; i++) {
            state[i] += dstate[i]*dt;
        }
        body.setState(state);
        time += dt; 
    }

}