package prj.clark.alchemy.data;

public class AlchemyString extends PrimitiveData<String> {
    private AlchemyString(String content) {
        super(content, PrimitiveType.STRING);
    }

    public static Data of(String content) {
        return new AlchemyString(content);
    }
}
