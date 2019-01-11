package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;
import prj.clark.lang.basic.env.BasicData;
import prj.clark.lang.basic.env.DecimalData;

public class ExponentNode extends NumericBinaryOperation {

    @Override
    public BasicData evaluate(BasicContext ctx) {
        ensureNumeric(ctx, left, right);
        BasicData leftOperand = left.evaluate(ctx);
        BasicData rightOperand = right.evaluate(ctx);

        return new DecimalData(
                Math.pow(Double.parseDouble(leftOperand.getContent()), Double.parseDouble(rightOperand.getContent()))
        );
    }
}
