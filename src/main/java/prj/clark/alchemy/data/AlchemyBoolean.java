package prj.clark.alchemy.data;

public class AlchemyBoolean implements Printable {
    public static final AlchemyBoolean TRUE;
    public static final AlchemyBoolean FALSE;

    private final boolean value;

    static {
        TRUE = new AlchemyBoolean(true);
        FALSE = new AlchemyBoolean(false);
    }

    private AlchemyBoolean(boolean value) {
        this.value = value;
    }

    public static AlchemyBoolean of(boolean value) {
        return value ? TRUE : FALSE;
    }

    public static AlchemyBoolean of(String content) {
        switch (content) {
            case "True": return of(true);
            case "False": return of(false);
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean toBoolean() {
        return value;
    }

    @Override
    public String print() {
        return value ? "True" : "False";
    }

    @Override
    public String toString() {
        return print();
    }
}
