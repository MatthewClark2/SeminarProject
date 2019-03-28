package prj.clark.alchemy.tree;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.AlchemyString;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.EagerAlchemyList;
import prj.clark.alchemy.data.Invokable;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.DefaultContext;
import prj.clark.alchemy.err.IllegalRebindingException;

import java.util.Optional;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.*;

public class BindingNodeTest {
    private Context ctx;

    private static final Invokable I = EagerAlchemyList::new;

    @Before
    public void setUp() {
        ctx = new DefaultContext();
        ctx.bind("msg", AlchemyString.of("hello"));
        ctx.bindFunction("list", I);
    }

    @Test(expected = IllegalRebindingException.class)
    public void valueToFunctionExceptionBubblesThrough() {
        BindingNode n = new BindingNode("list", i64(5), BindingNode.BindingType.VALUE);

        n.execute(ctx);
    }

    @Test(expected = IllegalRebindingException.class)
    public void functionToFunctionExceptionBubblesThrough() {
        BindingNode n = new BindingNode("list",
                new LiteralNode(I),
                BindingNode.BindingType.FUNCTION);

        n.execute(ctx);
    }

    @Test(expected = IllegalStateException.class)
    public void cannotBindNonInvokableAsFunction() {
        BindingNode n = new BindingNode("list",
                i64(10),
                BindingNode.BindingType.FUNCTION);

        n.execute(ctx);
    }

    @Test
    public void mayRebindValueToValue() {
        BindingNode n = new BindingNode("msg", str("goodbye"), BindingNode.BindingType.VALUE);

        assertEquals(AlchemyString.of("hello"), ctx.search("msg").get());

        n.execute(ctx);

        assertEquals(AlchemyString.of("goodbye"), ctx.search("msg").get());
    }

    @Test
    public void newlyBoundValueAvailable() {
        BindingNode n = new BindingNode("name", str("Matthew"), BindingNode.BindingType.VALUE);

        assertFalse(ctx.search("name").isPresent());

        n.execute(ctx);

        assertEquals(AlchemyString.of("Matthew"), ctx.search("name").get());
    }

    @Test
    public void newlyBoundFunctionAvailable () {
        BindingNode n = new BindingNode("I",
                new LiteralNode(I),
                BindingNode.BindingType.VALUE);

        assertFalse(ctx.search("I").isPresent());

        n.execute(ctx);

        assertEquals(I, ctx.search("I").get());
    }

    @Test
    public void bindCalledForValue() {
        Context failBindFunction = new Context() {
            @Override
            public void bind(String identifier, Data d) {}

            @Override
            public void bindFunction(String identifier, Invokable i) {
                fail();
            }

            @Override
            public Optional<Data> search(String identifier) {
                return Optional.empty();
            }
        };

        BindingNode n = new BindingNode("x", str(""), BindingNode.BindingType.VALUE);
        n.execute(ctx);
    }

    @Test
    public void bindFunctionCalledForFunction() {
        Context failBind = new Context() {
            @Override
            public void bind(String identifier, Data d) {
                fail();
            }

            @Override
            public Optional<Data> search(String identifier) {
                return Optional.empty();
            }
        };

        BindingNode n = new BindingNode("x",
                new LiteralNode(I),
                BindingNode.BindingType.FUNCTION);

        n.execute(ctx);
    }
}