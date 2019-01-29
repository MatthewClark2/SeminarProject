package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Empty;

import java.util.List;

public class StatementListNode extends ReferentiallyTransparentValuedNode {

    private final List<Valued> statements;

    public StatementListNode(List<Valued> statements) {
        this.statements = statements;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data d = Empty.get();
        for (Valued n : statements) {
            d = n.evaluate(ctx);
        }

        return d;
    }
}
