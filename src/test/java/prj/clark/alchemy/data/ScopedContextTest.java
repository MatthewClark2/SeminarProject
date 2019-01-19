package prj.clark.alchemy.data;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.DefaultContext;
import prj.clark.alchemy.env.ScopedContext;
import prj.clark.alchemy.err.IllegalRebindingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ScopedContextTest {
    private static final Context ORIGINAL;
    private static Context scoped;

    private static final class FrozenContext implements Context {
        private Map<String, Data> bindings;

        private FrozenContext(Map<String, Data> bindings) {
            this.bindings = bindings;
        }

        @Override
        public void bindMutably(String identifier, Data d) {
            fail();
        }

        @Override
        public void bindImmutably(String identifier, Data d) {
            fail();
        }

        @Override
        public Optional<Data> search(String identifier) {
            return bindings.containsKey(identifier) ? Optional.of(bindings.get(identifier)) : Optional.empty();
        }

        @Override
        public boolean isBoundImmutably(String identifier) {
            return bindings.containsKey(identifier);
        }
    }

    static {
        Map<String, Data> data = new HashMap<>();
        data.put("hello", AlchemyString.of("world"));
        data.put("foo", AlchemyFloat.of(2.5));
        data.put("False", AlchemyBoolean.of(false));
        ORIGINAL = new FrozenContext(data);
    }

    @Before
    public void setUp() {
        scoped = new ScopedContext(ORIGINAL);
    }

    @Test
    public void accessToOriginalBindingsAvailable() {
        assertTrue(scoped.search("hello").isPresent());
        assertTrue(scoped.search("foo").isPresent());
        assertTrue(scoped.search("False").isPresent());
    }

    @Test
    public void accessToOriginalBindingsAccurate() {
        Consumer<String> f = s -> assertEquals(ORIGINAL.search(s).get(), scoped.search(s).get());
        f.accept("hello");
        f.accept("foo");
        f.accept("False");
    }

    @Test
    public void unboundVariablesNotAvailable() {
        assertFalse(scoped.search("bar").isPresent());
        assertFalse(scoped.search("014lksaf").isPresent());
        // Smile emoji.
        assertFalse(scoped.search("\uD83D\uDE0A").isPresent());
    }

    @Test
    public void mayBindValues() {
        scoped.bindMutably("True", AlchemyBoolean.of(true));
        scoped.bindImmutably("name", AlchemyString.of("Matthew"));
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotRebindImmutableValuesImmutably() {
        scoped.bindImmutably("name", AlchemyString.of("Matthew"));
        scoped.bindImmutably("name", AlchemyString.of("Billy"));
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotRebindImmutableValuesMutably() {
        scoped.bindImmutably("name", AlchemyString.of("Matthew"));
        scoped.bindMutably("name", AlchemyString.of("Billy"));
    }

    @Test
    public void mayRebindMutableValuesMutably() {
        scoped.bindMutably("api_endpoint", AlchemyString.of("www.example.com/my-api"));
        scoped.bindMutably("api_endpoint", AlchemyString.of("www.example.com/my-new-api"));
    }

    @Test
    public void mayRebindMutableValuesImmutably() {
        scoped.bindMutably("api_endpoint", AlchemyString.of("www.example.com/my-api"));
        scoped.bindImmutably("api_endpoint", AlchemyString.of("www.example.com/my-new-api"));
    }

    @Test
    public void boundValuesAccurate() {
        scoped.bindMutably("n", AlchemyInt.of(7));
        scoped.bindImmutably("pi", AlchemyFloat.of(Math.PI));
        assertEquals(AlchemyInt.of(7), scoped.search("n").get());
        assertEquals(AlchemyFloat.of(Math.PI), scoped.search("pi").get());
    }

    @Test
    public void reBoundValuesAccurate() {
        scoped.bindMutably("n", AlchemyInt.of(7));
        scoped.bindImmutably("pi", AlchemyFloat.of(Math.PI));
        assertEquals(AlchemyInt.of(7), scoped.search("n").get());
        assertEquals(AlchemyFloat.of(Math.PI), scoped.search("pi").get());

        scoped.bindMutably("n", AlchemyInt.of(8));
        assertEquals(AlchemyInt.of(8), scoped.search("n").get());
    }

    @Test
    public void mutableBindingsNotBoundImmutably() {
        scoped.bindMutably("hi", AlchemyString.of("guy"));
        assertFalse(scoped.isBoundImmutably("hi"));
    }

    @Test
    public void unboundBindingsNotBoundImmutably() {
        assertFalse(scoped.isBoundImmutably("random"));
    }

    @Test
    public void immutableBindingsBoundImmutably() {
        // The nested bindings should be immutable, as they are immutably bound in the original.
        assertTrue(scoped.isBoundImmutably("hello"));
        assertTrue(scoped.isBoundImmutably("foo"));
        assertTrue(scoped.isBoundImmutably("False"));

        scoped.bindImmutably("quxx", AlchemyBoolean.of(true));
        assertTrue(scoped.isBoundImmutably("quxx"));
    }

    @Test
    public void overwritingExternalVariablesMutablyProducesNewValue() {
        Context ctx = new DefaultContext();
        ctx.bindMutably("bar", AlchemyString.of("asdf"));
        Context sc = new ScopedContext(ctx);

        sc.bindMutably("bar", AlchemyString.of("fdsa"));
        assertEquals(AlchemyString.of("fdsa"), sc.search("bar").get());
    }

    @Test
    public void overwritingExternalVariablesImmutablyProducesNewValue() {
        Context ctx = new DefaultContext();
        ctx.bindMutably("bar", AlchemyString.of("asdf"));
        Context sc = new ScopedContext(ctx);

        sc.bindImmutably("bar", AlchemyString.of("fdsa"));
        assertEquals(AlchemyString.of("fdsa"), sc.search("bar").get());
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotOverwriteImmutableExternalValueMutably() {
        Context ctx = new DefaultContext();
        ctx.bindImmutably("bar", AlchemyString.of("asdf"));
        Context sc = new ScopedContext(ctx);

        sc.bindMutably("bar", null);
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotOverwriteImmutableExternalValueImmutably() {
        Context ctx = new DefaultContext();
        ctx.bindImmutably("bar", AlchemyString.of("asdf"));
        Context sc = new ScopedContext(ctx);

        sc.bindImmutably("bar", null);
    }
}