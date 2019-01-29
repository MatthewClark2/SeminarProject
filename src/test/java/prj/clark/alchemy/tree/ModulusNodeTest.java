package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

// Note that resulting values were taken from a Python REPL. If any test inputs are incorrect, they need to be modified.
public class ModulusNodeTest {
    private Context ctx;

    private static Valued create(Data a, Data b) {
        return new ModulusNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Before
    public void setUp() {
        ctx = new DummyContext();
    }

    @Test
    public void intModulus() throws LangException {
        Data a = AlchemyInt.of(7);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyInt.of(2);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatModulus() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = AlchemyFloat.of(1.75);
        Data expected = AlchemyFloat.of(0.75);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void intFloatModulus() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyFloat.of(12.5);
        Data expected = AlchemyFloat.of(5);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatIntModulus() throws LangException {
        Data a = AlchemyFloat.of(10.25);
        Data b = AlchemyInt.of(5);
        Data expected = AlchemyFloat.of(0.25);

        Valued n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringModulus() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("world");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolModulus() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyBoolean.of(false);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntModulus() throws LangException {
        Data a = AlchemyString.of("asdfasdf");
        Data b = AlchemyInt.of(-8);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringModulus() throws LangException {
        Data a = AlchemyInt.of(-8);
        Data b = AlchemyString.of("asdfasdf");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatModulus() throws LangException {
        Data a = AlchemyString.of("klajsdlksakjh");
        Data b = AlchemyFloat.of(3.75);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringModulus() throws LangException {
        Data a = AlchemyFloat.of(3.75);
        Data b = AlchemyString.of("klajsdlksakjh");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntModulus() throws LangException {
        Data a = AlchemyBoolean.of(false);
        Data b = AlchemyInt.of(777);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolModulus() throws LangException {
        Data a = AlchemyInt.of(777);
        Data b = AlchemyBoolean.of(false);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatModulus() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyFloat.of(123.456);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolModulus() throws LangException {
        Data a = AlchemyFloat.of(123.456);
        Data b = AlchemyBoolean.of(true);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringModulus() throws LangException {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyString.of("khats");

        Valued n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolModulus() throws LangException {
        Data a = AlchemyString.of("khats");
        Data b = AlchemyBoolean.of(true);

        Valued n = create(a, b);
        n.evaluate(ctx);
    }
}