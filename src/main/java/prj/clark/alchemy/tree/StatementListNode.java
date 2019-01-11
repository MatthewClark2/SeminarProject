package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.Data;
import prj.clark.alchemy.env.Empty;
import prj.clark.alchemy.err.LangException;

import java.util.List;

public class StatementListNode implements Node {

    private final List<Node> statements;

    public StatementListNode(List<Node> statements) {
        this.statements = statements;
    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        Data d = Empty.get();
        for (Node n : statements) {
            d = n.evaluate(ctx);
        }

        return d;
    }
}
