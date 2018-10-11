package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.*;
import prj.clark.lang.impl.env.LangFloat;

import java.util.function.BiFunction;

public abstract class NumericBinaryOperator extends BinaryOperator {
    protected NumericBinaryOperator(Node left, Node right, BiFunction<Data, Data, Data> op) {
        super(left, right, op);
    }

    @Override
    public Data evaluate(Context ctx) {
        assert isNumeric(ctx, getLeft());
        assert isNumeric(ctx, getRight());
        return super.evaluate(ctx);
    }

    private static boolean isNumeric(Context ctx, Node node) {
        DataType type = node.evaluate(ctx).getType();

        return type instanceof LangFloat || type instanceof LangInt;
    }
}
