package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Invokable;
import prj.clark.alchemy.data.PartiallyAppliedFunction;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FeedLastNode extends ReferentiallyTransparentValuedNode {
    private final Valued left;
    private final Valued right;

    public FeedLastNode(Valued left, Valued right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data l = left.evaluate(ctx);
        Data r = right.evaluate(ctx);

        if (!(l instanceof Invokable)) {
            throw new TypeMismatchException();
        }

        // Only a single trailing argument is added, and we invoke the function with that single trailing argument.
        return new PartiallyAppliedFunction(
                Collections.emptyList(),
                Collections.singletonList(r),
                (Invokable) l)
                .invoke(Collections.emptyList());
    }
}
