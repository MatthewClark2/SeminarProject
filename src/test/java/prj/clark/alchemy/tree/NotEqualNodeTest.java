package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;

public class NotEqualNodeTest {
    private static Data TRUE = AlchemyBoolean.of(true);
    private static Data FALSE = AlchemyBoolean.of(false);
    private Context ctx = new DummyContext();

    private static Valued create(Data a, Data b) {
        return new NotEqualNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intEqual() throws LangException {
        Data a = AlchemyInt.of(12);
        Data b = AlchemyInt.of(12);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intNotEqual() throws LangException {
        Data a = AlchemyInt.of(12);
        Data b = AlchemyInt.of(119);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatEqual() throws LangException {
        Data a = AlchemyFloat.of(12.3);
        Data b = AlchemyFloat.of(12.3);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatNotEqual() throws LangException {
        Data a = AlchemyFloat.of(12.3);
        Data b = AlchemyFloat.of(-12.3);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void boolEqual() throws LangException {
        Valued n = create(TRUE, TRUE);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void boolNotEqual() throws LangException {
        Valued n = create(TRUE, FALSE);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void stringEqual() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("hello");
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void stringNotEqual() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("goodbye");
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intFloatNotEqual() throws LangException {
        Data a = AlchemyInt.of(12);
        Data b = AlchemyFloat.of(12);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intStringNotEqual() throws LangException {
        Data a = AlchemyInt.of(12);
        Data b = AlchemyString.of("12");
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intBoolNotEqual() throws LangException {
        Data a = AlchemyInt.of(17);
        Valued n = create(a, TRUE);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatIntNotEqual() throws LangException {
        Data a = AlchemyFloat.of(12);
        Data b = AlchemyInt.of(12);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatStringNotEqual() throws LangException {
        Data a = AlchemyFloat.of(21.2);
        Data b = AlchemyString.of("21.2");
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatBoolNotEqual() throws LangException {
        Data a = AlchemyFloat.of(0.5);
        Valued n = create(a, TRUE);
        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void boolIntNotEqual() throws LangException {
        Data a = AlchemyInt.of(17);
        Valued n = create(TRUE, a);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void boolFloatNotEqual() throws LangException {
        Data a = AlchemyFloat.of(0.5);
        Valued n = create(TRUE, a);
        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void boolStringNotEqual() throws LangException {
        Data a = AlchemyString.of("false");
        Valued n = create(TRUE, a);
        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void stringIntNotEqual() throws LangException {
        Data a = AlchemyString.of("12");
        Data b = AlchemyInt.of(12);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void stringFloatNotEqual() throws LangException {
        Data a = AlchemyString.of("21.2");
        Data b = AlchemyFloat.of(21.2);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void stringBoolNotEqual() throws LangException {
        Data a = AlchemyString.of("false");
        Valued n = create(a, TRUE);
        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }
}