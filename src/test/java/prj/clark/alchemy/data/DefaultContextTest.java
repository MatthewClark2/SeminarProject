package prj.clark.alchemy.data;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.DefaultContext;
import prj.clark.alchemy.err.IllegalRebindingException;

import static org.junit.Assert.*;

public class DefaultContextTest {
    private Context ctx;

    @Before
    public void setUp() {
        ctx = new DefaultContext();
    }

    @Test
    public void unboundVariablesNotAvailable() {
        assertFalse(ctx.search("bar").isPresent());
        assertFalse(ctx.search("014lksaf").isPresent());
        // Smile emoji.
        assertFalse(ctx.search("\uD83D\uDE0A").isPresent());
    }

    @Test
    public void mayBindValues() {
        ctx.bindMutably("True", LangBool.of(true));
        ctx.bindImmutably("name", LangString.of("Matthew"));
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotRebindImmutableValuesImmutably() {
        ctx.bindImmutably("name", LangString.of("Matthew"));
        ctx.bindImmutably("name", LangString.of("Billy"));
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotRebindImmutableValuesMutably() {
        ctx.bindImmutably("name", LangString.of("Matthew"));
        ctx.bindMutably("name", LangString.of("Billy"));
    }

    @Test
    public void mayRebindMutableValuesMutably() {
        ctx.bindMutably("api_endpoint", LangString.of("www.example.com/my-api"));
        ctx.bindMutably("api_endpoint", LangString.of("www.example.com/my-new-api"));
    }

    @Test
    public void mayRebindMutableValuesImmutably() {
        ctx.bindMutably("api_endpoint", LangString.of("www.example.com/my-api"));
        ctx.bindImmutably("api_endpoint", LangString.of("www.example.com/my-new-api"));
    }

    @Test
    public void boundValuesAccurate() {
        ctx.bindMutably("n", LangInt.of(7));
        ctx.bindImmutably("pi", LangFloat.of(Math.PI));
        assertEquals(LangInt.of(7), ctx.search("n").get());
        assertEquals(LangFloat.of(Math.PI), ctx.search("pi").get());
    }

    @Test
    public void reBoundValuesAccurate() {
        ctx.bindMutably("n", LangInt.of(7));
        ctx.bindImmutably("pi", LangFloat.of(Math.PI));
        assertEquals(LangInt.of(7), ctx.search("n").get());
        assertEquals(LangFloat.of(Math.PI), ctx.search("pi").get());

        ctx.bindMutably("n", LangInt.of(8));
        assertEquals(LangInt.of(8), ctx.search("n").get());
    }

    @Test
    public void mutableBindingsNotBoundImmutably() {
        ctx.bindMutably("hi", LangString.of("guy"));
        assertFalse(ctx.isBoundImmutably("hi"));
    }

    @Test
    public void unboundBindingsNotBoundImmutably() {
        assertFalse(ctx.isBoundImmutably("random"));
    }

    @Test
    public void immutableBindingsBoundImmutably() {
        ctx.bindImmutably("quxx", LangBool.of(true));
        assertTrue(ctx.isBoundImmutably("quxx"));
    }
}