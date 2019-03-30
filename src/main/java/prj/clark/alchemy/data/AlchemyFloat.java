package prj.clark.alchemy.data;

// TODO(matthew-c21) - Modify this to be the only implementation of Numeric.
public class AlchemyFloat implements Numeric, Printable {
    // This wastes space for the sake of quickly changing between integer and floating point representations.
    private final long iValue;
    private final double fValue;

    private AlchemyFloat(double f, long i) {
        iValue = i;
        fValue = f;
    }

    public static AlchemyFloat of(String content) {
        double value = Double.parseDouble(content);
        return of(value);
    }

    public static AlchemyFloat of(double value) {
        return new AlchemyFloat(value, (long) value);
    }

    public static AlchemyFloat of(long value) {
        return new AlchemyFloat((double) value, value);
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
        return false;
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();

        sb.append(fValue);

        if (!(Math.abs(fValue) == Double.POSITIVE_INFINITY || sb.toString().contains("."))) {
            sb.append(".0");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Numeric) {
            return ((Numeric) o).floatValue() == (fValue);
        }

        return false;
    }

    @Override
    public boolean toBoolean() {
        return fValue != 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(fValue);
    }

    @Override
    public String toString() {
        return print();
    }

}
