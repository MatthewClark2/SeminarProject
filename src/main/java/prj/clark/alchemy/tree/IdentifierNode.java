package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.err.NoSuchBindingException;

public class IdentifierNode extends ReferentiallyTransparentValuedNode {
    private final String identifier;

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    // TODO(matthew-c21) - Come back to fix this one after Context has been fleshed out somewhat.
    @Override
    public Data evaluate(Context ctx) {
        return ctx.search(identifier).orElseThrow(NoSuchBindingException::new);
    }
}
