package prj.clark.alchemy.data;

import org.junit.Test;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static prj.clark.alchemy.data.ConcatenatedSequence.concat;

public class ConcatenatedSequenceTest {
    private class SampleSequence implements Sequenced {
        int i = 0;
        List<Data> data = Arrays.asList(AlchemyInt.of(-100), AlchemyString.of("foo"), AlchemyBoolean.FALSE);
        @Override
        public Iterator<Data> iterator() {
            return new Iterator<Data>() {
                Iterator<Data> it = data.iterator();
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public Data next() {
                    i++;
                    return it.next();
                }
            };
        }

        @Override
        public boolean terminates() {
            return true;
        }
    }

    private class FailingSequence implements Sequenced {
        @Override
        public Iterator<Data> iterator() {
            fail();
            return null;
        }

        @Override
        public boolean terminates() {
            fail();
            return false;
        }
    }

    @Test
    public void correctlyPrependsData() {
        Data[] expected = new Data[]{AlchemyInt.of(1), AlchemyString.of("hello"), AlchemyString.of("world")};

        Sequenced base = new EagerAlchemyList(Arrays.asList(expected[1], expected[2]));

        int i = 0;
        for (Data d : concat(AlchemyInt.of(1), base)) {
            assertEquals(expected[i++], d);
        }
    }

    @Test
    public void correctlyAppendsData() {
        Data[] expected = new Data[]{AlchemyString.of("hello"), AlchemyString.of("world"), AlchemyFloat.of(11.125)};

        Sequenced base = new EagerAlchemyList(Arrays.asList(expected[0], expected[1]));

        int i = 0;
        for (Data d : concat(base, AlchemyFloat.of(11.125))) {
            assertEquals(expected[i++], d);
        }
    }

    @Test
    public void ableToPrependToInfiniteSequence() {
        Sequenced infiniteSequence = new AlchemyRange.AlchemyRangeBuilder().build();

        Iterator<Data> result = concat(AlchemyInt.of(-1), infiniteSequence).iterator();

        // Just check the first several elements of the range to make sure that it's correct.
        for (int i = -1; i < 50; ++i) {
            assertEquals(AlchemyInt.of(i), result.next());
        }
    }

    @Test
    public void appendingEvaluatesInitialSequence() {
        // We have a specialized class with pre-populated elements that keeps a track of how many times it has been
        // accessed.
        SampleSequence base = new SampleSequence();

        Data[] expected = new Data[]{AlchemyInt.of(-100), AlchemyString.of("foo"), AlchemyBoolean.FALSE, AlchemyInt.of(100)};

        Sequenced cat = concat(base, AlchemyInt.of(100));

        assertEquals(3, base.i);

        int i = 0;
        for (Data d : cat) {
            assertEquals(expected[i++], d);
        }
    }

    @Test
    public void prependingDoesNotEvaluateInitialSequence() {
        concat(AlchemyInt.of(32), new FailingSequence());
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotPrependWithTuple() {
        concat(AlchemyInt.of(1), new AlchemyTuple(Collections.emptyList()));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotAppendWithTuple() {
        concat(new AlchemyTuple(Collections.emptyList()), AlchemyBoolean.of(true));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotPrependWithString() {
        concat(AlchemyInt.of(1), AlchemyString.of("Hello"));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotAppendWithString() {
        concat(AlchemyString.of("KONO DIO DA"), AlchemyBoolean.of(false));
    }

    @Test
    public void toStringAndPrintSame() {
        Sequenced base = new EagerAlchemyList(Arrays.asList(AlchemyString.of("hello"), AlchemyString.of("world")));
        Sequenced cat = concat(AlchemyInt.of(1), base);

        assertEquals(cat.print(), cat.toString());
    }

    @Test
    public void printCorrectForAppendedSequence() {
        Sequenced base = new EagerAlchemyList(Arrays.asList(AlchemyString.of("hello"), AlchemyString.of("world")));

        assertEquals("1helloworld", concat(AlchemyInt.of(1), base).print());
    }

    @Test
    public void printCorrectForPrependedSequence() {
        Sequenced base = new EagerAlchemyList(Arrays.asList(AlchemyString.of("hello"), AlchemyString.of("world")));

        assertEquals("helloworld1", concat(base, AlchemyInt.of(1)).print());
    }

    // TODO(matthew-c21) - Check dictionaries.
}