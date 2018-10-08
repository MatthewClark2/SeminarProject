package prj.clark.lang.impl.env;

public class Str extends PrimitiveData<String> {
    private Str(String content) {
        super(content, PrimitiveType.STRING);
    }

    public static Data of(String content) {
        return new Str(content);
    }
}
