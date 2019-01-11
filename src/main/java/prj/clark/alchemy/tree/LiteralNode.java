package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.Data;
import prj.clark.alchemy.err.LangException;

public class LiteralNode implements Node {
    private Data data;

    public LiteralNode(Data data) {
        this.data = data;
    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        return data;
    }
}
