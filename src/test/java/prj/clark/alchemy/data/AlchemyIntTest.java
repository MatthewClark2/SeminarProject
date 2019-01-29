package prj.clark.alchemy.data;

import org.junit.Test;

import static org.junit.Assert.*;

// TODO(matthew-c21) - This test suite deliberately avoids overflows as it is undefined behavior. Fix that in the language spec.
public class AlchemyIntTest {
    @Test(expected = IllegalArgumentException.class)
    public void floatStringInvalid() {
        AlchemyInt.of("123.45");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyStringThrowsException() {
        AlchemyInt.of("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonNumericStringThrowsException() {
        AlchemyInt.of("invalid");
    }

    @Test
    public void intStringPreserved() {
        AlchemyInt i = AlchemyInt.of("9000000");
        assertEquals(9000000, i.intValue());
    }

    @Test
    public void toStringMethodProducesIntegerString() {
        Data i = AlchemyInt.of(456);
        assertEquals("456", i.toString());
        assertEquals(i.toString(), ((AlchemyInt) i).print());
    }

    @Test
    public void equalValuesEqual() {
        Data i = AlchemyInt.of(12);
        Data j = AlchemyInt.of("12");

        assertEquals(i, j);
    }

    @Test
    public void unequalValuesNotEqual() {
        Data i = AlchemyInt.of(34);
        Data j = AlchemyInt.of("56");

        assertNotEquals(i, j);
    }
}