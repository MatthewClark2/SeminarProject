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

/**
 * Extraordinarily thorough unit testing using basic types. This also serves to check for equality and inequality
 * between types.
 */
public class NotEqualNodeTest {
    private static boolean neq(Valued l, Valued r) {
        Data d = new NotEqualNode(l, r).evaluate(new DefaultContext());
        assertTrue(d instanceof AlchemyBoolean);
        return d.toBoolean();
    }

    private static boolean neq(Data l, Data r) {
        return neq(new LiteralNode(l), new LiteralNode(r));
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
        assertTrue(neq(s, b));
        assertTrue(neq(s, i));
        assertTrue(neq(s, f));
        assertTrue(neq(s, c));
        assertTrue(neq(s, l));
        assertTrue(neq(s, t));
        assertTrue(neq(s, fn));
        assertTrue(neq(b, s));
        assertTrue(neq(b, i));
        assertTrue(neq(b, f));
        assertTrue(neq(b, c));
        assertTrue(neq(b, l));
        assertTrue(neq(b, t));
        assertTrue(neq(b, fn));
        assertTrue(neq(i, s));
        assertTrue(neq(i, b));
        assertTrue(neq(i, f));
        assertTrue(neq(i, c));
        assertTrue(neq(i, l));
        assertTrue(neq(i, t));
        assertTrue(neq(i, fn));
        assertTrue(neq(f, s));
        assertTrue(neq(f, b));
        assertTrue(neq(f, i));
        assertTrue(neq(f, c));
        assertTrue(neq(f, l));
        assertTrue(neq(f, t));
        assertTrue(neq(f, fn));
        assertTrue(neq(c, s));
        assertTrue(neq(c, b));
        assertTrue(neq(c, i));
        assertTrue(neq(c, f));
        assertTrue(neq(c, l));
        assertTrue(neq(c, t));
        assertTrue(neq(c, fn));
        assertTrue(neq(l, s));
        assertTrue(neq(l, b));
        assertTrue(neq(l, i));
        assertTrue(neq(l, f));
        assertTrue(neq(l, c));
        assertTrue(neq(l, t));
        assertTrue(neq(l, fn));
        assertTrue(neq(t, s));
        assertTrue(neq(t, b));
        assertTrue(neq(t, i));
        assertTrue(neq(t, f));
        assertTrue(neq(t, c));
        assertTrue(neq(t, l));
        assertTrue(neq(t, fn));
        assertTrue(neq(fn, s));
        assertTrue(neq(fn, b));
        assertTrue(neq(fn, i));
        assertTrue(neq(fn, f));
        assertTrue(neq(fn, c));
        assertTrue(neq(fn, l));
        assertTrue(neq(fn, t));
    }

    @Test
    public void equalMixedTypes() {
        long rand = (long) (Math.random() * 1000);

        Data i = AlchemyInt.of(rand);
        Data f = AlchemyFloat.of(rand);

        assertFalse(neq(i, f));
        assertFalse(neq(f, i));
    }

    @Test
    public void equivalentSequencesEqual() {
        Data s1 = new EagerAlchemyList(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2), AlchemyInt.of(3)));
        Data s2 = new AlchemyRange.AlchemyRangeBuilder().setFirst(AlchemyInt.of(1)).setStop(AlchemyInt.of(4)).build();

        assertFalse(neq(s1, s2));
        assertFalse(neq(s2, s1));
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

        assertTrue(neq(s, l));
        assertTrue(neq(l, s));
    }

    @Test
    public void listAndTupleUnequal() {
        Data t = new AlchemyTuple(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2)));
        Data l = new EagerAlchemyList(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2)));

        assertTrue(neq(t, l));
        assertTrue(neq(l, t));
    }

    @Test
    public void nonEquivalentSequencesNotEqual() {
        Data r1 = new AlchemyRange.AlchemyRangeBuilder().setStop(AlchemyInt.of(100)).build();
        Data r2 = new AlchemyRange.AlchemyRangeBuilder().setFirst(AlchemyInt.of(1)).build();

        assertTrue(neq(r1, r2));
        assertTrue(neq(r2, r1));
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
        assertTrue(neq(s1, s3));
        assertTrue(neq(b1, b3));
        assertTrue(neq(i1, i3));
        assertTrue(neq(f1, f3));
        assertTrue(neq(c1, c3));
        assertTrue(neq(l1, l3));
        assertTrue(neq(t1, t3));

        assertTrue(neq(s3, s1));
        assertTrue(neq(b3, b1));
        assertTrue(neq(i3, i1));
        assertTrue(neq(f3, f1));
        assertTrue(neq(c3, c1));
        assertTrue(neq(l3, l1));
        assertTrue(neq(t3, t1));

        assertFalse(neq(s1, s2));
        assertFalse(neq(b1, b2));
        assertFalse(neq(i1, i2));
        assertFalse(neq(f1, f2));
        assertFalse(neq(c1, c2));
        assertFalse(neq(l1, l2));
        assertFalse(neq(t1, t2));

        assertFalse(neq(s2, s1));
        assertFalse(neq(b2, b1));
        assertFalse(neq(i2, i1));
        assertFalse(neq(f2, f1));
        assertFalse(neq(c2, c1));
        assertFalse(neq(l2, l1));
        assertFalse(neq(t2, t1));
    }

    @Test
    public void functionsNotEqual() {
        Data f1 = new GeneratedFunction(i64(5), new DefaultContext(), Collections.emptyList());
        Data f2 = new GeneratedFunction(i64(5), new DefaultContext(), Collections.emptyList());

        assertTrue(neq(f1, f2));
        assertTrue(neq(f2, f1));


        f2 = f1;
        assertFalse(neq(f1, f2));
        assertFalse(neq(f2, f1));
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

        assertTrue(neq(s, FALSE));
        assertTrue(neq(FALSE, s));

        assertTrue(neq(i, TRUE));
        assertTrue(neq(TRUE, i));

        assertTrue(neq(f, TRUE));
        assertTrue(neq(TRUE, f));

        assertTrue(neq(c, TRUE));
        assertTrue(neq(TRUE, c));

        assertTrue(neq(l, TRUE));
        assertTrue(neq(TRUE, l));

        assertTrue(neq(t, TRUE));
        assertTrue(neq(TRUE, t));

        assertTrue(neq(fn, TRUE));
        assertTrue(neq(TRUE, fn));
    }
}