package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

public class MultiplicationNodeTest {
    private Context ctx;

    private static Node create(Data a, Data b) {
        return new MultiplicationNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Before
    public void setUp() {
        ctx = new DummyContext();
    }

    @Test
    public void intMultiplication() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangInt.of(5);
        Data expected = LangInt.of(25);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatMultiplication() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = LangFloat.of(-1.5);
        Data expected = LangFloat.of(-3.75);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void intFloatMultiplication() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangFloat.of(1.5);
        Data expected = LangFloat.of(7.5);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatIntMultiplication() throws LangException {
        Data a = LangFloat.of(1.5);
        Data b = LangInt.of(5);
        Data expected = LangFloat.of(7.5);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringMultiplication() throws LangException {
        Data a = LangString.of("hello");
        Data b = LangString.of("world");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolMultiplication() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangBool.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntMultiplication() throws LangException {
        Data a = LangString.of("asdfasdf");
        Data b = LangInt.of(-8);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringMultiplication() throws LangException {
        Data a = LangInt.of(-8);
        Data b = LangString.of("asdfasdf");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatMultiplication() throws LangException {
        Data a = LangString.of("klajsdlksakjh");
        Data b = LangFloat.of(3.75);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringMultiplication() throws LangException {
        Data a = LangFloat.of(3.75);
        Data b = LangString.of("klajsdlksakjh");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntMultiplication() throws LangException {
        Data a = LangBool.of(false);
        Data b = LangInt.of(777);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolMultiplication() throws LangException {
        Data a = LangInt.of(777);
        Data b = LangBool.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatMultiplication() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangFloat.of(123.456);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolMultiplication() throws LangException {
        Data a = LangFloat.of(123.456);
        Data b = LangBool.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringMultiplication() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangString.of("khats");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolMultiplication() throws LangException {
        Data a = LangString.of("khats");
        Data b = LangBool.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }
}