package main.util;

/**
 * Just a holder class to reference an integer value.
 * 128 bit overhead to create class for a 1 bit value but if link is necessary this is the way to do it since no pointers to primitives in java.
 * significant (~2x) performance increase to be able to use primitives instead of generic type class 
 */
public class BooleanValue {

    public boolean val;

    public BooleanValue() {}

    public BooleanValue(boolean val) {
        this.val = val;
    }

}