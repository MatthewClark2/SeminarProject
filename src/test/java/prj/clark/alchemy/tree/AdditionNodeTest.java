package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.err.TypeMismatchException;

import static org.junit.Assert.*;

public class AdditionNodeTest {
    private static Valued i64(long l) {
        return new LiteralNode(AlchemyInt.of(l));
    }

    private static Valued f64(double d) {
        return new LiteralNode(AlchemyFloat.of(d));
    }

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