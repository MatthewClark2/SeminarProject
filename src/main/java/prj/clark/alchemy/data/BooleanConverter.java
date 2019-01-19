package prj.clark.alchemy.data;

/**
 * A basic BooleanConverter transforms primitive objects into booleans. Collections are treated as inherently truthy.
 */
public class BooleanConverter implements Converter {
    @Override
    public Data convert(Data data) {
        if (data == null) {
            return AlchemyBoolean.of(false);
        }

        if (data.getType().ofType(PrimitiveType.STRING)) {
            return AlchemyBoolean.of(! data.toString().isEmpty());
        } else if (data.getType().ofType(PrimitiveType.FLOAT)) {
            AlchemyFloat f = (AlchemyFloat) data;
            return AlchemyBoolean.of(f.getValue() != 0.0);
        } else if (data.getType().ofType(PrimitiveType.INT)) {
            AlchemyInt i = (AlchemyInt) data;
            return AlchemyBoolean.of(i.getValue() != 0);
        } else if (data.getType().ofType(PrimitiveType.BOOL)) {
            return data;
        } else {
            return AlchemyBoolean.of(false);
        }
    }
}
