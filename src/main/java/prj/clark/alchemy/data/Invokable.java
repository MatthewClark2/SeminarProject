package prj.clark.alchemy.data;

import java.util.List;

/**
 * This interface represents {@link Data} that can behave like a function.
 */
public interface Invokable extends Data {
    /**
     * Attempt to invoke this object with the given set of arguments.
     * @param args the list of parameters supplied alongside the invocation.
     * @return the result of invocation.
     */
    // TODO(matthew-c21) @throws
    Data invoke(List<Data> args);

    /**
     * Returns the number of parameters required to invoke the function.
     * @return the number of parameters required to invoke the function.
     */
    default int parameterCount() {
        return 0;
    }
}
