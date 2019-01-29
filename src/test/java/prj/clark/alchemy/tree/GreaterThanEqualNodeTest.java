package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

public class GreaterThanEqualNodeTest {
    private Context ctx = new DummyContext();
    private static final Data TRUE = AlchemyBoolean.of(true);
    private static final Data FALSE = AlchemyBoolean.of(false);

    private static Valued create(Data a, Data b) {
        return new GreaterThanEqualNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intGTETrue() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyInt.of(3);
        Data c = AlchemyInt.of(3);
        Valued n = create(a, b);
        Valued o = create(b, c);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
        Assert.assertEquals(TRUE, o.evaluate(ctx));
    }

    @Test
    public void floatGTETrue() throws LangException {
        Data a = AlchemyFloat.of(5);
        Data b = AlchemyFloat.of(3);
        Data c = AlchemyFloat.of(3);
        Valued n = create(a, b);
        Valued o = create(b, c);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
        Assert.assertEquals(TRUE, o.evaluate(ctx));
    }

    @Test
    public void intFloatGTETrue() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyFloat.of(3);
        Data c = AlchemyFloat.of(3);
        Valued n = create(a, b);
        Valued o = create(b, c);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
        Assert.assertEquals(TRUE, o.evaluate(ctx));
    }

    @Test
    public void floatIntGTETrue() throws LangException {
        Data a = AlchemyFloat.of(5);
        Data b = AlchemyInt.of(3);
        Data c = AlchemyInt.of(3);
        Valued n = create(a, b);
        Valued o = create(b, c);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
        Assert.assertEquals(TRUE, o.evaluate(ctx));
    }

    @Test
    public void intGTEFalse() throws LangException {
        Data a = AlchemyInt.of(3);
        Data b = AlchemyInt.of(5);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatGTEFalse() throws LangException {
        Data a = AlchemyFloat.of(3);
        Data b = AlchemyFloat.of(5);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intFloatGTEFalse() throws LangException {
        Data a = AlchemyInt.of(3);
        Data b = AlchemyFloat.of(5);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatIntGTEFalse() throws LangException {
        Data a = AlchemyFloat.of(3);
        Data b = AlchemyInt.of(5);
        Valued n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void boolGTE() throws LangException {
        Data a = TRUE;
        Data b = FALSE;
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringGTE() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("world");
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringGTE() throws LangException {
        Data a = TRUE;
        Data b = AlchemyString.of("asdf");
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolGTE() throws LangException {
        Data a = AlchemyString.of("lkasdfhjk");
        Data b = FALSE;
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolGTE() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = FALSE;
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntGTE() throws LangException {
        Data a = TRUE;
        Data b = AlchemyInt.of(3);
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolGTE() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = FALSE;
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatGTE() throws LangException {
        Data a = FALSE;
        Data b = AlchemyFloat.of(2.5);
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringGTE() throws LangException {
        Data a = AlchemyInt.of(2);
        Data b = AlchemyString.of("yo");
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntGTE() throws LangException {
        Data a = AlchemyString.of("yo");
        Data b = AlchemyInt.of(2);
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringGTE() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = AlchemyString.of("yo");
        Valued n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatGTE() throws LangException {
        Data a = AlchemyString.of("yo");
        Data b = AlchemyFloat.of(2.5);
        Valued n = create(a, b);

        n.evaluate(ctx);
    }
}