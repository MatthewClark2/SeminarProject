package prj.clark.alchemy.env;

import prj.clark.alchemy.err.IllegalRebindingException;

import java.util.Optional;

/**
 * Serves as a disposable wrapper for classes that require their own execution contexts, such as {@link Function}s.
 * Any bindings made through this class do not affect the originally supplied {@link Context}, although changes in the
 * original may be reflected through the wrapper.
 */
public class ScopedContext implements Context {
    private Context original;
    private Context current;

    private static final String MODULE = ".";

    public ScopedContext(Context original) {
        this.original = original;

        // TODO(matthew-c21) - Change from a DefaultContext to an empty context.
        current = new DefaultContext();
    }

    @Override
    public void bindMutably(String identifier, Data d) {
        if (isBoundImmutably(identifier)) {
            throw new IllegalRebindingException();
        }

        current.bindMutably(identifier, d);
    }

    @Override
    public void bindImmutably(String identifier, Data d) {
        if (isBoundImmutably(identifier)) {
            throw new IllegalRebindingException();
        }

        current.bindImmutably(identifier, d);
    }

    @Override
    public boolean isBoundImmutably(String identifier) {
        return current.isBoundImmutably(identifier) || original.isBoundImmutably(identifier);
    }

    @Override
    public Optional<Data> search(String identifier) {
        Optional<Data> result = current.search(identifier);

        if (result.isPresent()) {
            return result;
        }

        return original.search(identifier);
    }
}
