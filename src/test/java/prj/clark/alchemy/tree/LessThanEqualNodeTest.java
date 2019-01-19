package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

public class LessThanEqualNodeTest {
    private Context ctx = new DummyContext();
    private static final Data TRUE = AlchemyBoolean.of(true);
    private static final Data FALSE = AlchemyBoolean.of(false);

    private static Node create(Data a, Data b) {
        return new LessThanEqualNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intLTEFalse() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyInt.of(3);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatLTEFalse() throws LangException {
        Data a = AlchemyFloat.of(5);
        Data b = AlchemyFloat.of(3);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intFloatLTEFalse() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = AlchemyFloat.of(3);
        Data c = AlchemyFloat.of(3);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatIntLTEFalse() throws LangException {
        Data a = AlchemyFloat.of(5);
        Data b = AlchemyInt.of(3);
        Data c = AlchemyInt.of(3);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intLTETrue() throws LangException {
        Data a = AlchemyInt.of(3);
        Data b = AlchemyInt.of(5);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatLTETrue() throws LangException {
        Data a = AlchemyFloat.of(3);
        Data b = AlchemyFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intFloatLTETrue() throws LangException {
        Data a = AlchemyInt.of(3);
        Data b = AlchemyFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatIntLTETrue() throws LangException {
        Data a = AlchemyFloat.of(3);
        Data b = AlchemyInt.of(5);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void boolLTE() throws LangException {
        Data a = TRUE;
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringLTE() throws LangException {
        Data a = AlchemyString.of("hello");
        Data b = AlchemyString.of("world");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringLTE() throws LangException {
        Data a = TRUE;
        Data b = AlchemyString.of("asdf");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolLTE() throws LangException {
        Data a = AlchemyString.of("lkasdfhjk");
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolLTE() throws LangException {
        Data a = AlchemyInt.of(5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntLTE() throws LangException {
        Data a = TRUE;
        Data b = AlchemyInt.of(3);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolLTE() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatLTE() throws LangException {
        Data a = FALSE;
        Data b = AlchemyFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringLTE() throws LangException {
        Data a = AlchemyInt.of(2);
        Data b = AlchemyString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntLTE() throws LangException {
        Data a = AlchemyString.of("yo");
        Data b = AlchemyInt.of(2);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringLTE() throws LangException {
        Data a = AlchemyFloat.of(2.5);
        Data b = AlchemyString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatLTE() throws LangException {
        Data a = AlchemyString.of("yo");
        Data b = AlchemyFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }
}