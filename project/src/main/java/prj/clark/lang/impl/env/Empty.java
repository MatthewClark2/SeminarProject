package prj.clark.lang.impl.env;

// TODO(matthew-c21) - This should actually be an empty Tuple instead of its own null fill-in. 
public class Empty implements Data {
    private static final Data EMPTY;

    static {
        EMPTY = new Empty();
    }

    public static Data get() {
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
