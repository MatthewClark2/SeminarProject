package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;
import prj.clark.lang.basic.env.BasicData;
import prj.clark.lang.basic.env.DataType;

abstract class NumericBinaryOperation extends BinaryOperation {
    protected void ensureNumeric(BasicContext ctx, ExpressionNode e1, ExpressionNode e2) {
        ensureNumeric(ctx, e1);
        ensureNumeric(ctx, e2);
    }

    private void ensureNumeric(BasicContext ctx, ExpressionNode e) {
        BasicData data = e.evaluate(ctx);
        if (data.getType() == DataType.STRING) {
            throw new IllegalArgumentException("Expected value of numeric type, found string: " + data.getContent());
        }
    }
}
