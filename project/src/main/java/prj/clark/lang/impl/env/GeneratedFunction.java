package prj.clark.lang.impl.env;

import prj.clark.lang.impl.err.FunctionInvocationException;
import prj.clark.lang.impl.err.LangException;
import prj.clark.lang.impl.tree.StatementListNode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Used for functions that are created at runtime. This class should conform to the automatic currying in the language.
 * Instances are assumed to be raw, untyped functions.
 * @see RawFunction
 */
public class GeneratedFunction implements Function {
    private final StatementListNode functionBody;
    private final Context enclosingContext;
    private final int argCount;
    private final List<String> arguments;

    // TODO(matthew-c21) - Consider removing this to an external class.
    private static class PartiallyAppliedFunction implements Function {
        private final List<Data> suppliedArguments;
        private final Function baseFunction;

        public PartiallyAppliedFunction(List<Data> suppliedArguments, Function baseFunction) {
            this.suppliedArguments = suppliedArguments;
            this.baseFunction = baseFunction;
        }

        @Override
        public Data apply(List<Data> args) throws LangException {
            // TODO(matthew-c21) - Determine whether stream concatenation or the creation of a new list is faster.
            return baseFunction.apply(Stream.concat(suppliedArguments.stream(), args.stream()).collect(Collectors.toList()));
        }

        @Override
        public DataType getType() {
            return RawFunction.getInstance();
        }
    }

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
        if (args.size() < argCount) {
            // return a partial function.
            return new PartiallyAppliedFunction(args, this);
        } else if (args.size() > argCount) {
            throw new FunctionInvocationException("Too many arguments supplied to function.");
        } else {
            // Apply the function using the given function body.
            // TODO(matthew-c21) - Create the new scoped context here so that it can be discarded after use.
            for (int i = 0; i < args.size(); ++i) {
                enclosingContext.bindMutably(arguments.get(i), args.get(i));
            }

            return functionBody.evaluate(enclosingContext);
        }
    }

    @Override
    public DataType getType() {
        return RawFunction.getInstance();
    }
}
