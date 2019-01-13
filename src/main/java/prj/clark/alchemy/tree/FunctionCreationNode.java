package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.GeneratedFunction;
import prj.clark.alchemy.err.LangException;

import java.util.List;

/**
 * Yields a {@link GeneratedFunction} when evaluated.
 */
public class FunctionCreationNode implements Node {
    private final Node body;
    private final List<String> parameters;

    public FunctionCreationNode(Node body, List<String> parameters) {
        this.body = body;
        this.parameters = parameters;

    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        return new GeneratedFunction(body, ctx, parameters);
    }
}
