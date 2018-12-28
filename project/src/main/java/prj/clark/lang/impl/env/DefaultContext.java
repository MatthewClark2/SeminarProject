package prj.clark.lang.impl.env;

import prj.clark.lang.impl.err.IllegalRebindingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static prj.clark.lang.impl.validation.IdentifierValidation.isUnboundIdentifier;
import static prj.clark.lang.impl.validation.IdentifierValidation.validate;

public class DefaultContext implements Context {
    // The outer map contains everything into a module like structure. This is currently unused.
    // TODO(matthew-c21) - Decide on how to manage where bindings originate.
    private Map<String, Map<String, Data>> mutableBindings;
    private Map<String, Map<String, Data>> immutableBindings;

    private static final String MODULE = ".";

    public DefaultContext() {
        // TODO(matthew-c21) - Prepopulate these with any global language constants.
        mutableBindings = new HashMap<>();
        immutableBindings = new HashMap<>();

        mutableBindings.put(MODULE, new HashMap<>());
        immutableBindings.put(MODULE, new HashMap<>());
    }

    @Override
    public void bindMutably(String identifier, Data d) {
        // TODO(matthew-c21) - Decide on whether or not another level of abstraction would be useful here to avoid
        // direct validation.
        if (immutableBindings.get(MODULE).containsKey(identifier)) {
            throw new IllegalRebindingException();
        }

        validate(identifier);
        mutableBindings.get(MODULE).put(identifier, d);
    }

    @Override
    public void bindImmutably(String identifier, Data d) {
        if (immutableBindings.get(MODULE).containsKey(identifier)) {
            throw new IllegalRebindingException();
        }

        validate(identifier);
        immutableBindings.get(MODULE).put(identifier, d);
    }

    @Override
    public Optional<Data> search(String identifier) {
        if (isUnboundIdentifier(identifier)) {
            return Optional.empty();
        }

        if (immutableBindings.get(MODULE).containsKey(identifier)) {
            return Optional.of(immutableBindings.get(MODULE).get(identifier));
        }

        if (mutableBindings.get(MODULE).containsKey(identifier)) {
            return Optional.of(mutableBindings.get(MODULE).get(identifier));
        }

        return Optional.empty();
    }

    @Override
    public boolean isBoundImmutably(String identifier) {
        return immutableBindings.get(MODULE).containsKey(identifier);
    }
}
