package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;
import prj.clark.lang.alchemy.env.*;

public class GreaterThanNodeTest {
    private Context ctx = new DummyContext();
    private static final Data TRUE = LangBool.of(true);
    private static final Data FALSE = LangBool.of(false);

    private static Node create(Data a, Data b) {
        return new GreaterThanNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intGTTrue() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangInt.of(3);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatGTTrue() throws LangException {
        Data a = LangFloat.of(5);
        Data b = LangFloat.of(3);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intFloatGTTrue() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangFloat.of(3);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatIntGTTrue() throws LangException {
        Data a = LangFloat.of(5);
        Data b = LangInt.of(3);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intGTFalse() throws LangException {
        Data a = LangInt.of(3);
        Data b = LangInt.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatGTFalse() throws LangException {
        Data a = LangFloat.of(3);
        Data b = LangFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intFloatGTFalse() throws LangException {
        Data a = LangInt.of(3);
        Data b = LangFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatIntGTFalse() throws LangException {
        Data a = LangFloat.of(3);
        Data b = LangInt.of(5);
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
        Data a = LangString.of("hello");
        Data b = LangString.of("world");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringGT() throws LangException {
        Data a = TRUE;
        Data b = LangString.of("asdf");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolGT() throws LangException {
        Data a = LangString.of("lkasdfhjk");
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolGT() throws LangException {
        Data a = LangInt.of(5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntGT() throws LangException {
        Data a = TRUE;
        Data b = LangInt.of(3);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolGT() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatGT() throws LangException {
        Data a = FALSE;
        Data b = LangFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringGT() throws LangException {
        Data a = LangInt.of(2);
        Data b = LangString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntGT() throws LangException {
        Data a = LangString.of("yo");
        Data b = LangInt.of(2);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringGT() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = LangString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatGT() throws LangException {
        Data a = LangString.of("yo");
        Data b = LangFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }
}