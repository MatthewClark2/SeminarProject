package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;

public class LiteralNode extends ReferentiallyTransparentValuedNode {
    private Data data;

    public LiteralNode(Data data) {
        this.data = data;
    }

    @Override
    public Data evaluate(Context ctx) {
        return data;
    }
}
