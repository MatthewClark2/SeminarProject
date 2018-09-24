package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrintNode implements Node {
    private ExpressionNode expression;

    public PrintNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public void execute(BasicContext ctx) {
        try {
            ctx.getWriter().write(expression.evaluate().getContent());
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<Node>(){{
            add(expression);
        }};
    }
}
