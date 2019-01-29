package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;

import java.util.function.BiFunction;

public abstract class BinaryOperator extends ReferentiallyTransparentValuedNode {
    private Valued left;
    private Valued right;
    private BiFunction<Data, Data, Data> op;

    protected BinaryOperator(Valued left, Valued right, BiFunction<Data, Data, Data> op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Data evaluate(Context ctx) {
        return op.apply(left.evaluate(ctx), right.evaluate(ctx));
    }

    protected Valued getLeft() {
        return left;
    }

    protected Valued getRight() {
        return right;
    }
}
