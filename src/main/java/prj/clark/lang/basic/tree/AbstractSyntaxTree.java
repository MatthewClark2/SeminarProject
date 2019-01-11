package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.BasicParser;
import prj.clark.lang.basic.env.BasicContext;
import prj.clark.lang.basic.env.BasicData;

import java.util.ArrayList;
import java.util.List;

public class AbstractSyntaxTree {
    private List<Node> statements;

    public AbstractSyntaxTree(List<Node> statements) {
        this.statements = statements;
    }

    public AbstractSyntaxTree(BasicParser.FileContext ctx) {
        statements = new ArrayList<>();
        for (BasicParser.LineContext line : ctx.line()) {
            statements.add(NodeFactory.getStatement(line.statement()));
        }
    }

    public void execute(BasicContext ctx) {
        for (Node node : statements) {
            node.execute(ctx);
        }
    }

    public BasicData value(BasicContext ctx) {
        if (statements.isEmpty()) {
            return null;
        }

        Node finalStatement = statements.get(statements.size() - 1);

        if (finalStatement instanceof ExpressionNode) {
            return ((ExpressionNode) finalStatement).evaluate(ctx);
        } else {
            // All statement expressions return null.
            return null;
        }
    }
}
