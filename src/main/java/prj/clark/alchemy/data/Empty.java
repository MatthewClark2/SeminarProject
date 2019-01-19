package prj.clark.alchemy.data;

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
    public String toString() {
        return "nil";
    }
}
