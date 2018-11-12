package prj.clark.lang.impl.tree;

import org.junit.Test;
import prj.clark.lang.impl.env.*;
import prj.clark.lang.impl.err.LangException;
import prj.clark.lang.impl.err.TypeMismatchException;

import static org.junit.Assert.*;

public class LessThanEqualNodeTest {
    private Context ctx = new DummyContext();
    private static final Data TRUE = LangBool.of(true);
    private static final Data FALSE = LangBool.of(false);

    private static Node create(Data a, Data b) {
        return new LessThanEqualNode(new LiteralNode(a), new LiteralNode(b));
    }

    @Test
    public void intLTEFalse() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangInt.of(3);
        Node n = create(a, b);

        assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatLTEFalse() throws LangException {
        Data a = LangFloat.of(5);
        Data b = LangFloat.of(3);
        Node n = create(a, b);

        assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intFloatLTEFalse() throws LangException {
        Data a = LangInt.of(5);
        Data b = LangFloat.of(3);
        Data c = LangFloat.of(3);
        Node n = create(a, b);

        assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void floatIntLTEFalse() throws LangException {
        Data a = LangFloat.of(5);
        Data b = LangInt.of(3);
        Data c = LangInt.of(3);
        Node n = create(a, b);

        assertEquals(FALSE, n.evaluate(ctx));
    }

    @Test
    public void intLTETrue() throws LangException {
        Data a = LangInt.of(3);
        Data b = LangInt.of(5);
        Node n = create(a, b);

        assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatLTETrue() throws LangException {
        Data a = LangFloat.of(3);
        Data b = LangFloat.of(5);
        Node n = create(a, b);

        assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void intFloatLTETrue() throws LangException {
        Data a = LangInt.of(3);
        Data b = LangFloat.of(5);
        Node n = create(a, b);

        assertEquals(TRUE, n.evaluate(ctx));
    }

    @Test
    public void floatIntLTETrue() throws LangException {
        Data a = LangFloat.of(3);
        Data b = LangInt.of(5);
        Node n = create(a, b);

        assertEquals(TRUE, n.evaluate(ctx));
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
        Data a = LangString.of("hello");
        Data b = LangString.of("world");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolStringLTE() throws LangException {
        Data a = TRUE;
        Data b = LangString.of("asdf");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringBoolLTE() throws LangException {
        Data a = LangString.of("lkasdfhjk");
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intBoolLTE() throws LangException {
        Data a = LangInt.of(5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolIntLTE() throws LangException {
        Data a = TRUE;
        Data b = LangInt.of(3);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatBoolLTE() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = FALSE;
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void boolFloatLTE() throws LangException {
        Data a = FALSE;
        Data b = LangFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void intStringLTE() throws LangException {
        Data a = LangInt.of(2);
        Data b = LangString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringIntLTE() throws LangException {
        Data a = LangString.of("yo");
        Data b = LangInt.of(2);
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStringLTE() throws LangException {
        Data a = LangFloat.of(2.5);
        Data b = LangString.of("yo");
        Node n = create(a, b);

        n.evaluate(ctx);
    }

    @Test(expected = TypeMismatchException.class)
    public void stringFloatLTE() throws LangException {
        Data a = LangString.of("yo");
        Data b = LangFloat.of(2.5);
        Node n = create(a, b);

        n.evaluate(ctx);
    }
}