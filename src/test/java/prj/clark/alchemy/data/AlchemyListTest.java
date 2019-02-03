package prj.clark.alchemy.data;

import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class AlchemyListTest {
    private AlchemyList list;

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
        list = null;
    }

    private void init(Data... ds) {
        list = new AlchemyList(Arrays.asList(ds));
    }

    @Test
    public void iteratorContainsCorrectElements() {
        Data[] data = new Data[]{i64(12), str("asfd")};
        init(data);

        Iterator i = list.iter();
        for (Data d : data) {
            assertEquals(d, i.next());
        }

        assertFalse(i.hasNext());
    }

    @Test
    public void equalListsShareHashCodes() {
        Data[] data = new Data[]{i64(1), i64(2), i64(3)};
        init(data);

        AlchemyList list2 = new AlchemyList(Arrays.asList(i64(1), i64(2), i64(3)));
        assertEquals(list.hashCode(), list2.hashCode());
    }

    @Test
    public void unequalListsDoNotShareHashCodes() {
        Data[] data = new Data[]{i64(1), i64(2), i64(3)};
        init(data);

        AlchemyList list2 = new AlchemyList(Arrays.asList(i64(2), i64(2), i64(3)));
        assertNotEquals(list.hashCode(), list2.hashCode());
    }

    @Test
    public void sliceContainsCorrectElements() {
        Data[] data = new Data[]{i64(12), str("a"), f64(1.0), ch('a'), i64(11)};
        init(data);

        Data[] slice = new Data[]{data[1], data[2], data[3]};

        Iterator i = list.slice(AlchemyInt.of(1), AlchemyInt.of(4), AlchemyInt.of(1)).iter();

        for (Data d : slice) {
            assertEquals(d, i.next());
        }

        assertFalse(i.hasNext());
    }

    @Test
    public void skipSliceContainsCorrectElements() {
        Data[] data = new Data[]{i64(12), str("a"), f64(1.0), ch('a'), i64(11)};
        init(data);

        Data[] slice = new Data[]{data[0], data[2], data[4]};

        Iterator i = list.slice(AlchemyInt.of(0), AlchemyInt.of(5), AlchemyInt.of(1)).iter();

        for (Data d : slice) {
            assertEquals(d, i.next());
        }

        assertFalse(i.hasNext());
    }

    @Test
    public void toStringBehavesCorrectly() {
        Data[] data = new Data[]{i64(1), i64(2), i64(3), i64(4), i64(5)};
        init(data);
        assertEquals("[1, 2, 3, 4, 5]", list.toString());
    }

    @Test
    public void toStringAndPrintIdentical() {
        Data[] data = new Data[]{i64(1), i64(2), i64(3), i64(4), i64(5)};
        init(data);
        assertEquals(list.toString(), list.print());
    }
}