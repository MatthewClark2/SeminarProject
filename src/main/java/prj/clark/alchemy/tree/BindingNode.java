package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Empty;
import prj.clark.alchemy.validation.IdentifierValidation;

public class BindingNode implements Node {
    private final String identifier;
    private final Node body;
    private final boolean isMutableBinding;

    public BindingNode(String identifier, Node body, boolean isMutableBinding) {
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