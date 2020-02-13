package main.util;

/**
 * Interface that describes an action (force and moment) on the center of gravity
 */
public interface Action {

    /**
     * Gets the force (a) and moment (b) 
     * Force are expected in Global Frame (THIS IS CONTESTED), Moments in Body Frame
     * @return
     */
    public MyPair<Cartesian,Cartesian> getAction();

}