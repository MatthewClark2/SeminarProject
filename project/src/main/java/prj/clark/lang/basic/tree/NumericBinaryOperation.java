package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicData;
import prj.clark.lang.basic.env.DataType;

abstract class NumericBinaryOperation extends BinaryOperation {
    protected void ensureNumeric(ExpressionNode e1, ExpressionNode e2) {
        ensureNumeric(e1);
        ensureNumeric(e2);
    }

    private void ensureNumeric(ExpressionNode e) {
        BasicData data = e.evaluate();
        if (data.getType() == DataType.STRING) {
            throw new IllegalArgumentException("Expected value of numeric type, found string: " + data.getContent());
        }
    }
}
