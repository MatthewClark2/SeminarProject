package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.GeneratedFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Yields a {@link GeneratedFunction} when evaluated.
 */
// TODO(matthew-c21) - Update the name to be more along the lines of FunctionLiteral.
public class FunctionLiteralNode extends ReferentiallyTransparentValuedNode {
    private final Valued body;
    private final List<String> parameters;
    private final List<BindingNode> withBlock;

    public FunctionLiteralNode(Valued body, List<String> parameters, List<BindingNode> withBlock) {
        this.body = body;
        this.parameters = parameters;
        this.withBlock = withBlock;
    }

    @Override
    public Data evaluate(Context ctx) {
        return new GeneratedFunction(body, ctx, parameters, withBlock);
    }
}
