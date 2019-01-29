package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Invokable;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.ArrayList;
import java.util.List;

public class FunctionApplicationNode extends ReferentiallyTransparentValuedNode {
    // This node should either be an identifier that resolves to a function, or a literal that resolves to a function.
    private final Valued function;
    private final List<Valued> appliedArguments;

    public FunctionApplicationNode(Valued function, List<Valued> appliedArguments) {
        this.function = function;
        this.appliedArguments = appliedArguments;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data f = function.evaluate(ctx);

        if (! (f instanceof Invokable)) {
            throw new TypeMismatchException();
        }

        List<Data> args = new ArrayList<>();

        for (Valued n : appliedArguments) {
            args.add(n.evaluate(ctx));
        }

        return ((Invokable) f).invoke(args);
    }
}
