package prj.clark.alchemy.data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Contains information for integer values. This may be altered in the future
 * to default to arbitrary precision, or there may be several implementations
 * that work with various levels of precision.
 */
// TODO(matthew-c21) - Determine whether or not both implementations are required.
public class AlchemyInt implements Numeric, Printable {
    private final BigDecimal bfValue;
    private final BigInteger biValue;
    private final double fValue;
    private final long iValue;

    private AlchemyInt(long i, double f) {
        bfValue = new BigDecimal(f);
        biValue = new BigInteger("" + i);
        fValue = f;
        iValue = i;
    }

    public static AlchemyInt of(long value) {
        return new AlchemyInt(value, (double) value);
    }

    public static AlchemyInt of(String content) {
        try {
            long l = Long.parseLong(content);
            return of(l);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public BigDecimal arbitraryFloatValue() {
        return bfValue;
    }

    @Override
    public BigInteger arbitraryIntValue() {
        return biValue;
    }

    @Override
    public long intValue() {
        return iValue;
    }

    @Override
    public double floatValue() {
        return fValue;
    }

    @Override
    public String print() {
        return biValue.toString();
    }
}
