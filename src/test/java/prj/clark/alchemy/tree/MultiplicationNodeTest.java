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

    private static Valued create(Data a, Data b) {
        return new MultiplicationNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Before
    public void setUp() {
        ctx = new DummyContext();
    }

    @Test
    public void intMultiplication() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyInt.of(25);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatMultiplication() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = AlchemyFloat.of(-1.5);
        Data expected = AlchemyFloat.of(-3.75);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void intFloatMultiplication() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyFloat.of(1.5);
        Data expected = AlchemyFloat.of(7.5);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatIntMultiplication() throws LangException {
        Data a = AlchemyFloat.of(1.5);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyFloat.of(7.5);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringMultiplication() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("world");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolMultiplication() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyBoolean.of(false);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntMultiplication() throws LangException {
        Data a = AlchemyString.of("asdfasdf");
        Data b = AlchemyInt.of(-8);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringMultiplication() throws LangException {
        Data a = AlchemyInt.of(-8);
        Data b = AlchemyString.of("asdfasdf");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatMultiplication() throws LangException {
        Data a = AlchemyString.of("klajsdlksakjh");
        Data b = AlchemyFloat.of(3.75);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringMultiplication() throws LangException {
        Data a = AlchemyFloat.of(3.75);
        Data b = AlchemyString.of("klajsdlksakjh");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntMultiplication() throws LangException {
        Data a = AlchemyBoolean.of(false);
        Data b = AlchemyInt.of(777);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolMultiplication() throws LangException {
        Data a = AlchemyInt.of(777);
        Data b = AlchemyBoolean.of(false);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatMultiplication() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyFloat.of(123.456);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolMultiplication() throws LangException {
        Data a = AlchemyFloat.of(123.456);
        Data b = AlchemyBoolean.of(true);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringMultiplication() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyString.of("khats");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolMultiplication() throws LangException {
        Data a = AlchemyString.of("khats");
        Data b = AlchemyBoolean.of(true);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }
}