package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

public class GreaterThanNodeTest {
    private Context ctx = new DummyContext();
    private static final Data TRUE = AlchemyBoolean.of(true);
    private static final Data FALSE = AlchemyBoolean.of(false);

    private static Node create(Data a, Data b) {
        return new GreaterThanNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intGTTrue() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyInt.of(3);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatGTTrue() throws LangException {
        Data a = AlchemyFloat.of(5);
        Data b = AlchemyFloat.of(3);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intFloatGTTrue() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyFloat.of(3);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatIntGTTrue() throws LangException {
        Data a = AlchemyFloat.of(5);
        Data b = AlchemyInt.of(3);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intGTFalse() throws LangException {
        Data a = AlchemyInt.of(3);
        Data b = AlchemyInt.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatGTFalse() throws LangException {
        Data a = AlchemyFloat.of(3);
        Data b = AlchemyFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intFloatGTFalse() throws LangException {
        Data a = AlchemyInt.of(3);
        Data b = AlchemyFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatIntGTFalse() throws LangException {
        Data a = AlchemyFloat.of(3);
        Data b = AlchemyInt.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void boolGT() throws LangException {
        Data a = TRUE;
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringGT() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("world");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringGT() throws LangException {
        Data a = TRUE;
        Data b = AlchemyString.of("asdf");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolGT() throws LangException {
        Data a = AlchemyString.of("lkasdfhjk");
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolGT() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntGT() throws LangException {
        Data a = TRUE;
        Data b = AlchemyInt.of(3);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolGT() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatGT() throws LangException {
        Data a = FALSE;
        Data b = AlchemyFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringGT() throws LangException {
        Data a = AlchemyInt.of(2);
        Data b = AlchemyString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntGT() throws LangException {
        Data a = AlchemyString.of("yo");
        Data b = AlchemyInt.of(2);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringGT() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = AlchemyString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatGT() throws LangException {
        Data a = AlchemyString.of("yo");
        Data b = AlchemyFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }
}