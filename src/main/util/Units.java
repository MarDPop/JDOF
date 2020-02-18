package main.util;

public class Units {
    
    public static final int SI = 0;
    public static final int IMPERIAL = 1;
    public static final int BRITISH = 2;

    private final int type;

    public Units(int type) {
        this.type = type;
    }
}