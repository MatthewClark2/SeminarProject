package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.env.Context;

public class StringConcatenationNode extends ReferentiallyTransparentValuedNode {

    public StringConcatenationNode(Valued left, Valued right) {

    }

    @Override
    public Data evaluate(Context ctx) {
        return null;
    }
}
