package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;
import prj.clark.lang.basic.env.BasicData;

import java.io.IOException;

public abstract class ExpressionNode implements Node {
    public abstract BasicData evaluate();

    @Override
    public void execute(BasicContext ctx) throws IOException {
        // Do nothing, as this is an expression.
    }
}
