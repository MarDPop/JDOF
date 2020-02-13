package main.util;

public class MyPair<T,U> {

    public T a;

    public U b;

    public MyPair(){}

    public MyPair(T a, U b) {
        this.a = a;
        this.b = b;
    }
}