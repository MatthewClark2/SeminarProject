package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.DefaultContext;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.*;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.*;

public class ConcatenationNodeTest {

    private class SampleSequence implements Chainable {
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

    private class FailingSequence implements Chainable {
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

    private static Sequenced concat(Valued left, Valued right) {
        Data d = new ConcatenationNode(left, right).evaluate(new DefaultContext());

        assertTrue(d instanceof Sequenced);
        return (Sequenced)d;
    }

    @Test
    public void correctlyPrependsData() {
        Data[] expected = new Data[]{AlchemyInt.of(1), AlchemyString.of("hello"), AlchemyString.of("world")};

        Data base = new EagerAlchemyList(Arrays.asList(expected[1], expected[2]));

        int i = 0;
        for (Data d : concat(i64(1), new LiteralNode(base))) {
            assertEquals(expected[i++], d);
        }
    }

    @Test
    public void correctlyAppendsData() {
        Data[] expected = new Data[]{AlchemyString.of("hello"), AlchemyString.of("world"), AlchemyFloat.of(11.125)};

        Data base = new EagerAlchemyList(Arrays.asList(expected[0], expected[1]));

        int i = 0;
        for (Data d : concat(new LiteralNode(base), f64(11.125))) {
            assertEquals(expected[i++], d);
        }
    }

    @Test
    public void ableToPrependToInfiniteSequence() {
        Data infiniteSequence = new AlchemyRange.AlchemyRangeBuilder().build();

        Iterator<Data> result = concat(i64(-1), new LiteralNode(infiniteSequence)).iterator();

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

        Data[] expected = new Data[]{AlchemyInt.of(-100), AlchemyString.of("foo"), AlchemyBoolean.FALSE, new AlchemyTuple(Collections.emptyList())};

        Sequenced cat = concat(new LiteralNode(base), new LiteralNode(new AlchemyTuple(Collections.emptyList())));

        assertEquals(3, base.i);

        int i = 0;
        for (Data d : cat) {
            assertEquals(expected[i++], d);
        }
    }

    @Test
    public void prependingDoesNotEvaluateInitialSequence() {
        concat(i64(1), new LiteralNode(new FailingSequence()));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotConcatTwoNonSequenceValues() {
        concat(i64(1), f64(11));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotPrependWithTuple() {
        concat(i64(1), new LiteralNode(new AlchemyTuple(Collections.emptyList())));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotAppendWithTuple() {
        concat(new LiteralNode(new AlchemyTuple(Collections.emptyList())), bool(true));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotPrependWithString() {
        concat(i64(1), str("Hello"));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotAppendWithString() {
        concat(str("KONO DIO DA"), bool(false));
    }

    // TODO(matthew-c21) - Check dictionaries.
}