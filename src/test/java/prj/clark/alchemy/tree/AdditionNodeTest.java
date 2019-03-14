package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.err.TypeMismatchException;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.i64;
import static prj.clark.alchemy.TestUtils.f64;

public class AdditionNodeTest {
    private static Data add(Valued a, Valued b) {
        return new AdditionNode(a, b).evaluate(null);
    }

    @Test
    public void correctValueProduced() {
        assertEquals(AlchemyInt.of(3), add(i64(5), i64(-2)));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonNumericLeftOperandThrowsException() {
        add(new LiteralNode(AlchemyString.of("5")), i64(3));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonNumericRightOperandThrowsException() {
        add(f64(23.5), new LiteralNode(AlchemyString.of("5")));
    }

    @Test
    public void leftFloatProducesFloat() {
        assertEquals(AlchemyFloat.of(11.5), add(f64(10.5), i64(1)));
    }

    @Test
    public void rightFloatProducesFloat() {
        assertEquals(AlchemyFloat.of(11.5), add(i64(1), f64(10.5)));
    }
}