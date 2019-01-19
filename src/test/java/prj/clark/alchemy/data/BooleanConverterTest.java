package prj.clark.alchemy.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanConverterTest {
    private Converter c;
    private static final Data TRUE = AlchemyBoolean.of(true);
    private static final Data FALSE = AlchemyBoolean.of(false);

    @Before
    public void setUp() {
        c = new BooleanConverter();
    }

    @Test
    public void negativeIntTruthy() {
        assertEquals(TRUE, c.convert(AlchemyInt.of(-1)));
        assertEquals(TRUE, c.convert(AlchemyInt.of(Integer.MIN_VALUE)));
    }

    @Test
    public void positiveIntTruthy() {
        assertEquals(TRUE, c.convert(AlchemyInt.of(1)));
        assertEquals(TRUE, c.convert(AlchemyInt.of(Integer.MAX_VALUE)));
    }

    @Test
    public void zeroIntFalsy() {
        assertEquals(FALSE, c.convert(AlchemyInt.of(0)));
    }

    @Test
    public void negativeFloatTruthy() {
        assertEquals(TRUE, c.convert(AlchemyFloat.of(-10.0)));
        assertEquals(TRUE, c.convert(AlchemyFloat.of(Double.MIN_VALUE)));
    }

    @Test
    public void positiveFloatTruthy() {
        assertEquals(TRUE, c.convert(AlchemyFloat.of(10.0)));
        assertEquals(TRUE, c.convert(AlchemyFloat.of(Double.MAX_VALUE)));
    }

    @Test
    public void zeroFloatFalsy() {
        assertEquals(FALSE, c.convert(AlchemyFloat.of(0.0)));
    }

    @Test
    public void nonEmptyStringTruthy() {
        assertEquals(TRUE, c.convert(AlchemyString.of("hello, world")));
    }

    @Test
    public void emptyStringFalsy() {
        assertEquals(FALSE, c.convert(AlchemyString.of("")));
    }

    @Test
    public void trueBoolTruthy() {
        assertEquals(TRUE, c.convert(AlchemyBoolean.of(true)));
    }

    @Test
    public void falseBoolFalsy() {
        assertEquals(FALSE, c.convert(AlchemyBoolean.of(false)));
    }
}