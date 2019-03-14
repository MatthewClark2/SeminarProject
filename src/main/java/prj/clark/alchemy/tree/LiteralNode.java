package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;

/**
 * Serves to wrap literal data as a {@link Node} for evaluation. Supplied {@link Context}s will always be ignored during
 * evaluation, meaning that {@code null} may be safely passed as an argument to {@link #evaluate(Context)} and
 * {@link #execute(Context)}.
 */
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
