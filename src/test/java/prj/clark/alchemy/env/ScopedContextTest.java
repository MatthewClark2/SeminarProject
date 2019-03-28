package prj.clark.alchemy.env;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.err.IllegalRebindingException;

import java.util.Optional;

import static org.junit.Assert.*;

public class ScopedContextTest {
    private static final Invokable IDENTITY = EagerAlchemyList::new;
    private static final Invokable F = x -> AlchemyFloat.of(((Numeric)x).floatValue() * 2);

    // Truly immutable context that contains no internal state.
    private static final Context BASE = new Context() {
        @Override
        public void bind(String identifier, Data d) {
            fail();
        }

        @Override
        public Optional<Data> search(String identifier) {
            switch (identifier) {
                case "foo": return Optional.of(AlchemyInt.of(100));
                case "fn":  return Optional.of(IDENTITY);
            }

            return Optional.empty();
        }
    };

    private Context ctx;

    @Before
    public void setUp() {
        ctx = new ScopedContext(BASE);
    }

    @Test
    public void boundValueAvailable() {
        // Already in the context.
        assertTrue(ctx.search("foo").isPresent());
        assertEquals(AlchemyInt.of(100), ctx.search("foo").get());

        // New to the context.
        ctx.bind("msg", AlchemyString.of("Hello"));
        assertTrue(ctx.search("msg").isPresent());
        assertEquals(AlchemyString.of("Hello"), ctx.search("msg").get());
    }

    @Test
    public void unboundValueUnavailable() {
        assertFalse(ctx.search("msg").isPresent());
    }

    @Test
    public void boundFunctionAvailable() {
        assertTrue(ctx.search("fn").isPresent());
        assertEquals(IDENTITY, ctx.search("fn").get());

        ctx.bindFunction("f", F);

        assertTrue(ctx.search("f").isPresent());
        assertEquals(F, ctx.search("f").get());
    }

    @Test(expected = IllegalRebindingException.class)
    public void cannotRebindFunctionToValue() {
        ctx.bindFunction("f", F);

        ctx.bind("f", null);
    }

    @Test(expected = IllegalRebindingException.class)
    public void cannotRebindFunctionToFunction() {
        ctx.bindFunction("f", F);

        ctx.bindFunction("f", null);
    }

    @Test
    public void ableToShadowUnderlyingFunction() {
        ctx.bindFunction("fn", F);
        assertTrue(ctx.search("fn").isPresent());
        assertEquals(F, ctx.search("fn").get());
    }

    @Test
    public void newBindingsShadowOldOnes() {
        assertEquals(AlchemyInt.of(100), ctx.search("foo").get());

        ctx.bind("foo", AlchemyString.of("goodbye"));
        assertEquals(AlchemyString.of("goodbye"), ctx.search("foo").get());
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotRebindValueToFunction() {
        ctx.bind("msg", AlchemyString.of("f"));
        ctx.bindFunction("msg", IDENTITY);
    }

    @Test
    public void mayRebindValueToValue() {
        ctx.bind("msg", AlchemyString.of("yo"));
        ctx.bind("msg", AlchemyString.of(":thinking:"));

        assertTrue(ctx.search("msg").isPresent());
        assertEquals(AlchemyString.of(":thinking:"), ctx.search("msg").get());
    }

    // TODO(matthew-c21) - Test module imports.
}