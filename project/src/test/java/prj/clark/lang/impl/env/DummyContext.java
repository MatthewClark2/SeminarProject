package prj.clark.lang.impl.env;

import java.util.Optional;

/**
 * This context is useful for dealing with Nodes that require no execution context in order to be evaluated, and is only
 * for testing those nodes.
 */
public class DummyContext implements Context {
    @Override
    public void bindMutably(String identifier, Data d) {

    }

    @Override
    public void bindImmutably(String identifier, Data d) {

    }

    @Override
    public Optional<Data> search(String identifier) {
        return Optional.empty();
    }
}