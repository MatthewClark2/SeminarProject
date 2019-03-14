package prj.clark.alchemy.env;

import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Invokable;
import prj.clark.alchemy.err.IllegalRebindingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class serves as the de facto implementation for {@link Context}, and is safe to use as a basis for other
 * contexts.
 */
public class DefaultContext implements Context {
    private Map<String, Data> basicBindings;
    private Map<String, Data> functions;
    private Map<String, Data> imports;

    public DefaultContext() {
        // TODO(matthew-c21) - Since this is the "default", prepopulate it.
        basicBindings = new HashMap<>();
        functions = new HashMap<>();
        imports = new HashMap<>();
    }

    @Override
    public void bind(String identifier, Data d) {
        ensureUnused(identifier);

        basicBindings.put(identifier, d);
    }

    @Override
    public void bindFunction(String identifier, Invokable i) {
        ensureUnused(identifier);

        functions.put(identifier, i);
    }

    @Override
    public Optional<Data> search(String identifier) {
        if (basicBindings.containsKey(identifier)) {
            return Optional.of(basicBindings.get(identifier));
        } else if (functions.containsKey(identifier)) {
            return Optional.of(functions.get(identifier));
        } else if (imports.containsKey(identifier)) {
            return Optional.of(imports.get(identifier));
        }

        return Optional.empty();
    }

    private void ensureUnused(String identifier) {
        if (functions.containsKey(identifier) || imports.containsKey(identifier)) {
            throw new IllegalRebindingException(identifier + " already in use.");
        }
    }
}
