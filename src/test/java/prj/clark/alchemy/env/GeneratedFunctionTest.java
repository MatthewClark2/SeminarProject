package prj.clark.alchemy.env;

import org.junit.Test;
import prj.clark.alchemy.err.FunctionInvocationException;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;
import prj.clark.alchemy.tree.*;
import prj.clark.lang.alchemy.tree.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Note that this class tests with the assumption that all functions are raw.
 */
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
        public Data evaluate(Context ctx) throws LangException {
            fail();
            return null;
        }
    }

    @Test
    public void partialFunctionReturnsFunctionObject() throws LangException {
        Function f = new GeneratedFunction(null, new DummyContext(), Arrays.asList("foo", "bar"));
        assertEquals(RawFunction.getInstance(), f.getType());
        assertEquals(RawFunction.getInstance(), f.apply(Collections.singletonList(null)).getType());
    }

    @Test(expected = FunctionInvocationException.class)
    public void tooManyArgumentsThrowsException() throws LangException {
        Function f = new GeneratedFunction(null, new DummyContext(), new ArrayList<>());
        f.apply(Collections.singletonList(null));
    }

    @Test
    public void statementBodyNotEvaluatedIfPartiallyApplied() throws LangException {
        Function f = new GeneratedFunction(new FailingBody(), new DummyContext(), Arrays.asList("foo", "bar"));
        f.apply(Collections.singletonList(null));
    }

    @Test(expected = TypeMismatchException.class)
    public void innerExceptionBubblesUp() throws LangException {
        StatementListNode body = new StatementListNode(
                Collections.singletonList(
                        new AdditionNode(
                                new LiteralNode(LangString.of("")),
                                new LiteralNode(LangString.of(""))
                        )
                )
        );

        Function f = new GeneratedFunction(body, new DummyContext(), new ArrayList<>());
        f.apply(new ArrayList<>());
    }

    @Test
    public void correctValueReturnedFromSingleStatement() throws LangException {
        StatementListNode body = new StatementListNode(
                Collections.singletonList(
                        new AdditionNode(
                                new LiteralNode(LangInt.of(5)),
                                new LiteralNode(LangInt.of(7))
                        )
                )
        );

        Function f = new GeneratedFunction(body, new DummyContext(), new ArrayList<>());
        assertEquals(LangInt.of(12), f.apply(Collections.emptyList()));
    }

    @Test
    public void correctValueReturnedFromMultipleStatements() throws LangException {
        StatementListNode body = new StatementListNode(
                Arrays.asList(
                        new AdditionNode(
                                new LiteralNode(LangInt.of(5)),
                                new LiteralNode(LangInt.of(7))
                        ),
                        new SubtractionNode(
                                new LiteralNode(LangInt.of(12)),
                                new LiteralNode(LangInt.of(3))
                        )
                )
        );

        Function f = new GeneratedFunction(body, new DummyContext(), new ArrayList<>());
        assertEquals(LangInt.of(9), f.apply(Collections.emptyList()));
    }

    @Test
    public void emptyBodyEvaluatesToEmptyValue() throws LangException {
        StatementListNode body = new StatementListNode(Collections.emptyList());
        Function f = new GeneratedFunction(body, new DummyContext(), new ArrayList<>());
        assertEquals(Empty.get(), f.apply(Collections.emptyList()));
    }

    @Test
    public void functionCorrectlyBindsSingleParameter() throws LangException {
        StatementListNode body = new StatementListNode(Collections.singletonList(new IdentifierNode("x")));
        Function f = new GeneratedFunction(body, new DefaultContext(), Collections.singletonList("x"));
        assertEquals(LangBool.of(false), f.apply(Collections.singletonList(LangBool.of(false))));
    }

    @Test
    public void functionCorrectlyBindsMultipleParameters() throws LangException {
        StatementListNode body = new StatementListNode(
                Collections.singletonList(
                        new AdditionNode(
                                new IdentifierNode("x"),
                                new IdentifierNode("y")
                        )
                )
        );
        Function f = new GeneratedFunction(body, new DefaultContext(), Arrays.asList("x", "y"));
        assertEquals(LangInt.of(12), f.apply(Arrays.asList(LangInt.of(6), LangInt.of(6))));
    }

    private static Function triple() {
        return new GeneratedFunction(TRIPLE_ADDITION, new DefaultContext(), Arrays.asList("x", "y", "z"));
    }

    @Test
    public void zeroArgumentsRequiresThreeMore() throws LangException {
        Function f = triple();
        Data d = f.apply(Collections.emptyList());
        assertEquals(RawFunction.getInstance(), d.getType());
        assertEquals(LangInt.of(6), ((Function) d).apply(Arrays.asList(LangInt.of(1), LangInt.of(2), LangInt.of(3))));
    }

    @Test
    public void oneArgumentRequiresTwoMore() throws LangException {
        Function f = triple();
        Data d = f.apply(Collections.singletonList(LangInt.of(7)));
        assertEquals(RawFunction.getInstance(), d.getType());
        assertEquals(LangInt.of(12), ((Function) d).apply(Arrays.asList(LangInt.of(2), LangInt.of(3))));
    }

    @Test
    public void twoArgumentsRequiresOneMore() throws LangException {
        Function f = triple();
        Data d = f.apply(Arrays.asList(LangInt.of(11), LangInt.of(-1)));
        assertEquals(RawFunction.getInstance(), d.getType());
        assertEquals(LangInt.of(14), ((Function) d).apply(Collections.singletonList(LangInt.of(4))));
    }

    @Test
    public void fullApplicationDoesNotReturnPartial() throws LangException {
        Function f = triple();
        Data d = f.apply(Arrays.asList(LangInt.of(11), LangInt.of(-1), LangInt.of(9)));
        assertNotEquals(RawFunction.getInstance(), d.getType());
        assertEquals(LangInt.of(19), d);
    }
}