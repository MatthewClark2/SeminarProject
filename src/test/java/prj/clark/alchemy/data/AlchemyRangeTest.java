package prj.clark.alchemy.data;

import org.junit.Test;
import prj.clark.alchemy.err.TypeMismatchException;

import java.lang.reflect.Type;
import java.util.Iterator;

import static org.junit.Assert.*;

public class AlchemyRangeTest {
    private AlchemyRange range;

    private void init(long first, long second, long last) {
        range = new AlchemyRange.AlchemyRangeBuilder()
                .setFirst(AlchemyInt.of(first))
                .setSecond(AlchemyInt.of(second))
                .setStop(AlchemyInt.of(last))
                .build();
    }

    private void init(double first, double second, double last) {
        range = new AlchemyRange.AlchemyRangeBuilder()
                .setFirst(AlchemyFloat.of(first))
                .setSecond(AlchemyFloat.of(second))
                .setStop(AlchemyFloat.of(last))
                .build();
    }

    @Test
    public void toTen() {
        init(0, 1, 11);

        int N = 11;
        Data[] data = new Data[N];

        for (int i = 0; i < N; ++i) {
            Data d = AlchemyInt.of(i);
            assertEquals(d, range.getIndex(d).get());
            data[i] = d;
        }

        Iterator<Data> it = range.iter();

        for (Data d : data) {
            assertEquals(d, it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void toTwentiesEven() {
        init(0, 2, 21);

        int N = 10;
        Data[] data = new Data[N];

        for (int i = 0; i < N; ++i) {
            Data d = AlchemyInt.of(2*i);
            assertEquals(d, range.getIndex(AlchemyInt.of(i)).get());
            data[i] = d;
        }

        Iterator<Data> it = range.iter();

        for (Data d : data) {
            Data actual = it.next();
            assertEquals(d, actual);
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void printAndToStringSame() {
        init(0, 1, 10);
        assertEquals(range.print(), range.toString());
    }

    @Test
    public void printBehavesCorrectly() {
        init(0, 1, 10);
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", range.print());

        init(-5, -4, 1);
        assertEquals("[-5, -4, -3, -2, -1, 0]", range.print());
    }

    //@Test
    public void infiniteRangeHoldsAtLeast2e64Elements() {
        init(0.0, 1, Double.POSITIVE_INFINITY);

        for (long i = 0; i < Long.MAX_VALUE; ++i) {
            assertEquals(AlchemyInt.of(i), range.getIndex(AlchemyInt.of(i)).get());
        }
    }

    @Test
    public void sliceBehavesCorrectly() {
        init(0, 1, Double.POSITIVE_INFINITY);

        Iterator<Data> it = range.slice(AlchemyInt.of(1), AlchemyInt.of(500), AlchemyInt.of(3)).iter();
        for (long i = 1; i < 500; i += 3) {
            assertEquals(AlchemyInt.of(i), it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void negativeIndexThrowsException() {
        init(-1, 3, 50);
        assertFalse(range.getIndex(AlchemyInt.of(-1)).isPresent());
    }

    @Test
    public void largeIndexReturnsNothing() {
        init(1, 5, 10);
        assertTrue(range.getIndex(AlchemyInt.of(0)).isPresent());
        assertTrue(range.getIndex(AlchemyInt.of(1)).isPresent());
        assertTrue(range.getIndex(AlchemyInt.of(2)).isPresent());
        assertFalse(range.getIndex(AlchemyInt.of(3)).isPresent());
    }

    @Test(expected = TypeMismatchException.class)
    public void floatIndexThrowsException() {
        init(1, 2, 10);
        range.getIndex(AlchemyFloat.of(2.5));
    }

    @Test
    public void floatingPointRange() {
        init(0.5, 1, 4.1);

        int N = 8;
        Data[] data = new Data[N];

        for (double i = 0.5; i < 4.1; i += 0.5) {
            Data d = AlchemyFloat.of(i);
            long j = (long) (i * 2) - 1;
            assertEquals(d, range.getIndex(AlchemyInt.of(j)).get());
            data[(int)j] = d;
        }

        Iterator<Data> it = range.iter();

        for (Data d : data) {
            assertEquals(d, it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void stopLessThanStartYieldsNoElements() {
        init(1, 0, 5);
        Iterator<Data> it = range.iter();
        assertFalse(it.hasNext());
    }

    @Test
    public void negativeRange() {
        init(-10, -8, 1);
        init(0, 2, 21);

        int N = 6;
        Data[] data = new Data[N];

        for (int i = 0; i <= N; ++i) {
            Data d = AlchemyInt.of(-10-2*i);
            assertEquals(d, range.getIndex(AlchemyInt.of(i)).get());
            data[i] = d;
        }

        Iterator<Data> it = range.iter();

        for (Data d : data) {
            assertEquals(d, it.next());
        }

        assertFalse(it.hasNext());
    }
}