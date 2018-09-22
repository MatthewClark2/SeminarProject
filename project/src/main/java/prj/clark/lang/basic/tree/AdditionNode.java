package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.*;

// Addition is defined as being overridden for strings, so it's implemented differently than the other operators.
public class AdditionNode extends BinaryOperation {

    @Override
    public BasicData evaluate() {
        BasicData leftOperand = left.evaluate();
        BasicData rightOperand = right.evaluate();

        if (leftOperand.getType() == DataType.STRING || rightOperand.getType() == DataType.STRING) {
            return new StringData(leftOperand.getContent() + rightOperand.getContent());
        }

        if (leftOperand.getType() == DataType.DECIMAL || rightOperand.getType() == DataType.DECIMAL) {
            return new DecimalData(
                    Double.parseDouble(leftOperand.getContent()) - Double.parseDouble(rightOperand.getContent())
            );
        } else {
            return new IntegerData(
                    Long.parseLong(leftOperand.getContent()) - Long.parseLong(rightOperand.getContent())
            );
        }
    }
}
