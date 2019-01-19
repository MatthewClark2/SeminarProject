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
        AlchemyFloat f = (AlchemyFloat) AlchemyFloat.of("123");
        assertEquals((Double) 123.0, f.getValue());
    }

    @Test
    public void floatStringProducesCorrectValue() {
        AlchemyFloat f = (AlchemyFloat) AlchemyFloat.of("123.5");
        assertEquals((Double) 123.5, f.getValue());
    }

    @Test
    public void fromIntProducesCorrectString() {
        Data f = AlchemyFloat.of(55);
        assertEquals("55.0", f.toString());
    }

    @Test
    public void fromFloatProducesCorrectString() {
        Data f = AlchemyFloat.of(12.75);
        assertEquals("12.75", f.toString());
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

    @Test
    public void generatedValuesOfTypeFloat() {
        Data f = AlchemyFloat.of("12");
        Data g = AlchemyFloat.of(1.5);

        assertEquals(PrimitiveType.FLOAT, f.getType());
        assertEquals(PrimitiveType.FLOAT, g.getType());
    }
}