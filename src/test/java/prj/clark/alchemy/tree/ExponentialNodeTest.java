package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;
import prj.clark.lang.alchemy.env.*;

public class ExponentialNodeTest {
    private Context ctx;

    private static Node create(Data a, Data b) {
        return new ExponentialNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Before
    public void setUp() {
        ctx = new DummyContext();
    }

    @Test
    public void intExponentiation() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangInt.of(3);
        Data expected = LangFloat.of(125);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatExponentiation() throws LangException {
        Data a = LangFloat.of(1.21);
        Data b = LangFloat.of(0.5);
        Data expected = LangFloat.of(1.1);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void intFloatExponentiation() throws LangException {
        Data a = LangInt.of(25);
        Data b = LangFloat.of(0.5);
        Data expected = LangFloat.of(5);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatIntExponentiation() throws LangException {
        Data a = LangFloat.of(1.5);
        Data b = LangInt.of(2);
        Data expected = LangFloat.of(2.25);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringExponentiation() throws LangException {
        Data a = LangString.of("hello");
        Data b = LangString.of("world");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolExponentiation() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangBool.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntExponentiation() throws LangException {
        Data a = LangString.of("asdfasdf");
        Data b = LangInt.of(-8);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringExponentiation() throws LangException {
        Data a = LangInt.of(-8);
        Data b = LangString.of("asdfasdf");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatExponentiation() throws LangException {
        Data a = LangString.of("klajsdlksakjh");
        Data b = LangFloat.of(3.75);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringExponentiation() throws LangException {
        Data a = LangFloat.of(3.75);
        Data b = LangString.of("klajsdlksakjh");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntExponentiation() throws LangException {
        Data a = LangBool.of(false);
        Data b = LangInt.of(777);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolExponentiation() throws LangException {
        Data a = LangInt.of(777);
        Data b = LangBool.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatExponentiation() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangFloat.of(123.456);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolExponentiation() throws LangException {
        Data a = LangFloat.of(123.456);
        Data b = LangBool.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringExponentiation() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangString.of("khats");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolExponentiation() throws LangException {
        Data a = LangString.of("khats");
        Data b = LangBool.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }
}