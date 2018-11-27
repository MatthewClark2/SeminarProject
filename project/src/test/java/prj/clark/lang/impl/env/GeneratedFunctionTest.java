package prj.clark.lang.impl.env;

import org.junit.Test;
import prj.clark.lang.impl.err.FunctionInvocationException;
import prj.clark.lang.impl.err.LangException;
import prj.clark.lang.impl.err.TypeMismatchException;
import prj.clark.lang.impl.tree.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Note that this class tests with the assumption that all functions are raw.
 */
public class GeneratedFunctionTest {
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
}