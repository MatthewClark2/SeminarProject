package prj.clark.lang.impl.env;

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
}