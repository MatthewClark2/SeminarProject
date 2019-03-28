package prj.clark.alchemy.tree;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.AlchemyString;
import prj.clark.alchemy.data.AlchemyTuple;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.DefaultContext;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.*;

public class StatementListNodeTest {
    private int x;
    private final Node INC_X = new Node() {
        @Override
        public void execute(Context ctx) {
            x++;
        }
    };

    @Before
    public void setUp() {
        x = 0;
    }

    @Test
    public void finalValueReturned() {
        Valued v = new StatementListNode(Arrays.asList(i64(5), i64(6), f64(-11.5)));

        assertEquals(AlchemyFloat.of(-11.5), v.evaluate(null));
    }

    @Test
    public void allNodesExecuted() {
        Valued v = new StatementListNode(Arrays.asList(INC_X, INC_X, INC_X, INC_X, INC_X));

        v.evaluate(null);

        assertEquals(5, x);
    }

    @Test
    public void notDiscardedOnExecution() {
        Valued v = new StatementListNode(Arrays.asList(INC_X, INC_X, INC_X, INC_X, INC_X));

        v.execute(null);

        assertEquals(5, x);
    }

    @Test
    public void emptyReturnsEmptyTuple() {
        Valued v = new StatementListNode(Collections.emptyList());

        assertEquals(new AlchemyTuple(Collections.emptyList()), v.evaluate(null));
    }

    @Test
    public void noValuedNodesReturnsEmptyTuple() {
        Valued v = new StatementListNode(Arrays.asList(INC_X, INC_X, INC_X, INC_X, INC_X, INC_X, INC_X));

        assertEquals(new AlchemyTuple(Collections.emptyList()), v.evaluate(null));
        assertEquals(7, x);
    }

    @Test
    public void scopedContextUsed() {
        Context ctx = new DefaultContext();
        ctx.bind("name", AlchemyString.of("Matthew"));

        Node bind = new BindingNode("name", str("C."), BindingNode.BindingType.VALUE);

        Valued v = new StatementListNode(Arrays.asList(bind, new IdentifierNode("name")));

        assertEquals(AlchemyString.of("C."), v.evaluate(ctx));

        assertEquals(AlchemyString.of("Matthew"), ctx.search("name").get());
    }
}