package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyTuple;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;

import java.util.Collections;
import java.util.List;

public class StatementListNode implements Valued {

    private final List<Node> statements;

    public StatementListNode(List<Node> statements) {
        this.statements = statements;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data d = new AlchemyTuple(Collections.emptyList());
        for (Node n : statements) {
            if (n instanceof Valued) {
                d = ((Valued) n).evaluate(ctx);
            } else {
                n.execute(ctx);
            }
        }

        return d;
    }

    @Override
    public void execute(Context ctx) {
        for (Node n : statements) {
            n.execute(ctx);
        }
    }
}
