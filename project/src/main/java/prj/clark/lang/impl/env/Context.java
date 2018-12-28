package prj.clark.lang.impl.env;

import java.util.Optional;

/**
 * For now, this is just a dummy class for managing the execution context of a
 * script. It only exists to give a type to execution. Managed data includes:
 * - Associated input / output streams
 * - Variables
 */
public interface Context {
    /**
     * Bind a value to a given identifier. This binding will fail if the given identifier is already bound to another
     * value immutably.
     * @param identifier the identifier being bound.
     * @param d the value being bound to the given identifier.
     * @throws prj.clark.lang.impl.err.IllegalRebindingException if the given identifier is already bound immutably.
     */
    void bindMutably(String identifier, Data d);

    /**
     * Bind a value to a given identifier. This binding will fail if the given identifier is already bound to another
     * value immutably. The identifier will not be able to be bound again within the same {@link Context}.
     * @param identifier the identifier being bound.
     * @param d the value being bound to the given identifier.
     * @throws prj.clark.lang.impl.err.IllegalRebindingException if the given identifier is already bound immutably.
     */
    void bindImmutably(String identifier, Data d);

    /**
     * Determine if a given identifier has been bound within the current {@link Context}.
     * @param identifier the name of the identifier being searched.
     * @return an {@link Optional} containing the result of the search.
     */
    Optional<Data> search(String identifier);

    /**
     * Determine if a given identifier is already bound immutably. This method also works as a soft check to see whether
     * or not an invocation to either binding method using the given identifier will work.
     * @param identifier the identifier being checked against.
     * @return false if the identifier is either bound mutably, or not bound at all, and true otherwise.
     */
    boolean isBoundImmutably(String identifier);
}
