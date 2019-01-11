package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.LangException;
import prj.clark.lang.alchemy.env.*;

import static org.junit.Assert.*;

public class ConditionalTest {
    private static final Node TRUTHY = new LiteralNode(LangBool.of(true));
    private static final Node FALSEY = new LiteralNode(LangBool.of(false));

    // Some garbage data to test against.
    private static final Data RESULT = LangInt.of(3);
    private static final Node DATA = new LiteralNode(RESULT);
    private static final Node FAILURE = new FailNode();
    private static final Context ctx = new DummyContext();

    // The use of the failure node ensures that the extra condition is not evaluated.
    private static class FailNode implements Node {
        @Override
        public Data evaluate(Context ctx) throws LangException {
            fail();

            // Unreachable.
            return null;
        }
    }

    @Test
    public void trueCaseExecutesFirstNode() throws LangException {
        Node n = new Conditional(DATA, FAILURE, TRUTHY);
        Assert.assertEquals(RESULT, n.evaluate(ctx));
    }

    @Test
    public void falseCaseExecutesSecondNode() throws LangException {
        Node n = new Conditional(FAILURE, DATA, FALSEY);
        Assert.assertEquals(RESULT, n.evaluate(ctx));
    }
}