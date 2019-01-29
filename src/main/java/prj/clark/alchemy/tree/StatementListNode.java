package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Empty;

import java.util.List;

public class StatementListNode implements Node {

    private final List<Node> statements;

    public StatementListNode(List<Node> statements) {
        this.statements = statements;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data d = Empty.get();
        for (Node n : statements) {
            d = n.evaluate(ctx);
        }

        return d;
    }
}
