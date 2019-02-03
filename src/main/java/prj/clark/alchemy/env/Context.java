package prj.clark.alchemy.env;

import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.err.IllegalRebindingException;

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
     * @throws IllegalRebindingException if the given identifier is already used as an import.
     */
    void bind(String identifier, Data d);

    /**
     * Determine if a given identifier has been bound within the current {@link Context}.
     * @param identifier the name of the identifier being searched.
     * @return an {@link Optional} containing the result of the search.
     */
    Optional<Data> search(String identifier);
}
