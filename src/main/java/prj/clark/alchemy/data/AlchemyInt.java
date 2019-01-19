package prj.clark.alchemy.data;

/**
 * Contains information for integer values. This may be altered in the future
 * to default to arbitrary precision, or there may be several implementations
 * that work with various levels of precision.
 */
public class AlchemyInt extends PrimitiveData<Long> {
    private AlchemyInt(long value) {
        super(value, PrimitiveType.INT);
    }

    public static Data of(long value) {
        return new AlchemyInt(value);
    }

    public static Data of(String content) {
        try {
            return new AlchemyInt(Long.parseLong(content));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
