package prj.clark.alchemy.data;

import org.junit.Test;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.DefaultContext;
import prj.clark.alchemy.err.FunctionInvocationException;
import prj.clark.alchemy.err.TypeMismatchException;
import prj.clark.alchemy.tree.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class GeneratedFunctionTest {
    private static final StatementListNode TRIPLE_ADDITION;

    static {
        TRIPLE_ADDITION = new StatementListNode(Collections.singletonList(
                new AdditionNode(
                        new IdentifierNode("x"),
                        new AdditionNode(
                                new IdentifierNode("y"),
                                new IdentifierNode("z")
                        )
                )
        ));
    }

    private class FailingBody extends StatementListNode {
        private FailingBody() {
            super(null);
        }

        @Override
        public Data evaluate(Context ctx) {
            fail();
            return null;
        }
    }

    @Test
    public void partialFunctionReturnsFunctionObject() {
        Invokable f = new GeneratedFunction(null, new DummyContext(), Arrays.asList("foo", "bar"));
        assertTrue(f.invoke(Collections.singletonList(null)) instanceof Invokable);
    }

    @Test(expected = FunctionInvocationException.class)
    public void tooManyArgumentsThrowsException() {
        Invokable f = new GeneratedFunction(null, new DummyContext(), new ArrayList<>());
        f.invoke(Collections.singletonList(null));
    }

    @Test
    public void statementBodyNotEvaluatedIfPartiallyApplied() {
        Invokable f = new GeneratedFunction(new FailingBody(), new DummyContext(), Arrays.asList("foo", "bar"));
        f.invoke(Collections.singletonList(null));
    }

    @Test(expected = TypeMismatchException.class)
    public void innerExceptionBubblesUp() {
        StatementListNode body = new StatementListNode(
                Collections.singletonList(
                        new AdditionNode(
                                new LiteralNode(AlchemyString.of("")),
                                new LiteralNode(AlchemyString.of(""))
                        )
                )
        );

        Invokable f = new GeneratedFunction(body, new DummyContext(), new ArrayList<>());
        f.invoke(new ArrayList<>());
    }

    @Test
    public void correctValueReturnedFromSingleStatement() {
        StatementListNode body = new StatementListNode(
                Collections.singletonList(
                        new AdditionNode(
                                new LiteralNode(AlchemyInt.of(5)),
                                new LiteralNode(AlchemyInt.of(7))
                        )
                )
        );

        Invokable f = new GeneratedFunction(body, new DummyContext(), new ArrayList<>());
        assertEquals(AlchemyInt.of(12), f.invoke(Collections.emptyList()));
    }

    @Test
    public void correctValueReturnedFromMultipleStatements() {
        StatementListNode body = new StatementListNode(
                Arrays.asList(
                        new AdditionNode(
                                new LiteralNode(AlchemyInt.of(5)),
                                new LiteralNode(AlchemyInt.of(7))
                        ),
                        new SubtractionNode(
                                new LiteralNode(AlchemyInt.of(12)),
                                new LiteralNode(AlchemyInt.of(3))
                        )
                )
        );

        Invokable f = new GeneratedFunction(body, new DummyContext(), new ArrayList<>());
        assertEquals(AlchemyInt.of(9), f.invoke(Collections.emptyList()));
    }

    @Test
    public void emptyBodyEvaluatesToEmptyValue() {
        StatementListNode body = new StatementListNode(Collections.emptyList());
        Invokable f = new GeneratedFunction(body, new DummyContext(), new ArrayList<>());
        assertEquals(new AlchemyTuple(Collections.emptyList()), f.invoke(Collections.emptyList()));
    }

    @Test
    public void functionCorrectlyBindsSingleParameter() {
        StatementListNode body = new StatementListNode(Collections.singletonList(new IdentifierNode("x")));
        Invokable f = new GeneratedFunction(body, new DefaultContext(), Collections.singletonList("x"));
        assertEquals(AlchemyBoolean.of(false), f.invoke(Collections.singletonList(AlchemyBoolean.of(false))));
    }

    @Test
    public void functionCorrectlyBindsMultipleParameters() {
        StatementListNode body = new StatementListNode(
                Collections.singletonList(
                        new AdditionNode(
                                new IdentifierNode("x"),
                                new IdentifierNode("y")
                        )
                )
        );
        Invokable f = new GeneratedFunction(body, new DefaultContext(), Arrays.asList("x", "y"));
        assertEquals(AlchemyInt.of(12), f.invoke(Arrays.asList(AlchemyInt.of(6), AlchemyInt.of(6))));
    }

    private static Invokable triple() {
        return new GeneratedFunction(TRIPLE_ADDITION, new DefaultContext(), Arrays.asList("x", "y", "z"));
    }

    @Test
    public void zeroArgumentsRequiresThreeMore() {
        Invokable f = triple();
        Data d = f.invoke(Collections.emptyList());
        assertTrue(d instanceof Invokable);
        assertEquals(AlchemyInt.of(6), ((Invokable) d).invoke(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2), AlchemyInt.of(3))));
    }

    @Test
    public void oneArgumentRequiresTwoMore() {
        Invokable f = triple();
        Data d = f.invoke(Collections.singletonList(AlchemyInt.of(7)));
        assertTrue(d instanceof Invokable);
        assertEquals(AlchemyInt.of(12), ((Invokable) d).invoke(Arrays.asList(AlchemyInt.of(2), AlchemyInt.of(3))));
    }

    @Test
    public void twoArgumentsRequiresOneMore() {
        Invokable f = triple();
        Data d = f.invoke(Arrays.asList(AlchemyInt.of(11), AlchemyInt.of(-1)));
        assertTrue(d instanceof Invokable);
        assertEquals(AlchemyInt.of(14), ((Invokable) d).invoke(Collections.singletonList(AlchemyInt.of(4))));
    }

    @Test
    public void fullApplicationDoesNotReturnPartial() {
        Invokable f = triple();
        Data d = f.invoke(Arrays.asList(AlchemyInt.of(11), AlchemyInt.of(-1), AlchemyInt.of(9)));
        assertFalse(d instanceof Invokable);
        assertEquals(AlchemyInt.of(19), d);
    }
}