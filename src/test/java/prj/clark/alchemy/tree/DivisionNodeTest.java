package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;
import prj.clark.lang.alchemy.env.*;

public class DivisionNodeTest {
    private Context ctx;

    private static Node create(Data a, Data b) {
        return new DivisionNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Before
    public void setUp() {
        ctx = new DummyContext();
    }

    @Test
    public void intDivision() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangInt.of(5);
        Data expected = LangFloat.of(1);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatDivision() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = LangFloat.of(-1.25);
        Data expected = LangFloat.of(-2.0);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void intFloatDivision() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangFloat.of(12.5);
        Data expected = LangFloat.of(0.4);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test
    public void floatIntDivision() throws LangException {
        Data a = LangFloat.of(12.5);
        Data b = LangInt.of(5);
        Data expected = LangFloat.of(2.5);

        Node n = create(a, b);
        Assert.assertEquals(expected, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void stringDivision() throws LangException {
        Data a = LangString.of("hello");
        Data b = LangString.of("world");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolDivision() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangBool.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntDivision() throws LangException {
        Data a = LangString.of("asdfasdf");
        Data b = LangInt.of(-8);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringDivision() throws LangException {
        Data a = LangInt.of(-8);
        Data b = LangString.of("asdfasdf");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatDivision() throws LangException {
        Data a = LangString.of("klajsdlksakjh");
        Data b = LangFloat.of(3.75);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringDivision() throws LangException {
        Data a = LangFloat.of(3.75);
        Data b = LangString.of("klajsdlksakjh");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntDivision() throws LangException {
        Data a = LangBool.of(false);
        Data b = LangInt.of(777);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolDivision() throws LangException {
        Data a = LangInt.of(777);
        Data b = LangBool.of(false);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatDivision() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangFloat.of(123.456);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolDivision() throws LangException {
        Data a = LangFloat.of(123.456);
        Data b = LangBool.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringDivision() throws LangException {
        Data a = LangBool.of(true);
        Data b = LangString.of("khats");

        Node n = create(a, b);
        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolDivision() throws LangException {
        Data a = LangString.of("khats");
        Data b = LangBool.of(true);

        Node n = create(a, b);
        n.evaluate(ctx);
    }
}