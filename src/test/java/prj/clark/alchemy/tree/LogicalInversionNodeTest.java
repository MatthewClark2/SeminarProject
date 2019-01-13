package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.LangBool;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.data.DummyContext;

public class LogicalInversionNodeTest {
    private static final Data TRUE = LangBool.of(true);
    private static final Data FALSE = LangBool.of(false);
    private static final Context ctx = new DummyContext();

    private static Node create(Data d) {
        return new LogicalInversionNode(new LiteralNode(d));
    }

    @Test
    public void convertFalse() throws LangException {
        Node n = create(FALSE);
        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void convertTrue() throws LangException {
        Node n = create(TRUE);
        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }
}