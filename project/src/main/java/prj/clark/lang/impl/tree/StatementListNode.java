package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.err.LangException;

import java.util.List;

public class StatementListNode implements Node {

    private final List<Node> statements;

    public StatementListNode(List<Node> statements) {
        this.statements = statements;
    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        for (int i = 0; i < statements.size() - 1; ++i) {
            statements.get(i).evaluate(ctx);
        }

        return statements.get(statements.size() - 1).evaluate(ctx);
    }
}