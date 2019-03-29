package prj.clark.alchemy.tree;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.DefaultContext;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.i64;
import static prj.clark.alchemy.TestUtils.str;

public class FeedFirstNodeTest {
    // f(a, b, c) = a/b - c;
    private Invokable F;

    private static Data feedFirst(Data f, Data v) {
        return feedFirst(new LiteralNode(f), new LiteralNode(v));
    }

    private static Data feedFirst(Valued f, Valued v) {
        return new FeedFirstNode(v, f).evaluate(new DefaultContext());
    }

    @Before
    public void setUp() {
        F = new GeneratedFunction(
                new StatementListNode(
                        Collections.singletonList(
                                new SubtractionNode(
                                        new DivisionNode(
                                                new IdentifierNode("a"),
                                                new IdentifierNode("b")),
                                        new IdentifierNode("c")))),
                new DefaultContext(),
                Arrays.asList("a", "b", "c"));
    }

    @Test
    public void tripleFeedFirstSameAsCallingFunction() {
        Data a = AlchemyInt.of(100);
        Data b = AlchemyInt.of(4);
        Data c = AlchemyInt.of(11);

        // f(a, b, c) = 14.0
        Data f = feedFirst(F, a);
        assertTrue(f instanceof Invokable);

        f = feedFirst(f, b);
        assertTrue(f instanceof Invokable);

        f = feedFirst(f, c);

        assertEquals(AlchemyFloat.of(14), f);
    }

    @Test
    public void feedIntoPartialFunction() {
        Data a = AlchemyInt.of(78);
        Data b = AlchemyInt.of(3);
        Data c = AlchemyInt.of(-4);

        Data f = F.invoke(Arrays.asList(a, b));

        assertEquals(AlchemyFloat.of(30), feedFirst(f, c));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotPassNonInvokableAsFirstArgument() {
        feedFirst(str("Hello"), i64(5));
    }
}