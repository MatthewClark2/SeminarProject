package prj.clark.lang.impl.env;

import org.junit.Before;
import org.junit.Test;
import prj.clark.lang.impl.err.IllegalRebindingException;

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
    }

    static {
        Map<String, Data> data = new HashMap<>();
        data.put("hello", LangString.of("world"));
        data.put("foo", LangFloat.of(2.5));
        data.put("False", LangBool.of(false));
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
        scoped.bindMutably("True", LangBool.of(true));
        scoped.bindImmutably("name", LangString.of("Matthew"));
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotRebindImmutableValuesImmutably() {
        scoped.bindImmutably("name", LangString.of("Matthew"));
        scoped.bindImmutably("name", LangString.of("Billy"));
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotRebindImmutableValuesMutably() {
        scoped.bindImmutably("name", LangString.of("Matthew"));
        scoped.bindMutably("name", LangString.of("Billy"));
    }

    @Test
    public void mayRebindMutableValuesMutably() {
        scoped.bindMutably("api_endpoint", LangString.of("www.example.com/my-api"));
        scoped.bindMutably("api_endpoint", LangString.of("www.example.com/my-new-api"));
    }

    @Test
    public void mayRebindMutableValuesImmutably() {
        scoped.bindMutably("api_endpoint", LangString.of("www.example.com/my-api"));
        scoped.bindImmutably("api_endpoint", LangString.of("www.example.com/my-new-api"));
    }

    @Test
    public void boundValuesAccurate() {
        scoped.bindMutably("n", LangInt.of(7));
        scoped.bindImmutably("pi", LangFloat.of(Math.PI));
        assertEquals(LangInt.of(7), scoped.search("n").get());
        assertEquals(LangFloat.of(Math.PI), scoped.search("pi").get());
    }

    @Test
    public void reBoundValuesAccurate() {
        scoped.bindMutably("n", LangInt.of(7));
        scoped.bindImmutably("pi", LangFloat.of(Math.PI));
        assertEquals(LangInt.of(7), scoped.search("n").get());
        assertEquals(LangFloat.of(Math.PI), scoped.search("pi").get());

        scoped.bindMutably("n", LangInt.of(8));
        assertEquals(LangInt.of(8), scoped.search("n").get());
    }
}