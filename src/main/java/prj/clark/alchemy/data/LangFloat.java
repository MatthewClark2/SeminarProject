package prj.clark.alchemy.data;

public class LangFloat extends PrimitiveData<Double> {
    private LangFloat(double value) {
        super(value, PrimitiveType.FLOAT);
    }

    public static Data of(String content) {
        try {
            return of(Double.parseDouble(content));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Data of(double value) {
        return new LangFloat(value);
    }
}
