package prj.clark.alchemy.data;

import org.junit.After;
import org.junit.Test;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.Assert.*;

public class AlchemyTupleTest {
    private AlchemyTuple tuple;

    // Easy use factory methods.
    private static Data f64(double d) {
        return AlchemyFloat.of(d);
    }

    private static Data i64(long l) {
        return AlchemyInt.of(l);
    }

    private static Data str(String s) {
        return AlchemyString.of(s);
    }

    private static Data ch(int cp) {
        return AlchemyCharacter.of(cp);
    }

    @After
    public void tearDown() {
        tuple = null;
    }

    private void init(Data... ds) {
        tuple = new AlchemyTuple(Arrays.asList(ds));
    }

    @Test
    public void iteratorContainsCorrectElements() {
        Data[] data = new Data[]{i64(12), str("asfd")};
        init(data);

        Iterator i = tuple.iterator();
        for (Data d : data) {
            assertEquals(d, i.next());
        }

        assertFalse(i.hasNext());
    }

    @Test
    public void toStringBehavesCorrectly() {
        Data[] data = new Data[]{i64(1), i64(2), i64(3), i64(4), i64(5)};
        init(data);
        assertEquals("(1, 2, 3, 4, 5)", tuple.toString());
    }

    @Test
    public void toStringAndPrintIdentical() {
        Data[] data = new Data[]{i64(1), i64(2), i64(3), i64(4), i64(5)};
        init(data);
        assertEquals(tuple.toString(), tuple.print());
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeIndexThrowsException() {
        Data[] data = new Data[]{i64(1), i64(2), i64(3), i64(4), i64(5)};
        init(data);
        tuple.getIndex(AlchemyInt.of(-1));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonNumericIndexThrowsException() {
        init();

        tuple.getIndex(AlchemyString.of(""));
    }

    @Test
    public void tuplesComparable() {
        Data[] data = new Data[]{i64(1), i64(2), i64(3), i64(4), i64(5)};
        init(data);

        AlchemyTuple tuple2 = new AlchemyTuple(Arrays.asList(data));

        assertEquals(tuple, tuple2);

        tuple = new AlchemyTuple(Collections.emptyList());
        assertNotEquals(tuple, tuple2);
    }

    @Test
    public void tupleInequality() {
        Data[] data1 = new Data[]{i64(1), i64(2), i64(3)};
        Data[] data2 = new Data[]{i64(2), i64(3), i64(4)};

        tuple = new AlchemyTuple(Arrays.asList(data1));
        AlchemyTuple tuple2 = new AlchemyTuple(Arrays.asList(data2));

        assertNotEquals(tuple, tuple2);
    }

    @Test
    public void emptyTuplePrintsCorrectly() {
        init();
        assertEquals("()", tuple.print());
    }
}