package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Invokable;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.ArrayList;
import java.util.List;

public class FunctionApplicationNode implements Valued {
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

    @Override
    public void execute(Context ctx) {
        // Functions shouldn't mutate external state. However, since the language does not distinguish between mutating
        // and non-mutating functions, both types need to be executed in order to have reliable behavior.
        evaluate(ctx);
    }

    /**
     * This method is used by the feed operators since they modify the given arguments for an applied function.
     * @return a copy of the list containing the supplied arguments.
     */
    List<Valued> getAppliedArguments() {
        return new ArrayList<>(appliedArguments);
    }
}
