package prj.clark.lang.impl.env;

import prj.clark.lang.impl.err.FunctionInvocationException;
import prj.clark.lang.impl.err.LangException;
import prj.clark.lang.impl.tree.StatementListNode;

import java.util.List;

/**
 * Used for functions that are created at runtime.
 */
public class GeneratedFunction implements Function {
    private final StatementListNode functionBody;
    private final Context enclosingContext;
    private final int argCount;
    private final List<String> arguments;

    /**
     * Create a new GeneratedFunction that may be utilized.
     * @param functionBody the actual body of the function, given as a single node.
     * @param enclosingContext the surrounding context of the function.
     * @param arguments the names of all bound parameters. The length of this List should be the same as argCount.
     */
    public GeneratedFunction(StatementListNode functionBody, Context enclosingContext, List<String> arguments) {
        // TODO(matthew-c21) - Consider taking copies rather than pointers.
        this.functionBody = functionBody;
        this.enclosingContext = enclosingContext;
        this.arguments = arguments;
        this.argCount = arguments.size();
    }

    @Override
    public Data apply(List<Data> args) throws LangException {
        // TODO(matthew-c21) -
        if (args.size() < argCount) {
            // return a partial function.
            return null;
        } else if (args.size() > argCount) {
            throw new FunctionInvocationException("Too many arguments supplied to function.");
        } else {
            // Apply the function using the given function body.
            return null;
        }
    }

    @Override
    public DataType getType() {
        return RawFunction.getInstance();
    }
}
