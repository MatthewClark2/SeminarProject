package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.DataType;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.function.BiFunction;

// TODO(matthew-c21) - Most implementing subclasses directly check to see if the value is a AlchemyInt or AlchemyFloat. Alter
// them to see if the value is of type Int or type Float.
abstract class NumericBinaryOperator extends BinaryOperator {
    protected NumericBinaryOperator(Node left, Node right, BiFunction<Data, Data, Data> op) {
        super(left, right, op);
    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        if (! isNumeric(ctx, getLeft()) || ! isNumeric(ctx, getRight())) {
            throw new TypeMismatchException();
        }

        return super.evaluate(ctx);
    }

    private static boolean isNumeric(Context ctx, Node node) throws LangException {
        DataType type = null;
        type = node.evaluate(ctx).getType();
        return type.equals(PrimitiveType.INT) || type.equals(PrimitiveType.FLOAT);
    }
}
