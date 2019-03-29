package prj.clark.alchemy.data;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.ScopedContext;
import prj.clark.alchemy.err.FunctionInvocationException;
import prj.clark.alchemy.tree.BindingNode;
import prj.clark.alchemy.tree.Valued;

import java.util.Collections;
import java.util.List;

/**
 * Used for functions that are created at runtime. This class should conform to the automatic currying in the language.
 */
public class GeneratedFunction implements Invokable {
    private final Valued functionBody;
    private Context enclosingContext;
    private final int argCount;
    private final List<String> arguments;
    private final List<BindingNode> withBlock;

    public GeneratedFunction(Valued functionBody, Context enclosingContext, List<String> arguments) {
        this(functionBody, enclosingContext, arguments, Collections.emptyList());
    }

    /**
     * Create a new GeneratedFunction that may be utilized.
     * @param functionBody the actual body of the function, given as a single valued node.
     * @param enclosingContext the surrounding context of the function. This context cannot be modified by the function.
     * @param arguments the names of all bound parameters. The length of this List should be the same as argCount.
     * @param withBlock extra values to be calculated and bound for function execution. There is no guarantee as to when
     *                 calculation occurs.
     */
    public GeneratedFunction(Valued functionBody, Context enclosingContext, List<String> arguments, List<BindingNode> withBlock) {
        // TODO(matthew-c21) - Consider taking copies rather than pointers.
        this.functionBody = functionBody;
        this.enclosingContext = enclosingContext;
        this.arguments = arguments;
        this.argCount = arguments.size();
        this.withBlock = withBlock;
    }

    @Override
    public Data invoke(List<Data> args) {
        if (args.size() < argCount) {
            // return a partial function.
            return new PartiallyAppliedFunction(args, this);
        } else if (args.size() > argCount) {
            throw new FunctionInvocationException("Too many arguments supplied to function.");
        } else {
            // Create a new scoped context to avoid residual modifications. In theory, this shouldn't matter, but is
            // done for safety anyway.
            enclosingContext = new ScopedContext(enclosingContext);

            // Bind all values.
            for (int i = 0; i < args.size(); ++i) {
                enclosingContext.bind(arguments.get(i), args.get(i));
            }

            for (BindingNode n : withBlock) {
                n.execute(enclosingContext);
            }

            // Execute the function.
            return functionBody.evaluate(enclosingContext);
        }
    }

}
