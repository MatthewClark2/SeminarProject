package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;
import prj.clark.lang.alchemy.env.*;

public class LessThanNodeTest {
    private Context ctx = new DummyContext();
    private static final Data TRUE = LangBool.of(true);
    private static final Data FALSE = LangBool.of(false);

    private static Node create(Data a, Data b) {
        return new LessThanNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intLTFalse() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangInt.of(3);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatLTFalse() throws LangException {
        Data a = LangFloat.of(5);
        Data b = LangFloat.of(3);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intFloatLTFalse() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangFloat.of(3);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatIntLTFalse() throws LangException {
        Data a = LangFloat.of(5);
        Data b = LangInt.of(3);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intLTTrue() throws LangException {
        Data a = LangInt.of(3);
        Data b = LangInt.of(5);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatLTTrue() throws LangException {
        Data a = LangFloat.of(3);
        Data b = LangFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intFloatLTTrue() throws LangException {
        Data a = LangInt.of(3);
        Data b = LangFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatIntLTTrue() throws LangException {
        Data a = LangFloat.of(3);
        Data b = LangInt.of(5);
        Node n = create(a, b);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void boolLT() throws LangException {
        Data a = TRUE;
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringLT() throws LangException {
        Data a = LangString.of("hello");
        Data b = LangString.of("world");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringLT() throws LangException {
        Data a = TRUE;
        Data b = LangString.of("asdf");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolLT() throws LangException {
        Data a = LangString.of("lkasdfhjk");
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolLT() throws LangException {
        Data a = LangInt.of(5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntLT() throws LangException {
        Data a = TRUE;
        Data b = LangInt.of(3);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolLT() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatLT() throws LangException {
        Data a = FALSE;
        Data b = LangFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringLT() throws LangException {
        Data a = LangInt.of(2);
        Data b = LangString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntLT() throws LangException {
        Data a = LangString.of("yo");
        Data b = LangInt.of(2);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringLT() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = LangString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatLT() throws LangException {
        Data a = LangString.of("yo");
        Data b = LangFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }
}