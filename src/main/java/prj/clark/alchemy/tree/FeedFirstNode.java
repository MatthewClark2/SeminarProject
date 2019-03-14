package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Invokable;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedFirstNode extends ReferentiallyTransparentValuedNode {
    private final Valued left;
    private final Valued right;

    public FeedFirstNode(Valued left, Valued right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data l = left.evaluate(ctx);
        Data r = right.evaluate(ctx);

        if (!(r instanceof Invokable)) {
            throw new TypeMismatchException();
        }

        List<Valued> args = new ArrayList<>();
        args.add(left);

        if (r instanceof FunctionApplicationNode) {
            args.addAll(((FunctionApplicationNode) r).getAppliedArguments());
        }

        return ((Invokable) r).invoke(args.stream().map(x -> x.evaluate(ctx)).collect(Collectors.toList()));
    }
}
