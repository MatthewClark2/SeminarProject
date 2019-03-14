package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.ConcatenatedSequence;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.EagerAlchemyList;
import prj.clark.alchemy.data.Sequenced;
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

        if (l instanceof Sequenced) {
            // Appending requires that the initial list be exhausted first.
            List<Data> data = new ArrayList<>();
            ((Sequenced) l).iter().forEachRemaining(data::add);
            return new EagerAlchemyList(data);
        } else if (r instanceof Sequenced) {
            // Return a lazy sequence.
            return new ConcatenatedSequence(l, (Sequenced)r);
        } else {
            throw new TypeMismatchException();
        }
    }
}
