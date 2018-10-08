package prj.clark.lang.impl.env;

public class Float extends PrimitiveData<Double> {
    private Float(double value) {
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
        return new Float(value);
    }
}
