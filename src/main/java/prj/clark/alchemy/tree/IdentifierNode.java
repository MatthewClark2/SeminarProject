package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.Data;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.NoSuchBindingException;

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
