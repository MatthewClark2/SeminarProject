package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrintNode implements Node {
    private ExpressionNode expression;

    public PrintNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public void execute(BasicContext ctx) throws IOException {
        ctx.getWriter().write(expression.evaluate().getContent());
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<Node>(){{
            add(expression);
        }};
    }
}
