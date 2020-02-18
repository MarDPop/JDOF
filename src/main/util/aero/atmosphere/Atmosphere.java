package main.util.aero.atmosphere;

import main.util.Cartesian;

/**
 * Interface for any Atmosphere (somewhat simplified for what's necessary for Aerodynamics)
 */
public interface Atmosphere {

    public static final double GAS_CONSTANT = 8.31446261815324; // J / K mol
    public static final double SL_DENSITY = 1.225; // kg / m3
    public static final double ATM2PA = 101325; 
    public static final double SL_TEMPERATURE = 288.15; // K
    public static final double KELVIN_0C = 273.15;
    public static final double AIR_SPECIFIC_HEAT_RATIO = 1.4;
    public static final double AIR_MOLECULAR_WEIGHT = 0.02897644; // kg / mol
    public static final double STD_GRAVITY = 9.80665; // m/s2 

    public static final double MACH_CONSTANT = 401.7142086955656; //1.4*Atmosphere.GAS_CONSTANT/Atmosphere.AIR_MOLECULAR_WEIGHT
    public static final double AIR_R = 286.9387204968326; // Atmosphere.GAS_CONSTANT/Atmosphere.AIR_MOLECULAR_WEIGHT
    public static final double R_EARTH = 6371000;
    public static final double CONSTANT2 = -0.03417680953975067;//-Atmosphere.STD_GRAVITY/AIR_R;

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

    /**
     * calculates the geopotential height from geometric
     * @param z
     * @return
     */
    public static double geoPotentialHeightFromGeometric(double z) {
        return z*R_EARTH/(z+R_EARTH);
    }

    /**
     * Calculates the isothermal pressure ratio from base geopotential height
     * @param deltaH
     * @param temperature
     * @return
     */
    public static double isothermalPressureRatio(double deltaH, double temperature) {
        return Math.exp(CONSTANT2*deltaH/temperature);
    }

    /**
     * Calculates the isothermal pressure ratio from base geopotential height
     * @param deltaH
     * @param temperature
     * @return
     */
    public static double gradientPressureRatio(double lapseRate, double T1, double T2) {
        return Math.pow(T2/T1,CONSTANT2/lapseRate);
    }

}