package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;

import java.util.function.BiFunction;

public abstract class BinaryOperator implements Node {
    private Node left;
    private Node right;
    private BiFunction<Data, Data, Data> op;

    protected BinaryOperator(Node left, Node right, BiFunction<Data, Data, Data> op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Data evaluate(Context ctx) {
        return op.apply(left.evaluate(ctx), right.evaluate(ctx));
    }

    protected Node getLeft() {
        return left;
    }

    protected Node getRight() {
        return right;
    }
}
