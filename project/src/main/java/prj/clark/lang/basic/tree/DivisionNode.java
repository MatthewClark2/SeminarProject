package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.*;

public class DivisionNode extends NumericBinaryOperation {

    @Override
    public BasicData evaluate(BasicContext ctx) {
        ensureNumeric(ctx, left, right);
        BasicData leftOperand = left.evaluate(ctx);
        BasicData rightOperand = right.evaluate(ctx);

        if (leftOperand.getType() == DataType.DECIMAL || rightOperand.getType() == DataType.DECIMAL) {
            return new DecimalData(
                    Double.parseDouble(leftOperand.getContent()) / Double.parseDouble(rightOperand.getContent())
            );
        } else {
            return new IntegerData(
                    Long.parseLong(leftOperand.getContent()) / Long.parseLong(rightOperand.getContent())
            );
        }
    }
}
