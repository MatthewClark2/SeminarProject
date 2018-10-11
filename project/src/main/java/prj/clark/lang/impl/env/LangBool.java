package prj.clark.lang.impl.env;

public class LangBool extends PrimitiveData<Boolean> {
    private LangBool(boolean value) {
        super(value, PrimitiveType.BOOL);
    }

    public static Data of(boolean value) {
        return new LangBool(value);
    }

    public static Data of(String content) {
        switch (content) {
            case "true": return of(true);
            case "false": return of(false);
            default: throw new IllegalArgumentException();
        }
    }
}
