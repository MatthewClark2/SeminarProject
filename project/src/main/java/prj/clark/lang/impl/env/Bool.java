package prj.clark.lang.impl.env;

public class Bool extends PrimitiveData<Boolean> {
    private Bool(boolean value) {
        super(value, PrimitiveType.BOOL);
    }

    public static Data of(boolean value) {
        return new Bool(value);
    }

    public static Data of(String content) {
        switch (content) {
            case "true": return of(true);
            case "false": return of(false);
            default: throw new IllegalArgumentException();
        }
    }
}
