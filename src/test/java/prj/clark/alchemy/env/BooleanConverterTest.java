package prj.clark.alchemy.env;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanConverterTest {
    private Converter c;
    private static final Data TRUE = LangBool.of(true);
    private static final Data FALSE = LangBool.of(false);

    @Before
    public void setUp() {
        c = new BooleanConverter();
    }

    @Test
    public void negativeIntTruthy() {
        assertEquals(TRUE, c.convert(LangInt.of(-1)));
        assertEquals(TRUE, c.convert(LangInt.of(Integer.MIN_VALUE)));
    }

    @Test
    public void positiveIntTruthy() {
        assertEquals(TRUE, c.convert(LangInt.of(1)));
        assertEquals(TRUE, c.convert(LangInt.of(Integer.MAX_VALUE)));
    }

    @Test
    public void zeroIntFalsy() {
        assertEquals(FALSE, c.convert(LangInt.of(0)));
    }

    @Test
    public void negativeFloatTruthy() {
        assertEquals(TRUE, c.convert(LangFloat.of(-10.0)));
        assertEquals(TRUE, c.convert(LangFloat.of(Double.MIN_VALUE)));
    }

    @Test
    public void positiveFloatTruthy() {
        assertEquals(TRUE, c.convert(LangFloat.of(10.0)));
        assertEquals(TRUE, c.convert(LangFloat.of(Double.MAX_VALUE)));
    }

    @Test
    public void zeroFloatFalsy() {
        assertEquals(FALSE, c.convert(LangFloat.of(0.0)));
    }

    @Test
    public void nonEmptyStringTruthy() {
        assertEquals(TRUE, c.convert(LangString.of("hello, world")));
    }

    @Test
    public void emptyStringFalsy() {
        assertEquals(FALSE, c.convert(LangString.of("")));
    }

    @Test
    public void trueBoolTruthy() {
        assertEquals(TRUE, c.convert(LangBool.of(true)));
    }

    @Test
    public void falseBoolFalsy() {
        assertEquals(FALSE, c.convert(LangBool.of(false)));
    }
}