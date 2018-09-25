package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;
import prj.clark.lang.basic.env.BasicData;

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
            BasicData result = expression.evaluate(ctx);
            char[] bytes = result.getContent().toCharArray();
            ctx.getWriter().write(bytes, 0, bytes.length);
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
