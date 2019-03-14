package prj.clark.alchemy;

import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.AlchemyInt;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Sequenced;
import prj.clark.alchemy.tree.LiteralNode;
import prj.clark.alchemy.tree.Valued;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Class containing helpful methods for generating or comparing data used across multiple tests. It contains only static
 * methods, and may not be directly instantiated.
 */
public class TestUtils {
    private TestUtils() {}

    /**
     * Ensures that two sequences contain the exact same elements using JUnit's {@code assertEquals} method.
     * @param a the first sequence.
     * @param b the second sequence.
     */
    public static void compareSequences(Sequenced a, Sequenced b) {
        Iterator<Data> aIter = a.iter();
        Iterator<Data> bIter = b.iter();

        while (aIter.hasNext() || bIter.hasNext()) {
            assertEquals(aIter.next(), bIter.next());
        }
    }

    public static Valued i64(long l) {
        return new LiteralNode(AlchemyInt.of(l));
    }

    public static Valued f64(double d) {
        return new LiteralNode(AlchemyFloat.of(d));
    }

}
