package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.AlchemyInt;
import prj.clark.alchemy.data.AlchemyString;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.err.TypeMismatchException;

import static org.junit.Assert.*;

public class SubtractionNodeTest {
    private static Valued i64(long l) {
        return new LiteralNode(AlchemyInt.of(l));
    }

    private static Valued f64(double d) {
        return new LiteralNode(AlchemyFloat.of(d));
    }

    private static Data sub(Valued a, Valued b) {
        return new SubtractionNode(a, b).evaluate(null);
    }

    @Test
    public void correctValueProduced() {
        assertEquals(AlchemyInt.of(7), sub(i64(5), i64(-2)));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonNumericLeftOperandThrowsException() {
        sub(new LiteralNode(AlchemyString.of("5")), i64(3));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonNumericRightOperandThrowsException() {
        sub(f64(23.5), new LiteralNode(AlchemyString.of("5")));
    }

    @Test
    public void leftFloatProducesFloat() {
        assertEquals(AlchemyFloat.of(9.5), sub(f64(10.5), i64(1)));
    }

    @Test
    public void rightFloatProducesFloat() {
        assertEquals(AlchemyFloat.of(-9.5), sub(i64(1), f64(10.5)));
    }
}