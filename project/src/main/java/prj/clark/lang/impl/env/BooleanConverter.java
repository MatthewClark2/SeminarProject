package prj.clark.lang.impl.env;

/**
 * A basic BooleanConverter transforms primitive objects into booleans. Collections are treated as inherently truthy.
 */
public class BooleanConverter implements Converter {
    @Override
    public Data convert(Data data) {
        if (data == null) {
            return LangBool.of(false);
        }

        if (data.getType().ofType(PrimitiveType.STRING)) {
            return LangBool.of(data.toString().isEmpty());
        } else if (data.getType().ofType(PrimitiveType.FLOAT)) {
            LangFloat f = (LangFloat) data;
            return LangBool.of(f.getValue() == 0.0);
        } else if (data.getType().ofType(PrimitiveType.INT)) {
            LangInt i = (LangInt) data;
            return LangBool.of(i.getValue() == 0);
        } else if (data.getType().ofType(PrimitiveType.BOOL)) {
            return data;
        } else {
            return LangBool.of(false);
        }
    }
}
