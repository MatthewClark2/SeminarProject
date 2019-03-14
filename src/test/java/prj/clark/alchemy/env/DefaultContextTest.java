package prj.clark.alchemy.env;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.err.IllegalRebindingException;

import java.util.Collections;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.compareSequences;

public class DefaultContextTest {
    private Context ctx;

    @Before
    public void setUp() {
        ctx = new DefaultContext();
        ctx.bind("foo", AlchemyBoolean.TRUE);
        ctx.bindFunction("i", (args) -> new EagerAlchemyList(Collections.emptyList()));
    }

    @Test
    public void ableToFindBoundValues() {
        assertTrue(ctx.search("foo").isPresent());
        assertEquals(AlchemyBoolean.TRUE, ctx.search("foo").get());
    }

    @Test
    public void ableToFindBoundFunctions() {
        assertTrue(ctx.search("i").isPresent());
        compareSequences(new EagerAlchemyList(Collections.emptyList()),
                (Sequenced) ((Invokable) (ctx.search("i").get()))
                .invoke(Collections.emptyList()));
    }

    @Test
    public void ableToFindBoundModules() {}

    @Test
    public void unableToFindUnboundValues() {
        assertFalse(ctx.search("k").isPresent());
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotBindIdUsedForFunction() {
        ctx.bind("i", AlchemyInt.of(3));
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotBindIdUsedForModule() {}
}