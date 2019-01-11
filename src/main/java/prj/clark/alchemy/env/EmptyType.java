package prj.clark.alchemy.env;

public class EmptyType implements DataType {
    private static final EmptyType EMPTY;

    static {
        EMPTY = new EmptyType();
    }

    private EmptyType() {}

    public static EmptyType get() {
        return EMPTY;
    }

    @Override
    public boolean ofType(DataType dt) {
        return dt == EMPTY;
    }
}
