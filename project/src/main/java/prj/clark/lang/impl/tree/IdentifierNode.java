package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.env.Empty;
import prj.clark.lang.impl.err.LangException;
import prj.clark.lang.impl.err.NoSuchBindingException;

public class IdentifierNode implements Node {
    private final String identifier;

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    // TODO(matthew-c21) - Come back to fix this one after Context has been fleshed out somewhat.
    @Override
    public Data evaluate(Context ctx) throws LangException {
        return ctx.search(identifier).orElseThrow(NoSuchBindingException::new);
    }
}
