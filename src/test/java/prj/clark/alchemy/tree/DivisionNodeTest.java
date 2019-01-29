package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

public class DivisionNodeTest {
    private Context ctx;

    private static Valued create(Data a, Data b) {
        return new DivisionNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Before
    public void setUp() {
        ctx = new DummyContext();
    }

    @Test
    public void intDivision() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyFloat.of(1);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatDivision() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = AlchemyFloat.of(-1.25);
        Data expected = AlchemyFloat.of(-2.0);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void intFloatDivision() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyFloat.of(12.5);
        Data expected = AlchemyFloat.of(0.4);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatIntDivision() throws LangException {
        Data a = AlchemyFloat.of(12.5);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyFloat.of(2.5);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringDivision() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("world");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolDivision() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyBoolean.of(false);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntDivision() throws LangException {
        Data a = AlchemyString.of("asdfasdf");
        Data b = AlchemyInt.of(-8);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringDivision() throws LangException {
        Data a = AlchemyInt.of(-8);
        Data b = AlchemyString.of("asdfasdf");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatDivision() throws LangException {
        Data a = AlchemyString.of("klajsdlksakjh");
        Data b = AlchemyFloat.of(3.75);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringDivision() throws LangException {
        Data a = AlchemyFloat.of(3.75);
        Data b = AlchemyString.of("klajsdlksakjh");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntDivision() throws LangException {
        Data a = AlchemyBoolean.of(false);
        Data b = AlchemyInt.of(777);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolDivision() throws LangException {
        Data a = AlchemyInt.of(777);
        Data b = AlchemyBoolean.of(false);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatDivision() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyFloat.of(123.456);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolDivision() throws LangException {
        Data a = AlchemyFloat.of(123.456);
        Data b = AlchemyBoolean.of(true);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringDivision() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyString.of("khats");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolDivision() throws LangException {
        Data a = AlchemyString.of("khats");
        Data b = AlchemyBoolean.of(true);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }
}