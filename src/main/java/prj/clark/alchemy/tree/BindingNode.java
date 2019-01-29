package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;

public class BindingNode extends ReferentiallyTransparentValuedNode {
    private final String identifier;
    private final Valued body;
    private final boolean isMutableBinding;

    public BindingNode(String identifier, Valued body, boolean isMutableBinding) {
        this.identifier = identifier;
        this.body = body;
        this.isMutableBinding = isMutableBinding;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data d = body.evaluate(ctx);
        if (isMutableBinding) {
            ctx.bindMutably(identifier, d);
        } else {
            ctx.bindImmutably(identifier, d);
        }

        // TODO(matthew-c21) - Ensure that this is the correct return value.
        return d;
    }
}