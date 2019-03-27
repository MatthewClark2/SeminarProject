package prj.clark.alchemy;

import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.tree.LiteralNode;
import prj.clark.alchemy.tree.ReferentiallyTransparentValuedNode;
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
     * This class is useful for ensuring that certain nodes aren't evaluated at runtime.
     */
    public static class FailingValuedNode extends ReferentiallyTransparentValuedNode {
        /**
         * Fails instantly.
         * @param ctx ignored
         * @return nothing
         * @throws UnsupportedOperationException when called.
         */
        @Override
        public Data evaluate(Context ctx) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Ensures that two sequences contain the exact same elements using JUnit's {@code assertEquals} method.
     * @param a the first sequence.
     * @param b the second sequence.
     */
    public static void compareSequences(Sequenced a, Sequenced b) {
        Iterator<Data> aIter = a.iterator();
        Iterator<Data> bIter = b.iterator();

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

    public static Valued bool(boolean b) {
        return new LiteralNode(AlchemyBoolean.of(b));
    }

    public static Valued str(String s) {
        return new LiteralNode(AlchemyString.of(s));
    }
}
