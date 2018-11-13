package prj.clark.lang.impl.env;

import prj.clark.lang.impl.err.LangException;

import java.util.Optional;

/**
 * Serves a similar purpose to the {@link javax.script.Bindings} interface, but with a more specialized purpose with
 * reference to the language being interpreted.
 *
 * As a note, the binding _ is special, and is never actually bound at any point.
 */
public interface Bindings {
    /**
     * Bind or rebind an existing binding. If the given identifier conflicts with an immutable binding, an exception is
     * thrown.
     * @param id the name of the identifier being bound.
     * @param data the value associated with the given identifier.
     * @throws prj.clark.lang.impl.err.IllegalRebindingException if the identifier is already associated with immutable
     * data.
     */
    void bindMutably(String id, Data data) throws LangException;

    /**
     * Create a new binding that cannot be overwritten. If the identifier is already in use in any capacity, an
     * exception is thrown.
     * @param id the name of the identifier being bound.
     * @param data the value associated with the given identifier.
     */
    void bindImmutably(String id, Data data) throws LangException;

    /**
     * Find a piece of data given its identifier.
     * @param id the name of the binding.
     * @return an Optional containing the found data if the binding has been created, or Empty.
     */
    Optional<Data> search(String id) throws LangException;

    /**
     * Find a piece of data given its identifier.
     * @param id the name of the binding.
     * @return the data if it may be found.
     * @throws prj.clark.lang.impl.err.NoSuchBindingException if the given identifier has not been bound.
     */
    Data get(String id) throws LangException;
}
