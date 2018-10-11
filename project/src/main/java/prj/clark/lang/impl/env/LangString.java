package prj.clark.lang.impl.env;

public class LangString extends PrimitiveData<String> {
    private LangString(String content) {
        super(content, PrimitiveType.STRING);
    }

    public static Data of(String content) {
        return new LangString(content);
    }
}
