package prj.clark.alchemy.data;

import org.junit.Test;

import static org.junit.Assert.*;

// TODO(matthew-c21) - This test suite deliberately avoids overflows as it is undefined behavior. Fix that in the language spec.
public class LangIntTest {
    @Test(expected = IllegalArgumentException.class)
    public void floatStringInvalid() {
        LangInt.of("123.45");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyStringThrowsException() {
        LangInt.of("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonNumericStringThrowsException() {
        LangInt.of("invalid");
    }

    @Test
    public void intStringPreserved() {
        Data i = LangInt.of("9000000");
        assertEquals(Long.valueOf(9000000), ((LangInt) i).getValue());
    }

    @Test
    public void toStringMethodProducesIntegerString() {
        Data i = LangInt.of(456);
        assertEquals("456", i.toString());
    }

    @Test
    public void equalValuesEqual() {
        Data i = LangInt.of(12);
        Data j = LangInt.of("12");

        assertEquals(i, j);
    }

    @Test
    public void unequalValuesNotEqual() {
        Data i = LangInt.of(34);
        Data j = LangInt.of("56");

        assertNotEquals(i, j);
    }

    @Test
    public void generatedValuesOfTypeInt() {
        Data i = LangInt.of(100);
        Data j = LangInt.of("-35");

        assertEquals(PrimitiveType.INT, i.getType());
        assertEquals(PrimitiveType.INT, j.getType());
    }
}