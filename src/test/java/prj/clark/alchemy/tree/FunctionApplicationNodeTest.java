package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.FunctionInvocationException;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class FunctionApplicationNodeTest {
    private static Function F;
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

    @Before
    public void setUp() {
        F = new GeneratedFunction(TRIPLE_ADDITION, new DefaultContext(), Arrays.asList("x", "y", "z"));
    }

    private static Node init(Node... args) {
        return new FunctionApplicationNode(new LiteralNode(F), Arrays.asList(args));
    }

    @Test
    public void fullApplicationReturnsCorrectValue() throws LangException {
        Node n = init(
                new LiteralNode(LangInt.of(1)),
                new LiteralNode(LangInt.of(3)),
                new LiteralNode(LangInt.of(5))
        );

        Assert.assertEquals(LangInt.of(9), n.evaluate(new DummyContext()));
    }

    @Test
    public void emptyApplicationReturnsPartialFunction() throws LangException {
        Node n = init();
        Data d = n.evaluate(new DummyContext());
        assertEquals(RawFunction.getInstance(), d.getType());
    }

    @Test(expected = FunctionInvocationException.class)
    public void excessApplicationFails() throws LangException {
        Node n = init(
                new LiteralNode(LangInt.of(1)),
                new LiteralNode(LangInt.of(3)),
                new LiteralNode(LangInt.of(0)),
                new LiteralNode(LangInt.of(5))
        );

        n.evaluate(new DummyContext());
    }

    @Test(expected = TypeMismatchException.class)
    public void nonFunctionThrowsException() throws LangException {
        Node n = new FunctionApplicationNode(new LiteralNode(LangString.of("")), Collections.emptyList());
        n.evaluate(new DummyContext());
    }
}