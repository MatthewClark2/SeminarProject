package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Function;
import prj.clark.alchemy.data.RawFunction;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.ArrayList;
import java.util.List;

public class FunctionApplicationNode implements Node {
    // This node should either be an identifier that resolves to a function, or a literal that resolves to a function.
    private final Node function;
    private final List<Node> appliedArguments;

    public FunctionApplicationNode(Node function, List<Node> appliedArguments) {
        this.function = function;
        this.appliedArguments = appliedArguments;
    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        Data f = function.evaluate(ctx);

        if (! f.getType().ofType(RawFunction.getInstance())) {
            throw new TypeMismatchException();
        }

        List<Data> args = new ArrayList<>();

        for (Node n : appliedArguments) {
            args.add(n.evaluate(ctx));
        }

        return ((Function) f).apply(args);
    }
}