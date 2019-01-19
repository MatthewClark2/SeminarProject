package prj.clark.alchemy.data;

public class AlchemyBoolean implements Printable {
    public static final Data TRUE;
    public static final Data FALSE;

    private final boolean value;

    static {
        TRUE = new AlchemyBoolean(true);
        FALSE = new AlchemyBoolean(false);
    }

    private AlchemyBoolean(boolean value) {
        this.value = value;
    }

    public static Data of(boolean value) {
        return value ? TRUE : FALSE;
    }

    public static Data of(String content) {
        switch (content) {
            case "true": return of(true);
            case "false": return of(false);
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        // We can do this safely since all instances are either TRUE or FALSE.
        return this == o;
    }

    @Override
    public boolean toBoolean() {
        return value;
    }

    @Override
    public String print() {
        return value ? "True" : "False";
    }
}
