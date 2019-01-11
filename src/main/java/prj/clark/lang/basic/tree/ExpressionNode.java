package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;
import prj.clark.lang.basic.env.BasicData;

public abstract class ExpressionNode implements Node {
    public abstract BasicData evaluate(BasicContext ctx);

    @Override
    public void execute(BasicContext ctx) {
        // Do nothing, as this is an expression.
    }
}
