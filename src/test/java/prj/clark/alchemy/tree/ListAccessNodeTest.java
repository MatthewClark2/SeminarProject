package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.AlchemyInt;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Arrays;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.*;

public class ListAccessNodeTest {
    private static Valued lst(Valued... elems) {
        return new ListLiteralNode(Arrays.asList(elems));
    }

    @Test
    public void basicIndex() {
        Valued list = lst(i64(1), i64(3), i64(5), i64(7), i64(11));
        assertEquals(AlchemyInt.of(1), new ListAccessNode(list, i64(0)).evaluate(null));
        assertEquals(AlchemyInt.of(3), new ListAccessNode(list, i64(1)).evaluate(null));
        assertEquals(AlchemyInt.of(5), new ListAccessNode(list, i64(2)).evaluate(null));
        assertEquals(AlchemyInt.of(7), new ListAccessNode(list, i64(3)).evaluate(null));
        assertEquals(AlchemyInt.of(11), new ListAccessNode(list, i64(4)).evaluate(null));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonCollectionIndexed() {
        new ListAccessNode(f64(5.0), i64(1)).evaluate(null);
    }

    @Test(expected = TypeMismatchException.class)
    public void illegalIndexingValue() {
        Valued list = lst(i64(1), i64(3), i64(5), i64(7), i64(11));
        new ListAccessNode(list, str("Hello, world!"));
    }
}