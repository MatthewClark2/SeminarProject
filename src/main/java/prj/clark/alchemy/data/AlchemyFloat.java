package prj.clark.alchemy.data;

import java.math.BigDecimal;
import java.math.BigInteger;

// TODO(matthew-c21) - Modify this to be the only implementation of Numeric.
public class AlchemyFloat implements Numeric, Printable {
    // This wastes space for the sake of quickly changing between integer and floating point representations.
    private final BigDecimal bfValue;
    private final BigInteger biValue;
    private final long iValue;
    private final double fValue;

    private AlchemyFloat(double f, long i) {
        bfValue = new BigDecimal(f);
        biValue = new BigInteger("" + i);
        iValue = i;
        fValue = f;
    }

    public static Data of(String content) {
        double value = Double.parseDouble(content);
        return of(value);
    }

    public static Data of(double value) {
        return new AlchemyFloat(value, (long) value);
    }

    public static Data of(long value) {
        return new AlchemyFloat((double) value, value);
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
        return null;
    }
}
