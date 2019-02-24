package prj.clark.alchemy.data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * This interface represents {@link Data} capable of being transformed into Java compatible numeric values.
 *
 * Use of this interface should never result in exceptions.
 */
public interface Numeric extends Data {
    BigDecimal arbitraryFloatValue();
    BigInteger arbitraryIntValue();
    long intValue();
    double floatValue();
    boolean isInteger();
}
