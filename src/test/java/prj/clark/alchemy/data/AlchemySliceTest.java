package prj.clark.alchemy.data;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class AlchemySliceTest {
    private List<Data> rawData = Arrays.asList(
            AlchemyInt.of(0),
            AlchemyInt.of(1),
            AlchemyInt.of(2),
            AlchemyInt.of(3),
            AlchemyInt.of(4),
            AlchemyInt.of(5),
            AlchemyInt.of(6),
            AlchemyInt.of(7),
            AlchemyInt.of(8),
            AlchemyInt.of(9)
    );

    private Sliceable exactData;
    private Sliceable endlessData;

    @Before
    public void setUp() {
        exactData = new EagerAlchemyList(rawData);

        // Initialize endless data with a range.
    }

    @Test
    public void defaultIteratesOverListAsGiven() {
        Iterator<Data> it = new AlchemySlice.AlchemySliceBuilder(exactData).build().iterator();

        for (Data d : rawData) {
            assertEquals(d, it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void takeEvenPositionedElements() {
        Iterator<Data> it = new AlchemySlice.AlchemySliceBuilder(exactData)
                .setSkip(AlchemyInt.of(2))
                .build()
                .iterator();

        for (int i = 0; i < rawData.size(); i += 2) {
            assertEquals(rawData.get(i), it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void dropTwoElements() {
        Iterator<Data> it = new AlchemySlice.AlchemySliceBuilder(exactData)
                .setStart(AlchemyInt.of(2))
                .build()
                .iterator();

        for (int i = 2; i < rawData.size(); ++i) {
            assertEquals(rawData.get(i), it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void takeThreeElements() {
        Iterator<Data> it = new AlchemySlice.AlchemySliceBuilder(exactData)
                .setStop(AlchemyInt.of(3))
                .build()
                .iterator();

        for (int i = 0; i < 3; ++i) {
            assertEquals(rawData.get(i), it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void complexSlice() {
        // Should go through elements 2, 5, 8.
        Iterator<Data> it = new AlchemySlice.AlchemySliceBuilder(exactData)
                .setStart(AlchemyInt.of(2))
                .setStop(AlchemyInt.of(9))
                .setSkip(AlchemyInt.of(3))
                .build()
                .iterator();

        for (int i = 2; i < 9; i += 3) {
            assertEquals(rawData.get(i), it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStartThrowsException() {
        Iterator<Data> it = new AlchemySlice.AlchemySliceBuilder(exactData)
                .setStart(AlchemyFloat.of(2.75))
                .build()
                .iterator();

        it.next();
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStopThrowsException() {
        Iterator<Data> it = new AlchemySlice.AlchemySliceBuilder(exactData)
                .setStop(AlchemyFloat.of(2.75))
                .build()
                .iterator();

        it.next();
    }

    @Test(expected = TypeMismatchException.class)
    public void floatSkipThrowsException() {
        Iterator<Data> it = new AlchemySlice.AlchemySliceBuilder(exactData)
                .setSkip(AlchemyFloat.of(2.75))
                .build()
                .iterator();

        it.next();
    }

    @Test
    public void basicIndexCheck() {
        AlchemySlice slice = new AlchemySlice.AlchemySliceBuilder(exactData)
                .setStart(AlchemyInt.of(2))
                .setStop(AlchemyInt.of(9))
                .setSkip(AlchemyInt.of(3))
                .build();

        assertEquals(slice.getIndex(AlchemyInt.of(0)).get(), rawData.get(2));
        assertEquals(slice.getIndex(AlchemyInt.of(1)).get(), rawData.get(5));
        assertEquals(slice.getIndex(AlchemyInt.of(2)).get(), rawData.get(8));

        assertFalse(slice.getIndex(AlchemyInt.of(3)).isPresent());
    }

    @Test
    public void droppedIndexCheck() {
        AlchemySlice slice = new AlchemySlice.AlchemySliceBuilder(exactData)
                .setStop(AlchemyInt.of(7))
                .setSkip(AlchemyInt.of(2))
                .build();

        assertEquals(slice.getIndex(AlchemyInt.of(0)).get(), rawData.get(0));
        assertEquals(slice.getIndex(AlchemyInt.of(1)).get(), rawData.get(2));
        assertEquals(slice.getIndex(AlchemyInt.of(2)).get(), rawData.get(4));
        assertEquals(slice.getIndex(AlchemyInt.of(3)).get(), rawData.get(6));

        // Would otherwise align with rawData[8].
        assertFalse(slice.getIndex(AlchemyInt.of(4)).isPresent());
    }

    @Test
    public void equivalentSlicesEquivalent() {
        AlchemySlice slice1 = new AlchemySlice.AlchemySliceBuilder(exactData).setStart(AlchemyInt.of(1)).build();
        AlchemySlice slice2 = new AlchemySlice.AlchemySliceBuilder(exactData).setStart(AlchemyInt.of(1)).build();
        assertEquals(slice1, slice2);
    }

    @Test
    public void unequalSlicesNotEquivalent() {
        AlchemySlice slice1 = new AlchemySlice.AlchemySliceBuilder(exactData).setStart(AlchemyInt.of(2)).build();
        AlchemySlice slice2 = new AlchemySlice.AlchemySliceBuilder(exactData).setStart(AlchemyInt.of(1)).build();
        assertNotEquals(slice1, slice2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeIndexThrowsException() {
        AlchemySlice slice = new AlchemySlice.AlchemySliceBuilder(exactData).build();
        slice.getIndex(AlchemyInt.of(-1));
    }
}