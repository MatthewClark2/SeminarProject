package prj.clark.alchemy.data;

/**
 * This interface represents {@link Data} capable of being transformed into Java compatible numeric values.
 *
 * Use of this interface should never result in exceptions.
 */
public interface Numeric extends Data {
    long intValue();
    double floatValue();
    boolean isInteger();
}
