package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.GeneratedFunction;

import java.util.List;

/**
 * Yields a {@link GeneratedFunction} when evaluated.
 */
// TODO(matthew-c21) - Update the name to be more along the lines of FunctionLiteral.
public class FunctionCreationNode extends ReferentiallyTransparentValuedNode {
    private final Valued body;
    private final List<String> parameters;

    public FunctionCreationNode(Valued body, List<String> parameters) {
        this.body = body;
        this.parameters = parameters;

    }

    @Override
    public Data evaluate(Context ctx) {
        return new GeneratedFunction(body, ctx, parameters);
    }
}
