package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;

import java.util.List;

public class StatementListNode implements Node {

    private final List<Node> statements;

    public StatementListNode(List<Node> statements) {
        this.statements = statements;
    }

    @Override
    public Data evaluate(Context ctx) {
        return statements.stream()
                .reduce((a, b) -> {
                    a.evaluate(ctx);
                    return b;
                })  // Used to obtain the last value in the stream.
                .orElseThrow(IllegalStateException::new)  // Realistically, this shouldn't occur.
                .evaluate(ctx);
    }
}
