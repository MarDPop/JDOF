package main.util.aero.atmosphere;

import main.util.Cartesian;

/**
 * US Standard Atmosphere to 71Km (although will continue to provide solution past)
 */
public class StandardAtmosphere implements Atmosphere {

    public static final double[] HEIGHTS =      new double[]{ 0,      11000,  20000,  32000,  47000,  51000,  71000, 1e6 }; //m
    public static final double[] PRESSURES =    new double[]{ 101325, 22632.1,5474.89,868.019,110.906,66.9389,3.95642 }; // Pa
    public static final double[] TEMPERATURES = new double[]{ 288.15, 216.65, 216.65, 228.65, 270.65, 270.65, 214.65 }; // K
    public static final double[] LAPSE_RATE =   new double[]{ -0.0065,0.0   , 0.001,  0.0028, 0.0,    -0.0028,-0.002 }; // K/m

    private int idx = 0;

    private double pressure, temperature;

    private double temperatureOffset;

    public StandardAtmosphere(){};

    public void setTemperatureOffset(double temperatureOffset) {
        this.temperatureOffset = temperatureOffset;
        TEMPERATURES[0] = Atmosphere.SL_TEMPERATURE + temperatureOffset;
        PRESSURES[0] = Atmosphere.ATM2PA*(TEMPERATURES[0]/Atmosphere.SL_TEMPERATURE);
        for(int i = 1; i < 6; i++) {
            if(LAPSE_RATE[i-1] == 0) {
                TEMPERATURES[i] = TEMPERATURES[i-1];
                PRESSURES[i] = PRESSURES[i-1]*Math.exp(CONSTANT2*(HEIGHTS[i]-HEIGHTS[i-1])/TEMPERATURES[i]);
            } else {
                TEMPERATURES[i] = TEMPERATURES[i-1]+LAPSE_RATE[i-1]*(HEIGHTS[i]-HEIGHTS[i-1]);
                PRESSURES[i] = PRESSURES[i-1]*Math.pow(TEMPERATURES[i]/TEMPERATURES[i-1],CONSTANT2/LAPSE_RATE[i-1]);
            }
        }
    }

     /**
     * @return the temperatureOffset
     */
    public double getTemperatureOffset() {
        return this.temperatureOffset;
    }

    @Override
    public void setAltitude(double h) {
        double hg = h*Atmosphere.R_EARTH/(h+Atmosphere.R_EARTH);
        if(hg < HEIGHTS[idx]) {
            if(idx > 0) {
                idx--;
                setAltitude(h);
            } else {
                pressure = 101325*Math.exp(-hg/8500);
                this.temperature = 288.15+this.temperatureOffset;
            }            
        }
        if(hg > HEIGHTS[idx+1]) {
            if(idx < 5) {
                idx++;
                setAltitude(h);
            } else {
                this.temperature = TEMPERATURES[idx]+LAPSE_RATE[idx]*(hg-HEIGHTS[idx]);
                this.pressure = PRESSURES[idx]*Math.pow(this.temperature/TEMPERATURES[idx],CONSTANT2/LAPSE_RATE[idx]);
            }
        }
        if(LAPSE_RATE[idx] == 0) {
            this.temperature = TEMPERATURES[idx];
            this.pressure = PRESSURES[idx]*Math.exp(CONSTANT2*(hg-HEIGHTS[idx])/this.temperature);
        } else {
            this.temperature = TEMPERATURES[idx]+LAPSE_RATE[idx]*(hg-HEIGHTS[idx]);
            this.pressure = PRESSURES[idx]*Math.pow(this.temperature/TEMPERATURES[idx],CONSTANT2/LAPSE_RATE[idx]);
        }
    }

    @Override
    public void setLatitudeLongitude(double latitude, double longitude) {}

    @Override
    public double getDensity() {
        return this.pressure/(Atmosphere.AIR_R*this.temperature);
    }

    @Override
    public double getTemperature() {
        return this.temperature;
    }

    @Override
    public double getPressure() {
        return this.pressure;
    }

    @Override
    public Cartesian getWind() {
        return new Cartesian();
    }

    @Override
    public double getSpeedOfSound() {
        return Math.sqrt(Atmosphere.MACH_CONSTANT*this.temperature);
    }

    @Override
    public double getDynamicViscosity() {
        return Atmosphere.getDynamicViscosity(this.temperature);
    }

    @Override
    public double getMolecularWeight() {
        return Atmosphere.AIR_MOLECULAR_WEIGHT;
    }

    @Override
    public double getSpecificHeatRatio() {
        return Atmosphere.AIR_SPECIFIC_HEAT_RATIO;
    }

}