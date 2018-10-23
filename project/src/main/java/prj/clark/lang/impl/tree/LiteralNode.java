package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.err.LangException;

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
