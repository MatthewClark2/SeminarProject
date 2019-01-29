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

    private static Valued create(Data a, Data b) {
        return new SubtractionNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Before
    public void setUp() {
        ctx = new DummyContext();
    }

    @Test
    public void intSubtraction() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyInt.of(0);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatSubtraction() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = AlchemyFloat.of(-1.25);
        Data expected = AlchemyFloat.of(3.75);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void intFloatSubtraction() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyFloat.of(10.25);
        Data expected = AlchemyFloat.of(-5.25);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatIntSubtraction() throws LangException {
        Data a = AlchemyFloat.of(10.25);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyFloat.of(5.25);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringSubtraction() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("world");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolSubtraction() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyBoolean.of(false);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntSubtraction() throws LangException {
        Data a = AlchemyString.of("asdfasdf");
        Data b = AlchemyInt.of(-8);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringSubtraction() throws LangException {
        Data a = AlchemyInt.of(-8);
        Data b = AlchemyString.of("asdfasdf");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatSubtraction() throws LangException {
        Data a = AlchemyString.of("klajsdlksakjh");
        Data b = AlchemyFloat.of(3.75);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringSubtraction() throws LangException {
        Data a = AlchemyFloat.of(3.75);
        Data b = AlchemyString.of("klajsdlksakjh");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntSubtraction() throws LangException {
        Data a = AlchemyBoolean.of(false);
        Data b = AlchemyInt.of(777);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolSubtraction() throws LangException {
        Data a = AlchemyInt.of(777);
        Data b = AlchemyBoolean.of(false);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatSubtraction() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyFloat.of(123.456);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolSubtraction() throws LangException {
        Data a = AlchemyFloat.of(123.456);
        Data b = AlchemyBoolean.of(true);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringSubtraction() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyString.of("khats");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolSubtraction() throws LangException {
        Data a = AlchemyString.of("khats");
        Data b = AlchemyBoolean.of(true);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }
}