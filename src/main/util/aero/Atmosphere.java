package main.util.aero;

import main.util.Cartesian;

/**
 * Interface for any Atmosphere (somewhat simplified for what's necessary for Aerodynamics)
 */
public interface Atmosphere {

    public static final double GAS_CONSTANT = 8.31446261815324; // J / K mol
    public static final double SL_DENSITY = 1.225; // kg / m3
    public static final double ATM2PA = 101325; 
    public static final double SL_TEMPERATURE = 298; // K
    public static final double KELVIN_0C = 273.15;
    public static final double AIR_SPECIFIC_HEAT_RATIO = 1.4;
    public static final double AIR_MOLECULAR_WEIGHT = 0.02897; // kg / mol
    public static final double STD_GRAVITY = 9.806; // m/s2

    /**
     * Set the altitude
     */
    public void setAltitude(double h);

    /**
     * Set the longitude and latitude (generally not used)
     * @param latitude
     * @param longitude
     */
    public void setLatitudeLongitude(double latitude, double longitude);

    /**
     * Gets the density in kg/m3
     * @return
     */
    public double getDensity(); 

    /**
     * Gets the temperature in Kelvin
     * @return
     */
    public double getTemperature(); 

    /**
     * Gets the pressure in Pascal
     * @return
     */
    public double getPressure(); 

    /**
     * Gets the wind vector in m/s
     * @return
     */
    public Cartesian getWind();

    /**
     * Gets the speed of sound in m/s
     * @return
     */
    public double getSpeedOfSound();

    /**
     * Gets the dynamic viscosity in Pa-s
     * @return
     */
    public double getDynamicViscosity(); 

    /**
     * Gets the molecular weight
     * @return
     */
    public double getMolecularWeight();

    /**
     * Gets the specific heat ratio
     * @return
     */
    public double getSpecificHeatRatio();

    /**
     * Basic dynamic viscosity calc at 1 atm
     * @param temperature
     * @return
     */
    public static double getDynamicViscosity(double temperature) {
        return (5.058e-2-1.295e-5*temperature)*temperature + 4.371;
    }


}