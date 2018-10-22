package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;

public class IdentifierNode implements Node {
    private final String identifier;

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    // TODO(matthew-c21) - Come back to fix this one after Context has been fleshed out somewhat.
    @Override
    public Data evaluate(Context ctx) {
        return null;
    }
}
