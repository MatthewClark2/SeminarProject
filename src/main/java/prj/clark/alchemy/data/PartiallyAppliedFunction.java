package prj.clark.alchemy.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartiallyAppliedFunction implements Invokable {
    private final List<Data> suppliedArguments;
    private final Invokable baseFunction;

    public PartiallyAppliedFunction(List<Data> suppliedArguments, Invokable baseFunction) {
        this.suppliedArguments = suppliedArguments;
        this.baseFunction = baseFunction;
    }

    @Override
    public Data invoke(List<Data> args) {
        // TODO(matthew-c21) - Determine whether stream concatenation or the creation of a new list is faster.
        return baseFunction.invoke(Stream.concat(suppliedArguments.stream(), args.stream()).collect(Collectors.toList()));
    }

    @Override
    public int parameterCount() {
        return 0;
    }
}