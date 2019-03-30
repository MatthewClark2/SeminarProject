package prj.clark.alchemy.data;

import prj.clark.alchemy.err.FunctionInvocationException;

import java.util.ArrayList;
import java.util.List;

public class PartiallyAppliedFunction implements Invokable {
    private final List<Data> leadingArguments;
    private final Invokable baseFunction;
    private final int totalSuppliedArguments;
    private final List<Data> trailingArguments;

    public PartiallyAppliedFunction(List<Data> leadingArguments, Invokable baseFunction) {
        this(leadingArguments, new ArrayList<>(), baseFunction);
    }

    public PartiallyAppliedFunction(List<Data> leadingArguments, List<Data> trailingArguments, Invokable baseFunction) {
        this.leadingArguments = leadingArguments;
        this.trailingArguments = trailingArguments;
        this.baseFunction = baseFunction;
        this.totalSuppliedArguments = leadingArguments.size() + trailingArguments.size();
        if (totalSuppliedArguments > baseFunction.parameterCount()) {
            throw new IllegalArgumentException("Too many total parameters supplied for given function.");
        }
    }

    @Override
    public Data invoke(List<Data> args) {
        int argCount = totalSuppliedArguments + args.size();

        if (argCount > baseFunction.parameterCount()) {
            throw new FunctionInvocationException();
        } else if (argCount < baseFunction.parameterCount()) {
            List<Data> newLeadingArguments = new ArrayList<>(leadingArguments);
            newLeadingArguments.addAll(args);
            return new PartiallyAppliedFunction(newLeadingArguments, trailingArguments, baseFunction);
        } else {
            List<Data> invokingArgs = new ArrayList<>(leadingArguments);
            invokingArgs.addAll(args);
            invokingArgs.addAll(trailingArguments);
            return baseFunction.invoke(invokingArgs);
        }
    }

    @Override
    public int parameterCount() {
        return baseFunction.parameterCount() - totalSuppliedArguments;
    }
}