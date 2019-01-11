package prj.clark.alchemy.env;

import prj.clark.alchemy.err.FunctionInvocationException;
import prj.clark.alchemy.err.LangException;

import java.util.List;

/**
 * Represents a function that may be utilized. A function should capture its surrounding scope upon generation.
 * While this class does extend {@link Data}, there is no need to override toString or equals unless it makes absolute
 * sense to do so in implementations of this interface.
 */
public interface Function extends Data {
    /**
     * Apply the function, and return the result of application. If not enough arguments are provided, a new function
     * should be returned in order to match the specified curried behavior.
     * @param args a list of arguments supplied to the function. Should not be null.
     * @return the result of function application.
     * @throws LangException if there is an exception during evaluation.
     * @throws FunctionInvocationException if too many arguments are supplied.
     */
    Data apply(List<Data> args) throws LangException;
}
