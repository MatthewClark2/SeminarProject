package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.err.LangException;
import prj.clark.lang.impl.validation.IdentifierValidation;

public class BindingNode implements Node {
    private final String identifier;
    private final Node body;
    private final boolean isMutableBinding;
    private final boolean binds;

    public BindingNode(String identifier, Node body, boolean isMutableBinding) {
        this.identifier = identifier;
        this.body = body;
        this.isMutableBinding = isMutableBinding;
        this.binds = IdentifierValidation.isUnboundIdentifier(this.identifier);
    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        if (binds) {
            Data d = body.evaluate(ctx);
            if (isMutableBinding) {
                ctx.bindMutably(identifier, d);
            } else {
                ctx.bindImmutably(identifier, d);
            }

            return d;
        }

        // Return some empty type.
        return null;
    }
}