package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Iterator;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.*;

public class RangeCreationNodeTest {
    private AlchemyRange range;
    private RangeCreationNode rcn;

    private void init(long first, long second, long last) {
        range = new AlchemyRange.AlchemyRangeBuilder()
                .setFirst(AlchemyInt.of(first))
                .setSecond(AlchemyInt.of(second))
                .setStop(AlchemyInt.of(last))
                .build();

        rcn = new RangeCreationNode(i64(first), i64(second), i64(last));
    }

    private void init(double first, double second, double last) {
        range = new AlchemyRange.AlchemyRangeBuilder()
                .setFirst(AlchemyFloat.of(first))
                .setSecond(AlchemyFloat.of(second))
                .setStop(AlchemyFloat.of(last))
                .build();

        rcn = new RangeCreationNode(f64(first), f64(second), f64(last));
    }

    private static void compare(Iterable<Data> range, Valued rcn) {
        Iterator<Data> expected = range.iterator();
        for (Data data : ((Sequenced) rcn.evaluate(null))) {
            assertEquals(expected.next(), data);
        }

        assertFalse(expected.hasNext());
    }

    @Test
    public void toTen() {
        init(0, 1, 11);

        compare(range, rcn);
    }

    @Test
    public void toTwentiesEven() {
        init(0, 2, 21);

        compare(range, rcn);
    }

    @Test
    public void printAndToStringSame() {
        init(0, 1, 10);
        assertEquals(((Printable)rcn.evaluate(null)).print(), rcn.evaluate(null).toString());
    }

    @Test
    public void printBehavesCorrectly() {
        init(0, 1, 10);
        assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", ((Printable)rcn.evaluate(null)).print());

        init(-5, -4, 1);
        assertEquals("[-5, -4, -3, -2, -1, 0]", ((Printable)rcn.evaluate(null)).print());
    }

    @Test
    public void sliceBehavesCorrectly() {
        init(0, 1, Double.POSITIVE_INFINITY);

        Iterator<Data> expected = range.slice(AlchemyInt.of(1), AlchemyInt.of(500), AlchemyInt.of(3)).iterator();
        Iterator<Data> actual = ((AlchemyList)rcn.evaluate(null))
                .slice(AlchemyInt.of(1), AlchemyInt.of(500), AlchemyInt.of(3))
                .iterator();

        for (long i = 1; i < 500; i += 3) {
            assertEquals(expected.next(), actual.next());
        }

        assertFalse(actual.hasNext());
    }

    @Test
    public void negativeIndexThrowsException() {
        init(-1, 3, 50);
        assertFalse(((Indexed)rcn.evaluate(null)).getIndex(AlchemyInt.of(-1)).isPresent());
    }

    @Test
    public void largeIndexReturnsNothing() {
        init(1, 5, 10);
        Indexed actual = (Indexed) (rcn.evaluate(null));
        assertTrue(actual.getIndex(AlchemyInt.of(0)).isPresent());
        assertTrue(actual.getIndex(AlchemyInt.of(1)).isPresent());
        assertTrue(actual.getIndex(AlchemyInt.of(2)).isPresent());
        assertFalse(actual.getIndex(AlchemyInt.of(3)).isPresent());
    }

    @Test(expected = TypeMismatchException.class)
    public void floatIndexThrowsException() {
        init(1, 2, 10);
        ((Indexed)rcn.evaluate(null)).getIndex(AlchemyFloat.of(2.5));
    }

    @Test
    public void floatingPointRange() {
        init(0.5, 1, 4.1);

        compare(range, rcn);
    }

    @Test
    public void stopLessThanStartYieldsNoElements() {
        init(1, 0, 5);
        Iterator<Data> it = ((Sequenced)rcn.evaluate(null)).iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void negativeRange() {
        init(-10, -8, 1);

        compare(range, rcn);
    }

    @Test
    public void firstValueDefaultsCorrectly() {
        // Here we assume that the first element is set correctly, and assume that 1 is second.
        range = new AlchemyRange.AlchemyRangeBuilder().setSecond(AlchemyInt.of(1)).setStop(AlchemyInt.of(5)).build();
        rcn = new RangeCreationNode(null, i64(1), i64(5));

        compare(range, rcn);

        int i = 0;

        for (Data d : range) {
            assertEquals(AlchemyInt.of(i++), d);
            assertTrue(i <= 5);
        }

        assertEquals(i, 5);
    }

    @Test
    public void secondValueDefaultsCorrectly() {
        range = new AlchemyRange.AlchemyRangeBuilder().setFirst(AlchemyInt.of(-5)).setStop(AlchemyInt.of(1)).build();
        rcn = new RangeCreationNode(i64(-5), null, i64(1));

        compare(range, rcn);

        int i = -5;

        for (Data d : range) {
            assertEquals(AlchemyInt.of(i++), d);
            assertTrue(i <= 1);
        }

        assertEquals(i, 1);
    }

    @Test
    public void thirdValueDefaultsCorrectly() {
        range = new AlchemyRange.AlchemyRangeBuilder().setFirst(AlchemyInt.of(5)).setSecond(AlchemyInt.of(6)).build();
        rcn = new RangeCreationNode(i64(5), i64(6), null);

        compare(range, rcn);

        // We can't exactly check that the sequence is infinite, so we just check the first couple billion elements.
        for (int i = 5; i < Integer.MAX_VALUE; ++i) {
            assertEquals(AlchemyInt.of(i), range.getIndex(AlchemyInt.of(i - 5)).get());
        }
    }
}