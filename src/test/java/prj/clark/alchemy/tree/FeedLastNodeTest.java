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

public class FeedLastNodeTest {
    // f(a, b, c) = a/b - c;
    private Invokable F;

    private static Data feedLast(Data f, Data v) {
        return feedLast(new LiteralNode(f), new LiteralNode(v));
    }

    private static Data feedLast(Valued f, Valued v) {
        return new FeedLastNode(f, v).evaluate(new DefaultContext());
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
        Data f = feedLast(F, c);
        assertTrue(f instanceof Invokable);

        f = feedLast(f, b);
        assertTrue(f instanceof Invokable);

        f = feedLast(f, a);

        assertEquals(AlchemyFloat.of(14), f);
    }

    @Test
    public void feedIntoPartialFunction() {
        Data a = AlchemyInt.of(78);
        Data b = AlchemyInt.of(3);
        Data c = AlchemyInt.of(-4);

        Data f = feedLast(F, c);

        assertEquals(AlchemyFloat.of(30), ((Invokable)f).invoke(Arrays.asList(a, b)));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotPassNonInvokableAsFirstArgument() {
        feedLast(str("Hello"), i64(5));
    }

}