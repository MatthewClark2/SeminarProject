package prj.clark.lang.impl.env;

public class Empty implements Data {
    private static final Data EMPTY;

    static {
        EMPTY = new Empty();
    }

    public Data get() {
        return EMPTY;
    }

    @Override
    public DataType getType() {
        return EmptyType.get();
    }

    @Override
    public String toString() {
        return "nil";
    }
}
