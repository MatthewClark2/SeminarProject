package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;

import java.util.List;

public class AbstractSyntaxTree {
    private List<Node> statements;

    public AbstractSyntaxTree(List<Node> statements) {
        this.statements = statements;
    }

    public void execute(BasicContext ctx) {
        for (Node node : statements) {
            node.execute(ctx);
        }
    }
}
