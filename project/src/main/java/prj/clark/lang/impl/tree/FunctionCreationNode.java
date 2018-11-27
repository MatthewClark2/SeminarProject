package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.env.GeneratedFunction;
import prj.clark.lang.impl.err.LangException;

import java.util.List;

/**
 * Yields a {@link prj.clark.lang.impl.env.GeneratedFunction} when evaluated.
 */
public class FunctionCreationNode implements Node {
    private final StatementListNode body;
    private final List<String> parameters;

    public FunctionCreationNode(StatementListNode body, List<String> parameters) {
        this.body = body;
        this.parameters = parameters;

    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        return new GeneratedFunction(body, ctx, parameters);
    }
}
