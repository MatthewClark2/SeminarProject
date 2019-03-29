package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.ArrayList;
import java.util.List;

public class ConcatenationNode extends ReferentiallyTransparentValuedNode {
    private final Valued left;
    private final Valued right;

    public ConcatenationNode(Valued left, Valued right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data l = left.evaluate(ctx);
        Data r = right.evaluate(ctx);

        if (l instanceof Chainable) {
            return ConcatenatedSequence.concat((Chainable) l, r);
        } else if (r instanceof Chainable) {
            return ConcatenatedSequence.concat(l, (Chainable) r);
        } else {
            throw new TypeMismatchException();
        }
    }
}
