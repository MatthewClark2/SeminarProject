package prj.clark.lang.impl.env;

/**
 * This is simply a placeholder interface that is used due to a lack of static type checking negating the need to know
 * the type of arguments that a function takes, or its return type. As a result, all functions that are of this type are
 * considered to be equivalent.
 */
public class RawFunction implements DataType {
    // For the sake of saving a few bytes of memory, this class is implemented as a singleton.
    private static final DataType INSTANCE;

    static {
        INSTANCE = new RawFunction();
    }

    private RawFunction() {}

    public static DataType getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean ofType(DataType dt) {
        return dt instanceof RawFunction;
    }
}
