package prj.clark.alchemy.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlchemyFloatTest {
    @Test(expected = IllegalArgumentException.class)
    public void nonFloatStringIllegal() {
        AlchemyFloat.of("abcd");
    }

    @Test
    public void intStringProducesCorrectValue() {
        AlchemyFloat f = AlchemyFloat.of("123");
        assertEquals(123.0, f.floatValue(), 0.0);
    }

    @Test
    public void floatStringProducesCorrectValue() {
        AlchemyFloat f = AlchemyFloat.of("123.5");
        assertEquals(123.5, f.floatValue(), 0.0);
    }

    @Test
    public void fromIntProducesCorrectString() {
        AlchemyFloat f = AlchemyFloat.of(55);
        assertEquals("55.0", f.toString());
        assertEquals(f.print(), f.toString());
    }

    @Test
    public void fromFloatProducesCorrectString() {
        AlchemyFloat f = AlchemyFloat.of(12.75);
        assertEquals("12.75", f.toString());
        assertEquals(f.print(), f.toString());

    }

    @Test
    public void unequalValuesNotEqual() {
        Data f = AlchemyFloat.of(123);
        Data g = AlchemyFloat.of(456);
        assertNotEquals(f, g);
    }

    @Test
    public void equalValuesEqual() {
        Data f = AlchemyFloat.of("123");
        Data g = AlchemyFloat.of(123);
        assertEquals(f, g);
    }
}