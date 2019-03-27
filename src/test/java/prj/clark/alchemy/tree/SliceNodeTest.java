package prj.clark.alchemy.tree;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class SliceNodeTest {
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
        Iterator<Data> it = ((Sequenced) (new SliceNode.SliceNodeBuilder(new LiteralNode(exactData)).build().evaluate(null))).iterator();

        for (Data d : rawData) {
            assertEquals(d, it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void takeEvenPositionedElements() {
        Iterator<Data> it = ((Sequenced) new SliceNode.SliceNodeBuilder(new LiteralNode(exactData))
                .setSkip(new LiteralNode(AlchemyInt.of(2)))
                .build()
                .evaluate(null))
                .iterator();

        for (int i = 0; i < rawData.size(); i += 2) {
            assertEquals(rawData.get(i), it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void dropTwoElements() {
        Iterator<Data> it = ((Sequenced) new SliceNode.SliceNodeBuilder(new LiteralNode(exactData))
                .setStart(new LiteralNode(AlchemyInt.of(2)))
                .build()
                .evaluate(null))
                .iterator();

        for (int i = 2; i < rawData.size(); ++i) {
            assertEquals(rawData.get(i), it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void takeThreeElements() {
        Iterator<Data> it = ((Sequenced) new SliceNode.SliceNodeBuilder(new LiteralNode(exactData))
                .setStop(new LiteralNode(AlchemyInt.of(3)))
                .build()
                .evaluate(null))
                .iterator();

        for (int i = 0; i < 3; ++i) {
            assertEquals(rawData.get(i), it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test
    public void complexSlice() {
        // Should go through elements 2, 5, 8.
        Iterator<Data> it = ((Sequenced) new SliceNode.SliceNodeBuilder(new LiteralNode(exactData))
                .setStart(new LiteralNode(AlchemyInt.of(2)))
                .setStop(new LiteralNode(AlchemyInt.of(9)))
                .setSkip(new LiteralNode(AlchemyInt.of(3)))
                .build()
                .evaluate(null))
                .iterator();

        for (int i = 2; i < 9; i += 3) {
            assertEquals(rawData.get(i), it.next());
        }

        assertFalse(it.hasNext());
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStartThrowsException() {
        Iterator<Data> it = ((Sequenced) new SliceNode.SliceNodeBuilder(new LiteralNode(exactData))
                .setStart(new LiteralNode(AlchemyFloat.of(1.25)))
                .build()
                .evaluate(null))
                .iterator();

        it.next();
    }

    @Test(expected = TypeMismatchException.class)
    public void floatStopThrowsException() {
        Iterator<Data> it = ((Sequenced) new SliceNode.SliceNodeBuilder(new LiteralNode(exactData))
                .setStop(new LiteralNode(AlchemyFloat.of(0.625)))
                .build()
                .evaluate(null))
                .iterator();

        it.next();
    }

    @Test(expected = TypeMismatchException.class)
    public void floatSkipThrowsException() {
        Iterator<Data> it = ((Sequenced) new SliceNode.SliceNodeBuilder(new LiteralNode(exactData))
                .setSkip(new LiteralNode(AlchemyFloat.of(17.5)))
                .build()
                .evaluate(null))
                .iterator();

        it.next();
    }

    @Test
    public void basicIndexCheck() {
        AlchemySlice slice = ((AlchemySlice) new SliceNode.SliceNodeBuilder(new LiteralNode(exactData))
                .setStart(new LiteralNode(AlchemyInt.of(2)))
                .setStop(new LiteralNode(AlchemyInt.of(9)))
                .setSkip(new LiteralNode(AlchemyInt.of(3)))
                .build()
                .evaluate(null));

        assertEquals(slice.getIndex(AlchemyInt.of(0)).get(), rawData.get(2));
        assertEquals(slice.getIndex(AlchemyInt.of(1)).get(), rawData.get(5));
        assertEquals(slice.getIndex(AlchemyInt.of(2)).get(), rawData.get(8));

        assertFalse(slice.getIndex(AlchemyInt.of(3)).isPresent());
    }

    @Test
    public void droppedIndexCheck() {
        AlchemySlice slice = ((AlchemySlice) new SliceNode.SliceNodeBuilder(new LiteralNode(exactData))
                .setStop(new LiteralNode(AlchemyInt.of(7)))
                .setSkip(new LiteralNode(AlchemyInt.of(2)))
                .build()
                .evaluate(null));

        assertEquals(slice.getIndex(AlchemyInt.of(0)).get(), rawData.get(0));
        assertEquals(slice.getIndex(AlchemyInt.of(1)).get(), rawData.get(2));
        assertEquals(slice.getIndex(AlchemyInt.of(2)).get(), rawData.get(4));
        assertEquals(slice.getIndex(AlchemyInt.of(3)).get(), rawData.get(6));

        // Would otherwise align with rawData[8].
        assertFalse(slice.getIndex(AlchemyInt.of(4)).isPresent());
    }
}