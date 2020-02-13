package main.util;

/**
 * Class to hold a property, generally used to link values for a state.
 * @param <T>
 */
public class Property<T> {

    /**
     * Value of property. While not possible to explicitly call out, it is strongly encouraged to only use numeric values for states
     */
    public T value;

    /**
     * Name of property
     */
    public final String name;

    // Constructors

    /**
     * Empty Constructor
     */
    public Property() {
        this.name = null;
    }

    public Property(T value) {
        this.value = value;
        this.name = null;
    }

    public Property(String name) {
        this.name = name;
    }

    public Property(T value, String name) {
        this.value = value;
        this.name = name;
    }
}