package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

public class SubtractionNodeTest {
    private Context ctx;

    private static Node create(Data a, Data b) {
        return new SubtractionNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Before
    public void setUp() {
        ctx = new DummyContext();
    }

    @Test
    public void intSubtraction() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangInt.of(5);
        Data expected = LangInt.of(0);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatSubtraction() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = LangFloat.of(-1.25);
        Data expected = LangFloat.of(3.75);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void intFloatSubtraction() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangFloat.of(10.25);
        Data expected = LangFloat.of(-5.25);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatIntSubtraction() throws LangException {
        Data a = LangFloat.of(10.25);
        Data b = LangInt.of(5);
        Data expected = LangFloat.of(5.25);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringSubtraction() throws LangException {
        Data a = LangString.of("hello");
        Data b = LangString.of("world");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolSubtraction() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangBool.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntSubtraction() throws LangException {
        Data a = LangString.of("asdfasdf");
        Data b = LangInt.of(-8);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringSubtraction() throws LangException {
        Data a = LangInt.of(-8);
        Data b = LangString.of("asdfasdf");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatSubtraction() throws LangException {
        Data a = LangString.of("klajsdlksakjh");
        Data b = LangFloat.of(3.75);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringSubtraction() throws LangException {
        Data a = LangFloat.of(3.75);
        Data b = LangString.of("klajsdlksakjh");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntSubtraction() throws LangException {
        Data a = LangBool.of(false);
        Data b = LangInt.of(777);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolSubtraction() throws LangException {
        Data a = LangInt.of(777);
        Data b = LangBool.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatSubtraction() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangFloat.of(123.456);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolSubtraction() throws LangException {
        Data a = LangFloat.of(123.456);
        Data b = LangBool.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringSubtraction() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangString.of("khats");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolSubtraction() throws LangException {
        Data a = LangString.of("khats");
        Data b = LangBool.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }
}