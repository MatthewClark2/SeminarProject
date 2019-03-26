package prj.clark.alchemy.data;

/**
 * Contains information for integer values. This may be altered in the future
 * to default to arbitrary precision, or there may be several implementations
 * that work with various levels of precision.
 */
// TODO(matthew-c21) - Determine whether or not both implementations are required.
public class AlchemyInt implements Numeric, Printable {
    private final double fValue;
    private final long iValue;

    private AlchemyInt(long i, double f) {
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
    public long intValue() {
        return iValue;
    }

    @Override
    public double floatValue() {
        return fValue;
    }

    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public String print() {
        return "" + iValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Numeric) {
            return ((Numeric) o).intValue() == (iValue);
        }

        return false;
    }

    @Override
    public boolean toBoolean() {
        return iValue != 0;
    }

    @Override
    public int hashCode() {
        return (int) iValue;
    }

    @Override
    public String toString() {
        return print();
    }
}
