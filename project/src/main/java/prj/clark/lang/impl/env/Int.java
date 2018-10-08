package prj.clark.lang.impl.env;

/**
 * Contains information for integer values. This may be altered in the future
 * to default to arbitrary precision, or there may be several implementations
 * that work with various levels of precision.
 */
public class Int extends PrimitiveData<Long> {
    private Int(long value) {
        super(value, PrimitiveType.INT);
    }

    public static Data of(long value) {
        return new Int(value);
    }

    public static Data of(String content) {
        try {
            return new Int(Long.parseLong(content));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
