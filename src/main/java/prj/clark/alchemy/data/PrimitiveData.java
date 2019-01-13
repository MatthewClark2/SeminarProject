package prj.clark.alchemy.data;

/**
 * This class serves as a base for all primitive data types.
 * @param <T> the type of element stored as a primitive. This should only be
 *           String, a numeric type, or boolean.
 */
// TODO(matthew-c21) - Consider caching some values to reduce object overhead.
public abstract class PrimitiveData<T> implements Data {
    private final T value;
    private final String content;
    private final DataType type;

    protected PrimitiveData(T value, DataType type) {
        this(value, value.toString(), type);
    }

    protected PrimitiveData(T value, String content, DataType type) {
        this.value = value;
        this.content = content;
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    @Override
    public DataType getType() {
        return type;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PrimitiveData) {
            PrimitiveData p = (PrimitiveData) o;
            return value.equals(p.value);
        }

        return false;
    }
}
