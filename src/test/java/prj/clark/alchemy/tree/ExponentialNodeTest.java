package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.Data;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.f64;
import static prj.clark.alchemy.TestUtils.i64;

public class ExponentialNodeTest extends NumericBinaryOperatorNodeTest {

    @Override
    protected Data apply(Valued a, Valued b) {
        return new ExponentialNode(a, b).evaluate(null);
    }

    @Override
    protected double apply(double a, double b) {
        return Math.pow(a, b);
    }

    @Test
    public void zeroPowerReturnsOne() {
        assertEquals(apply(i64(5), i64(0)), AlchemyFloat.of(1));
    }

    @Test
    public void negativePowerBehavesCorrectly() {
        assertEquals(apply(i64(2), i64(-2)), AlchemyFloat.of(0.25));
    }

    @Test
    public void fractionalPowerReturnsCorrectValue() {
        assertEquals(apply(i64(4), f64(0.5)), AlchemyFloat.of(2));
    }
}
