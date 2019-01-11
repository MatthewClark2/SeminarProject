package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;
import prj.clark.lang.alchemy.env.*;

public class GreaterThanEqualNodeTest {
    private Context ctx = new DummyContext();
    private static final Data TRUE = LangBool.of(true);
    private static final Data FALSE = LangBool.of(false);

    private static Node create(Data a, Data b) {
        return new GreaterThanEqualNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intGTETrue() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangInt.of(3);
        Data c = LangInt.of(3);
        Node n = create(a, b);
        Node o = create(b, c);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
        Assert.assertEquals(TRUE, o.evaluate(ctx));
    }

    @Test
    public void floatGTETrue() throws LangException {
        Data a = LangFloat.of(5);
        Data b = LangFloat.of(3);
        Data c = LangFloat.of(3);
        Node n = create(a, b);
        Node o = create(b, c);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
        Assert.assertEquals(TRUE, o.evaluate(ctx));
    }

    @Test
    public void intFloatGTETrue() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangFloat.of(3);
        Data c = LangFloat.of(3);
        Node n = create(a, b);
        Node o = create(b, c);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
        Assert.assertEquals(TRUE, o.evaluate(ctx));
    }

    @Test
    public void floatIntGTETrue() throws LangException {
        Data a = LangFloat.of(5);
        Data b = LangInt.of(3);
        Data c = LangInt.of(3);
        Node n = create(a, b);
        Node o = create(b, c);

        Assert.assertEquals(TRUE, n.evaluate(ctx));
        Assert.assertEquals(TRUE, o.evaluate(ctx));
    }

    @Test
    public void intGTEFalse() throws LangException {
        Data a = LangInt.of(3);
        Data b = LangInt.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatGTEFalse() throws LangException {
        Data a = LangFloat.of(3);
        Data b = LangFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intFloatGTEFalse() throws LangException {
        Data a = LangInt.of(3);
        Data b = LangFloat.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatIntGTEFalse() throws LangException {
        Data a = LangFloat.of(3);
        Data b = LangInt.of(5);
        Node n = create(a, b);

        Assert.assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test(expected = TypeMismatchException.class)
    public void boolGTE() throws LangException {
        Data a = TRUE;
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringGTE() throws LangException {
        Data a = LangString.of("hello");
        Data b = LangString.of("world");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringGTE() throws LangException {
        Data a = TRUE;
        Data b = LangString.of("asdf");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolGTE() throws LangException {
        Data a = LangString.of("lkasdfhjk");
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolGTE() throws LangException {
        Data a = LangInt.of(5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntGTE() throws LangException {
        Data a = TRUE;
        Data b = LangInt.of(3);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolGTE() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatGTE() throws LangException {
        Data a = FALSE;
        Data b = LangFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringGTE() throws LangException {
        Data a = LangInt.of(2);
        Data b = LangString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntGTE() throws LangException {
        Data a = LangString.of("yo");
        Data b = LangInt.of(2);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringGTE() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = LangString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatGTE() throws LangException {
        Data a = LangString.of("yo");
        Data b = LangFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }
}