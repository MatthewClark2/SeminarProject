package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.DefaultContext;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.i64;
import static prj.clark.alchemy.data.AlchemyBoolean.FALSE;
import static prj.clark.alchemy.data.AlchemyBoolean.TRUE;

public class EqualNodeTest {
    private static boolean eq(Valued l, Valued r) {
        Data d = new EqualNode(l, r).evaluate(new DefaultContext());
        assertTrue(d instanceof AlchemyBoolean);
        return d.toBoolean();
    }

    private static boolean eq(Data l, Data r) {
        return eq(new LiteralNode(l), new LiteralNode(r));
    }

    @Test
    public void mixedUnequalTypes() {
        Data s = AlchemyString.of("hello");
        Data b = AlchemyBoolean.of(false);
        Data i = AlchemyInt.of(1);
        Data f = AlchemyFloat.of(-1.0);
        Data c = AlchemyCharacter.of("\n");
        Data l = new EagerAlchemyList(Arrays.asList(AlchemyCharacter.of('h'), AlchemyCharacter.of('i')));
        Data t = new AlchemyTuple(Arrays.asList(AlchemyString.of("a"), AlchemyInt.of(2)));
        Data fn = new GeneratedFunction(i64(5), new DefaultContext(), Collections.emptyList());

        // Thank Guido for Python.
        assertFalse(eq(s, b));
        assertFalse(eq(s, i));
        assertFalse(eq(s, f));
        assertFalse(eq(s, c));
        assertFalse(eq(s, l));
        assertFalse(eq(s, t));
        assertFalse(eq(s, fn));
        assertFalse(eq(b, s));
        assertFalse(eq(b, i));
        assertFalse(eq(b, f));
        assertFalse(eq(b, c));
        assertFalse(eq(b, l));
        assertFalse(eq(b, t));
        assertFalse(eq(b, fn));
        assertFalse(eq(i, s));
        assertFalse(eq(i, b));
        assertFalse(eq(i, f));
        assertFalse(eq(i, c));
        assertFalse(eq(i, l));
        assertFalse(eq(i, t));
        assertFalse(eq(i, fn));
        assertFalse(eq(f, s));
        assertFalse(eq(f, b));
        assertFalse(eq(f, i));
        assertFalse(eq(f, c));
        assertFalse(eq(f, l));
        assertFalse(eq(f, t));
        assertFalse(eq(f, fn));
        assertFalse(eq(c, s));
        assertFalse(eq(c, b));
        assertFalse(eq(c, i));
        assertFalse(eq(c, f));
        assertFalse(eq(c, l));
        assertFalse(eq(c, t));
        assertFalse(eq(c, fn));
        assertFalse(eq(l, s));
        assertFalse(eq(l, b));
        assertFalse(eq(l, i));
        assertFalse(eq(l, f));
        assertFalse(eq(l, c));
        assertFalse(eq(l, t));
        assertFalse(eq(l, fn));
        assertFalse(eq(t, s));
        assertFalse(eq(t, b));
        assertFalse(eq(t, i));
        assertFalse(eq(t, f));
        assertFalse(eq(t, c));
        assertFalse(eq(t, l));
        assertFalse(eq(t, fn));
        assertFalse(eq(fn, s));
        assertFalse(eq(fn, b));
        assertFalse(eq(fn, i));
        assertFalse(eq(fn, f));
        assertFalse(eq(fn, c));
        assertFalse(eq(fn, l));
        assertFalse(eq(fn, t));
    }

    @Test
    public void equalMixedTypes() {
        long rand = (long) (Math.random() * 1000);

        Data i = AlchemyInt.of(rand);
        Data f = AlchemyFloat.of(rand);

        assertTrue(eq(i, f));
        assertTrue(eq(f, i));
    }

    @Test
    public void equivalentSequencesEqual() {
        Data s1 = new EagerAlchemyList(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2), AlchemyInt.of(3)));
        Data s2 = new AlchemyRange.AlchemyRangeBuilder().setFirst(AlchemyInt.of(1)).setStop(AlchemyInt.of(4)).build();

        assertTrue(eq(s1, s2));
        assertTrue(eq(s2, s1));
    }

    @Test
    public void stringAndListUnequal() {
        Data s = AlchemyString.of("Hello");
        Data l = new EagerAlchemyList(Arrays.asList(
                AlchemyCharacter.of('H'),
                AlchemyCharacter.of('e'),
                AlchemyCharacter.of('l'),
                AlchemyCharacter.of('l'),
                AlchemyCharacter.of('o')));

        assertFalse(eq(s, l));
        assertFalse(eq(l, s));
    }

    @Test
    public void listAndTupleUnequal() {
        Data t = new AlchemyTuple(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2)));
        Data l = new EagerAlchemyList(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2)));

        assertFalse(eq(t, l));
        assertFalse(eq(l, t));
    }

    @Test
    public void nonEquivalentSequencesNotEqual() {
        Data r1 = new AlchemyRange.AlchemyRangeBuilder().setStop(AlchemyInt.of(100)).build();
        Data r2 = new AlchemyRange.AlchemyRangeBuilder().setFirst(AlchemyInt.of(1)).build();

        assertFalse(eq(r1, r2));
        assertFalse(eq(r2, r1));
    }

    @Test
    public void nonMixedTypes() {
        Data s1 = AlchemyString.of("hello");
        Data b1 = AlchemyBoolean.of(false);
        Data i1 = AlchemyInt.of(1);
        Data f1 = AlchemyFloat.of(-1.0);
        Data c1 = AlchemyCharacter.of("\n");
        Data l1 = new EagerAlchemyList(Arrays.asList(AlchemyCharacter.of('h'), AlchemyCharacter.of('i')));
        Data t1 = new AlchemyTuple(Arrays.asList(AlchemyString.of("a"), AlchemyInt.of(2)));

        Data s2 = AlchemyString.of("hello");
        Data b2 = AlchemyBoolean.of(false);
        Data i2 = AlchemyInt.of(1);
        Data f2 = AlchemyFloat.of(-1.0);
        Data c2 = AlchemyCharacter.of("\n");
        Data l2 = new EagerAlchemyList(Arrays.asList(AlchemyCharacter.of('h'), AlchemyCharacter.of('i')));
        Data t2 = new AlchemyTuple(Arrays.asList(AlchemyString.of("a"), AlchemyInt.of(2)));

        Data s3 = AlchemyString.of("goodbye");
        Data b3 = AlchemyBoolean.of(true);
        Data i3 = AlchemyInt.of(-1);
        Data f3 = AlchemyFloat.of(1.0);
        Data c3 = AlchemyCharacter.of('n');
        Data l3 = new EagerAlchemyList(Arrays.asList(AlchemyCharacter.of('g'), AlchemyCharacter.of('o')));
        Data t3 = new AlchemyTuple(Arrays.asList(AlchemyString.of("a"), AlchemyInt.of(3)));
// s b i f c l t
        assertFalse(eq(s1, s3));
        assertFalse(eq(b1, b3));
        assertFalse(eq(i1, i3));
        assertFalse(eq(f1, f3));
        assertFalse(eq(c1, c3));
        assertFalse(eq(l1, l3));
        assertFalse(eq(t1, t3));

        assertFalse(eq(s3, s1));
        assertFalse(eq(b3, b1));
        assertFalse(eq(i3, i1));
        assertFalse(eq(f3, f1));
        assertFalse(eq(c3, c1));
        assertFalse(eq(l3, l1));
        assertFalse(eq(t3, t1));

        assertTrue(eq(s1, s2));
        assertTrue(eq(b1, b2));
        assertTrue(eq(i1, i2));
        assertTrue(eq(f1, f2));
        assertTrue(eq(c1, c2));
        assertTrue(eq(l1, l2));
        assertTrue(eq(t1, t2));

        assertTrue(eq(s2, s1));
        assertTrue(eq(b2, b1));
        assertTrue(eq(i2, i1));
        assertTrue(eq(f2, f1));
        assertTrue(eq(c2, c1));
        assertTrue(eq(l2, l1));
        assertTrue(eq(t2, t1));
    }

    @Test
    public void functionsNotEqual() {
        Data f1 = new GeneratedFunction(i64(5), new DefaultContext(), Collections.emptyList());
        Data f2 = new GeneratedFunction(i64(5), new DefaultContext(), Collections.emptyList());

        assertFalse(eq(f1, f2));
        assertFalse(eq(f2, f1));

        f2 = f1;
        assertTrue(eq(f1, f2));
        assertTrue(eq(f2, f1));
    }

    @Test
    public void dataNotEqualToBooleanEquivalent() {
        Data s = AlchemyString.of("hello");
        Data i = AlchemyInt.of(1);
        Data f = AlchemyFloat.of(-1.0);
        Data c = AlchemyCharacter.of("\n");
        Data l = new EagerAlchemyList(Arrays.asList(AlchemyCharacter.of('h'), AlchemyCharacter.of('i')));
        Data t = new AlchemyTuple(Arrays.asList(AlchemyString.of("a"), AlchemyInt.of(2)));
        Data fn = new GeneratedFunction(i64(5), new DefaultContext(), Collections.emptyList());

        assertFalse(eq(s, FALSE));
        assertFalse(eq(FALSE, s));

        assertFalse(eq(i, TRUE));
        assertFalse(eq(TRUE, i));

        assertFalse(eq(f, TRUE));
        assertFalse(eq(TRUE, f));

        assertFalse(eq(c, TRUE));
        assertFalse(eq(TRUE, c));

        assertFalse(eq(l, TRUE));
        assertFalse(eq(TRUE, l));

        assertFalse(eq(t, TRUE));
        assertFalse(eq(TRUE, t));

        assertFalse(eq(fn, TRUE));
        assertFalse(eq(TRUE, fn));
    }
}