package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

public class LessThanNodeTest {
    private Context ctx = new DummyContext();
    private static final Data TRUE = AlchemyBoolean.of(true);
    private static final Data FALSE = AlchemyBoolean.of(false);

    private static Valued create(Data a, Data b) {
        return new LessThanNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intLTFalse() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyInt.of(3);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatLTFalse() throws LangException {
        Data a = AlchemyFloat.of(5);
        Data b = AlchemyFloat.of(3);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intFloatLTFalse() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyFloat.of(3);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatIntLTFalse() throws LangException {
        Data a = AlchemyFloat.of(5);
        Data b = AlchemyInt.of(3);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intLTTrue() throws LangException {
        Data a = AlchemyInt.of(3);
        Data b = AlchemyInt.of(5);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatLTTrue() throws LangException {
        Data a = AlchemyFloat.of(3);
        Data b = AlchemyFloat.of(5);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intFloatLTTrue() throws LangException {
        Data a = AlchemyInt.of(3);
        Data b = AlchemyFloat.of(5);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatIntLTTrue() throws LangException {
        Data a = AlchemyFloat.of(3);
        Data b = AlchemyInt.of(5);
        Valued n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void boolLT() throws LangException {
        Data a = TRUE;
        Data b = FALSE;
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringLT() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("world");
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringLT() throws LangException {
        Data a = TRUE;
        Data b = AlchemyString.of("asdf");
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolLT() throws LangException {
        Data a = AlchemyString.of("lkasdfhjk");
        Data b = FALSE;
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolLT() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = FALSE;
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntLT() throws LangException {
        Data a = TRUE;
        Data b = AlchemyInt.of(3);
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolLT() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = FALSE;
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatLT() throws LangException {
        Data a = FALSE;
        Data b = AlchemyFloat.of(2.5);
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringLT() throws LangException {
        Data a = AlchemyInt.of(2);
        Data b = AlchemyString.of("yo");
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntLT() throws LangException {
        Data a = AlchemyString.of("yo");
        Data b = AlchemyInt.of(2);
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringLT() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = AlchemyString.of("yo");
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatLT() throws LangException {
        Data a = AlchemyString.of("yo");
        Data b = AlchemyFloat.of(2.5);
        Valued n = create(a, b);

        n.evaluate(ctx);
    }
}