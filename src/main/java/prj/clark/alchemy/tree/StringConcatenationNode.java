package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyString;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.TypeMismatchException;

public class StringConcatenationNode extends ReferentiallyTransparentValuedNode {
    private final Valued left;
    private final Valued right;

    public StringConcatenationNode(Valued left, Valued right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data l = left.evaluate(ctx);
        Data r = right.evaluate(ctx);

        if (! (l instanceof AlchemyString && r instanceof AlchemyString)) {
            throw new TypeMismatchException();
        }

        return AlchemyString.of(l.toString() + r.toString());
    }
}
