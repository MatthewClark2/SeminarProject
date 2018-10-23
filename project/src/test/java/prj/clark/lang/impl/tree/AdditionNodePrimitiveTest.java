package prj.clark.lang.impl.tree;

import org.junit.Before;
import org.junit.Test;
import prj.clark.lang.impl.env.*;
import prj.clark.lang.impl.err.LangException;
import prj.clark.lang.impl.err.TypeMismatchException;

import static org.junit.Assert.*;

/**
 * Tests involving complex data types, such as functions or collections are not handled by this test suite.
 * All tests written in this class work under the assumption that the Lang<i>Type</i>, and terminal {@link Node} classes
 * work correctly.
 * {@link LiteralNode}s are used for their simplicity.
 */
public class AdditionNodePrimitiveTest {
    private Context ctx;

    private static Node create(Data a, Data b) {
        return new AdditionNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Before
    public void setUp() {
        ctx = new DummyContext();
    }

    @Test
    public void intAddition() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangInt.of(5);
        Data expected = LangInt.of(10);

        Node n = create(a, b);
        assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatAddition() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = LangFloat.of(-1.25);
        Data expected = LangFloat.of(1.25);

        Node n = create(a, b);
        assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringAddition() throws LangException {
        Data a = LangString.of("hello");
        Data b = LangString.of("world");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolAddition() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangBool.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntAddition() throws LangException {
        Data a = LangString.of("asdfasdf");
        Data b = LangInt.of(-8);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatAddition() throws LangException {
        Data a = LangString.of("klajsdlksakjh");
        Data b = LangFloat.of(3.75);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntAddition() throws LangException {
        Data a = LangBool.of(false);
        Data b = LangInt.of(777);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatAddition() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangFloat.of(123.456);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringAddition() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangString.of("khats");

        Node n = create(a, b);
        n.evaluate(ctx);
    }
}