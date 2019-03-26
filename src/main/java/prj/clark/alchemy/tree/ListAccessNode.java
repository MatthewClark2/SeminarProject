package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Indexed;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.TypeMismatchException;

public class ListAccessNode extends ReferentiallyTransparentValuedNode {
    private Valued coll;
    private Valued index;

    public ListAccessNode(Valued coll, Valued index) {
        this.coll = coll;
        this.index = index;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data c = coll.evaluate(ctx);
        Data i = index.evaluate(ctx);

        if (!(c instanceof Indexed)) {
            throw new TypeMismatchException();
        }

        // TODO(matthew-c21) - Determine when to do a presence check, and what exception should be thrown.
        return ((Indexed) c).getIndex(i).get();
    }
}
