package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicData;
import prj.clark.lang.basic.env.DataType;
import prj.clark.lang.basic.env.DecimalData;
import prj.clark.lang.basic.env.IntegerData;

public class DivisionNode extends NumericBinaryOperation {

    @Override
    public BasicData evaluate() {
        ensureNumeric(left, right);
        BasicData leftOperand = left.evaluate();
        BasicData rightOperand = right.evaluate();

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
