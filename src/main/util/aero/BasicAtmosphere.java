package main.util.aero;

import main.util.Cartesian;

/**
 * Very basic atmosphere class based on scale height
 */
public class BasicAtmosphere implements Atmosphere {

    private double height;

    /**
     * Scale Height
     */
    public final double scaleHeight;
    public final double slDensity;
    public final double slTemperature;

    private double speedOfSound;

    public BasicAtmosphere() {
        this.scaleHeight = Atmosphere.GAS_CONSTANT/Atmosphere.AIR_MOLECULAR_WEIGHT*Atmosphere.SL_TEMPERATURE/Atmosphere.STD_GRAVITY;
        this.slDensity = Atmosphere.SL_DENSITY;
        this.slTemperature = Atmosphere.SL_TEMPERATURE;
        this.speedOfSound = Math.sqrt(1.4*Atmosphere.GAS_CONSTANT/Atmosphere.AIR_MOLECULAR_WEIGHT*slTemperature);
    }

    /**
     * Constructor for basic air atmosphere
     * @param scaleHeight
     * @param slDensity
     * @param slTemperature
     */
    public BasicAtmosphere(double scaleHeight, double slDensity, double slTemperature) {
        this.scaleHeight = scaleHeight;
        this.slDensity = slDensity;
        this.slTemperature = slTemperature;
        this.speedOfSound = Math.sqrt(1.4*Atmosphere.GAS_CONSTANT/Atmosphere.AIR_MOLECULAR_WEIGHT*slTemperature);
    } 

    @Override
    public void setAltitude(double h) {
        this.height = h;
    }

    @Override
    public double getDensity() {
        return this.slDensity*Math.exp(-height/scaleHeight);
    }

    @Override
    public double getTemperature() {
        return this.slTemperature;
    }

    @Override
    public double getPressure() {
        return getDensity()*Atmosphere.GAS_CONSTANT/Atmosphere.AIR_MOLECULAR_WEIGHT*this.slTemperature;
    }

    @Override
    public Cartesian getWind() {
        return null;
    }

    @Override
    public double getSpeedOfSound() {
        return this.speedOfSound;
    }

    @Override
    public double getDynamicViscosity() {
        return 18e-6;
    }

    @Override
    public void setLatitudeLongitude(double latitude, double longitude) { }

    @Override
    public double getMolecularWeight() {
        return Atmosphere.AIR_MOLECULAR_WEIGHT;
    }

    @Override
    public double getSpecificHeatRatio() {
        return Atmosphere.AIR_SPECIFIC_HEAT_RATIO;
    }

}