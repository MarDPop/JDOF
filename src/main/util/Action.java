package main.util;

/**
 * Interface that describes an action (force and moment) on the center of gravity
 */
public interface Action {

    /**
     * Gets the force (a) and moment (b)
     * @return
     */
    public MyPair<Cartesian,Cartesian> getAction();

}