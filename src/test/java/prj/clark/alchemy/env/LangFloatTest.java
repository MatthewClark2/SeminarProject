package prj.clark.alchemy.env;

import org.junit.Test;

import static org.junit.Assert.*;

public class LangFloatTest {
    @Test(expected = IllegalArgumentException.class)
    public void nonFloatStringIllegal() {
        LangFloat.of("abcd");
    }

    @Test
    public void intStringProducesCorrectValue() {
        LangFloat f = (LangFloat) LangFloat.of("123");
        assertEquals((Double) 123.0, f.getValue());
    }

    @Test
    public void floatStringProducesCorrectValue() {
        LangFloat f = (LangFloat) LangFloat.of("123.5");
        assertEquals((Double) 123.5, f.getValue());
    }

    @Test
    public void fromIntProducesCorrectString() {
        Data f = LangFloat.of(55);
        assertEquals("55.0", f.toString());
    }

    @Test
    public void fromFloatProducesCorrectString() {
        Data f = LangFloat.of(12.75);
        assertEquals("12.75", f.toString());
    }

    @Test
    public void unequalValuesNotEqual() {
        Data f = LangFloat.of(123);
        Data g = LangFloat.of(456);
        assertNotEquals(f, g);
    }

    @Test
    public void equalValuesEqual() {
        Data f = LangFloat.of("123");
        Data g = LangFloat.of(123);
        assertEquals(f, g);
    }

    @Test
    public void generatedValuesOfTypeFloat() {
        Data f = LangFloat.of("12");
        Data g = LangFloat.of(1.5);

        assertEquals(PrimitiveType.FLOAT, f.getType());
        assertEquals(PrimitiveType.FLOAT, g.getType());
    }
}