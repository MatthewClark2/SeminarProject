package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicData;
import prj.clark.lang.basic.env.DecimalData;

public class ExponentNode extends NumericBinaryOperation {

    @Override
    public BasicData evaluate() {
        ensureNumeric(left, right);
        BasicData leftOperand = left.evaluate();
        BasicData rightOperand = right.evaluate();

        return new DecimalData(
                Math.pow(Double.parseDouble(leftOperand.getContent()), Double.parseDouble(rightOperand.getContent()))
        );
    }
}
