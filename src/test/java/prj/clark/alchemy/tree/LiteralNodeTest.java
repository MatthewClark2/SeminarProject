package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.AlchemyInt;
import prj.clark.alchemy.data.AlchemyString;
import prj.clark.alchemy.data.AlchemyTuple;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.env.DefaultContext;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LiteralNodeTest {
    @Test
    public void valueReturnedLiterally() {
        Data d = AlchemyInt.of(11);
        Valued v = new LiteralNode(d);

        assertEquals(d, v.evaluate(new DefaultContext()));
        assertSame(d, v.evaluate(new DefaultContext()));
    }

    @Test
    public void returnsNull() {
        Valued v = new LiteralNode(null);

        assertNull(v.evaluate(new DefaultContext()));
    }

    @Test
    public void doesNotEvaluateContext() {
        Data d = new AlchemyTuple(Arrays.asList(AlchemyString.of("matt"), AlchemyInt.of(21)));
        Valued v = new LiteralNode(d);

        assertEquals(d, v.evaluate(null));
    }
}