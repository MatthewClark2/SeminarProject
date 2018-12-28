package prj.clark.lang.impl.env;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Serves as a disposable wrapper for classes that require their own execution contexts, such as {@link Function}s.
 * Any bindings made through this class do not affect the originally supplied {@link Context}, although changes in the
 * original may be reflected through the wrapper.
 */
public class ScopedContext implements Context {
    // The outer map contains everything into a module like structure. This is currently unused.
    private Map<String, Map<String, Data>> mutableBindings;
    private Map<String, Map<String, Data>> immutableBindings;
    private Context original;
    // Consider injecting this.
    private Context current;

    private static final String MODULE = ".";

    public ScopedContext(Context original) {
        this.original = original;
        mutableBindings = new HashMap<>();
        immutableBindings = new HashMap<>();

        mutableBindings.put(MODULE, new HashMap<>());
        immutableBindings.put(MODULE, new HashMap<>());
        current = new DefaultContext();
    }

    @Override
    public void bindMutably(String identifier, Data d) {
        current.bindMutably(identifier, d);
    }

    @Override
    public void bindImmutably(String identifier, Data d) {
        current.bindImmutably(identifier, d);
    }

    @Override
    public boolean isBoundImmutably(String identifier) {
        return current.isBoundImmutably(identifier) || original.isBoundImmutably(identifier);
    }

    @Override
    public Optional<Data> search(String identifier) {
        Optional<Data> originalResult = original.search(identifier);

        if (originalResult.isPresent()) {
            return originalResult;
        }

        return current.search(identifier);
    }
}
