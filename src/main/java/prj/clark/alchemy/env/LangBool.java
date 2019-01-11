package prj.clark.alchemy.env;

public class LangBool extends PrimitiveData<Boolean> {
    private static final Data TRUE;
    private static final Data FALSE;

    static {
        TRUE = new LangBool(true);
        FALSE = new LangBool(false);
    }

    private LangBool(boolean value) {
        super(value, PrimitiveType.BOOL);
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
}
