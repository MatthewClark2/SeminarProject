package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Numeric;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.err.TypeMismatchException;

import java.lang.reflect.Type;
import java.util.function.BiFunction;

abstract class NumericBinaryOperator extends BinaryOperator {
    protected NumericBinaryOperator(Node left, Node right, BiFunction<Data, Data, Data> op) {
        super(left, right, op);
    }

    @Override
    public Data evaluate(Context ctx) {
        assertNumeric(getLeft().evaluate(ctx));
        assertNumeric(getRight().evaluate(ctx));

        return super.evaluate(ctx);
    }

    private void assertNumeric(Data d) {
        if (! (d instanceof Numeric)) {
            throw new TypeMismatchException();
        }
    }
}
