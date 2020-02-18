package main.util.aero.atmosphere;

import java.util.ArrayList;

import main.util.Cartesian;

public class UniformTableAtmosphere implements Atmosphere {

    private final ArrayList<double[]> data = new ArrayList<>();
    private final ArrayList<double[]> delta = new ArrayList<>();

    private int n = 0;;

    private double minAltitude,maxAltitude,dH;

    // private double temperature,pressure,density,gamma,MW,mach,visc_d,windN,windE;
    //                       0      1         2      3    4   5   6       7     8
    private final double[] row = new double[9];

    public UniformTableAtmosphere() { }

    public UniformTableAtmosphere(String file) {  }

    @Override
    public void setAltitude(double h) {
        double hg = h*Atmosphere.R_EARTH/(h+Atmosphere.R_EARTH);
        if(hg < minAltitude) {
            System.arraycopy(data.get(0), 0, this.row , 0, 9);
            return;
        } 
        if(hg >= maxAltitude) {
            System.arraycopy(data.get(n), 0, this.row , 0, 9);
            return;
        }

        hg /= dH;
        int idx = (int) hg;
        hg -= idx;

        double[] a = data.get(idx);
        double[] b = delta.get(idx);
        for(int i = 9; i-- > 0;) {
            row[i] = a[i] + hg*b[i];
        }
    }


    @Override
    public void setLatitudeLongitude(double latitude, double longitude) {}

    @Override
    public double getDensity() {
        return this.row[2];
    }

    @Override
    public double getTemperature() {
        return this.row[0];
    }

    @Override
    public double getPressure() {
        return this.row[1];
    }

    @Override
    public Cartesian getWind() {
        return new Cartesian(this.row[7],this.row[8],0);
    }

    @Override
    public double getSpeedOfSound() {
        return this.row[5];
    }

    @Override
    public double getDynamicViscosity() {
        return this.row[6];
    }

    @Override
    public double getMolecularWeight() {
        return this.row[4];
    }

    @Override
    public double getSpecificHeatRatio() {
        return this.row[3];
    }


}