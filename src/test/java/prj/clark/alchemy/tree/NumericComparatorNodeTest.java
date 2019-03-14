package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.AlchemyBoolean;
import prj.clark.alchemy.data.AlchemyString;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.err.TypeMismatchException;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.f64;
import static prj.clark.alchemy.TestUtils.i64;

public abstract class NumericComparatorNodeTest {
    protected abstract Data apply(Valued left, Valued right);

    protected abstract boolean apply(double left, double right);

    @Test
    public void leftOperandLarger() {
        assertEquals(AlchemyBoolean.of(apply(2, 1)), apply(i64(2), i64(1)));
    }

    @Test
    public void rightOperandLarger() {
        assertEquals(AlchemyBoolean.of(apply(-1, 5)), apply(i64(-1), i64(5)));
    }

    @Test
    public void leftAndRightOperandEqual() {
        assertEquals(AlchemyBoolean.of(apply(0.5, 0.5)), apply(f64(0.5), f64(0.5)));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonNumericLeftOperandThrowsException() {
        apply(new LiteralNode(AlchemyString.of("5")), i64(3));
    }

    @Test(expected = TypeMismatchException.class)
    public void nonNumericRightOperandThrowsException() {
        apply(f64(23.5), new LiteralNode(AlchemyString.of("5")));
    }

}
