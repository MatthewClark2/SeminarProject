package prj.clark.lang.impl.tree;

import org.junit.Test;
import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.env.DummyContext;
import prj.clark.lang.impl.env.LangBool;
import prj.clark.lang.impl.err.LangException;

import static org.junit.Assert.*;

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
        assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void convertTrue() throws LangException {
        Node n = create(TRUE);
        assertEquals(FALSE, n.evaluate(ctx));
    }
}