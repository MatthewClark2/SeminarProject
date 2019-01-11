package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.LangException;

public class EqualNodeTest {
    private static Data TRUE = LangBool.of(true);
    private static Data FALSE = LangBool.of(false);
    private Context ctx = new DummyContext();

    private static Node create(Data a, Data b) {
        return new EqualNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intEqual() throws LangException {
        Data a = LangInt.of(12);
        Data b = LangInt.of(12);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intNotEqual() throws LangException {
        Data a = LangInt.of(12);
        Data b = LangInt.of(119);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatEqual() throws LangException {
        Data a = LangFloat.of(12.3);
        Data b = LangFloat.of(12.3);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatNotEqual() throws LangException {
        Data a = LangFloat.of(12.3);
        Data b = LangFloat.of(-12.3);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void boolEqual() throws LangException {
        Node n = create(TRUE, TRUE);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void boolNotEqual() throws LangException {
        Node n = create(TRUE, FALSE);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void stringEqual() throws LangException {
        Data a = LangString.of("hello");
        Data b = LangString.of("hello");
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void stringNotEqual() throws LangException {
        Data a = LangString.of("hello");
        Data b = LangString.of("goodbye");
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intFloatNotEqual() throws LangException {
        Data a = LangInt.of(12);
        Data b = LangFloat.of(12);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intStringNotEqual() throws LangException {
        Data a = LangInt.of(12);
        Data b = LangString.of("12");
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intBoolNotEqual() throws LangException {
        Data a = LangInt.of(17);
        Node n = create(a, FALSE);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatIntNotEqual() throws LangException {
        Data a = LangFloat.of(12);
        Data b = LangInt.of(12);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatStringNotEqual() throws LangException {
        Data a = LangFloat.of(21.2);
        Data b = LangString.of("21.2");
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatBoolNotEqual() throws LangException {
        Data a = LangFloat.of(0.5);
        Node n = create(a, TRUE);
        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void boolIntNotEqual() throws LangException {
        Data a = LangInt.of(17);
        Node n = create(FALSE, a);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void boolFloatNotEqual() throws LangException {
        Data a = LangFloat.of(0.5);
        Node n = create(TRUE, a);
        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void boolStringNotEqual() throws LangException {
        Data a = LangString.of("false");
        Node n = create(FALSE, a);
        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void stringIntNotEqual() throws LangException {
        Data a = LangString.of("12");
        Data b = LangInt.of(12);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void stringFloatNotEqual() throws LangException {
        Data a = LangString.of("21.2");
        Data b = LangFloat.of(21.2);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void stringBoolNotEqual() throws LangException {
        Data a = LangString.of("false");
        Node n = create(a, FALSE);
        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }
}