package prj.clark.alchemy.env;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.AlchemyBoolean;
import prj.clark.alchemy.data.EagerAlchemyList;
import prj.clark.alchemy.err.IllegalRebindingException;

import static org.junit.Assert.*;

public class DefaultContextTest {
    private Context ctx;

    @Before
    public void setUp() {
        ctx = new DefaultContext();
        ctx.bind("foo", AlchemyBoolean.TRUE);
        ctx.bindFunction("i", EagerAlchemyList::new);
    }

    @Test
    public void ableToFindBoundValues() {}

    @Test
    public void ableToFindBoundFunctions() {}

    @Test
    public void ableToFindBoundModules() {}

    @Test
    public void unableToFindUnboundValues() {}

    @Test(expected = IllegalRebindingException.class)
    public void mayNotBindIdUsedForFunction() {}

    @Test(expected = IllegalRebindingException.class)
    public void mayNotBindIdUsedForModule() {}
}