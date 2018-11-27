package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.env.Empty;
import prj.clark.lang.impl.err.LangException;

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
