package prj.clark.lang.impl.env;

public class Empty implements DataType {
    private static final Empty EMPTY;

    static {
        EMPTY = new Empty();
    }

    private Empty() {}

    public static Empty get() {
        return EMPTY;
    }

    @Override
    public boolean ofType(DataType dt) {
        return dt == EMPTY;
    }
}
