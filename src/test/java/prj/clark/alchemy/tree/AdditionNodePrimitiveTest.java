package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

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
        Data a = AlchemyInt.of(5);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyInt.of(10);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatAddition() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = AlchemyFloat.of(-1.25);
        Data expected = AlchemyFloat.of(1.25);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void intFloatAddition() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyFloat.of(10.25);
        Data expected = AlchemyFloat.of(15.25);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatIntAddition() throws LangException {
        Data a = AlchemyFloat.of(10.25);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyFloat.of(15.25);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringAddition() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("world");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolAddition() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyBoolean.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntAddition() throws LangException {
        Data a = AlchemyString.of("asdfasdf");
        Data b = AlchemyInt.of(-8);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringAddition() throws LangException {
        Data a = AlchemyInt.of(-8);
        Data b = AlchemyString.of("asdfasdf");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatAddition() throws LangException {
        Data a = AlchemyString.of("klajsdlksakjh");
        Data b = AlchemyFloat.of(3.75);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringAddition() throws LangException {
        Data a = AlchemyFloat.of(3.75);
        Data b = AlchemyString.of("klajsdlksakjh");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntAddition() throws LangException {
        Data a = AlchemyBoolean.of(false);
        Data b = AlchemyInt.of(777);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolAddition() throws LangException {
        Data a = AlchemyInt.of(777);
        Data b = AlchemyBoolean.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatAddition() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyFloat.of(123.456);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolAddition() throws LangException {
        Data a = AlchemyFloat.of(123.456);
        Data b = AlchemyBoolean.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringAddition() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyString.of("khats");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolAddition() throws LangException {
        Data a = AlchemyString.of("khats");
        Data b = AlchemyBoolean.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }
}