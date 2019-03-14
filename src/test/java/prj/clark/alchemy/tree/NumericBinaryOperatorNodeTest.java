package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.AlchemyInt;
import prj.clark.alchemy.data.AlchemyString;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.err.TypeMismatchException;

import static org.junit.Assert.assertEquals;
import static prj.clark.alchemy.TestUtils.f64;
import static prj.clark.alchemy.TestUtils.i64;

public abstract class NumericBinaryOperatorNodeTest {
    protected abstract Data apply(Valued a, Valued b);

    protected abstract double apply(double a, double b);

    @Test
    public void correctValueProduced() {
        assertEquals(AlchemyInt.of((long) apply(5, -2)), apply(i64(5), i64(-2)));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonNumericLeftOperandThrowsException() {
        apply(new LiteralNode(AlchemyString.of("5")), i64(3));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonNumericRightOperandThrowsException() {
        apply(f64(23.5), new LiteralNode(AlchemyString.of("5")));
    }

    @Test
    public void leftFloatProducesFloat() {
        assertEquals(AlchemyFloat.of(apply(10.5, 1)), apply(f64(10.5), i64(1)));
    }

    @Test
    public void rightFloatProducesFloat() {
        assertEquals(AlchemyFloat.of(apply(1, 10.5)), apply(i64(1), f64(10.5)));
    }

}
