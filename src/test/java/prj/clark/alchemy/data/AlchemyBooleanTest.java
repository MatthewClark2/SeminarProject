package prj.clark.alchemy.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlchemyBooleanTest {
    @Test
    public void trueStringCreatesTrue() {
        Data b = AlchemyBoolean.of("True");
        assertTrue(b.toBoolean());
    }

    @Test
    public void falseStringCreatesFalse() {
        Data b = AlchemyBoolean.of("False");
        assertFalse(b.toBoolean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalStringThrowsException() {
        Data b = AlchemyBoolean.of("garbage");
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectCasingThrowsException() {
        Data b = AlchemyBoolean.of("true");
    }

    @Test
    public void literalTrueIsTrue() {
        Data b = AlchemyBoolean.of(true);
        assertTrue(b.toBoolean());
    }

    @Test
    public void literalFalseIsFalse() {
        Data b = AlchemyBoolean.of(false);
        assertFalse(b.toBoolean());
    }

    @Test
    public void trueValuesEqual() {
        Data a = AlchemyBoolean.of("True");
        Data b = AlchemyBoolean.of(true);
        assertEquals(a, b);
    }

    @Test
    public void falseValuesEqual() {
        Data a = AlchemyBoolean.of("False");
        Data b = AlchemyBoolean.of(false);
        assertEquals(a, b);
    }

    @Test
    public void trueAndFalseNotEqual() {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyBoolean.of(false);
        assertNotEquals(a, b);

    }

    @Test
    public void trueToString() {
        assertEquals("True", AlchemyBoolean.TRUE.print());
    }

    @Test
    public void falseToString() {
        assertEquals("False", AlchemyBoolean.FALSE.print());
    }

    @Test
    public void equivalentValuesShareHash() {
        assertEquals(AlchemyBoolean.of(true).hashCode(), AlchemyBoolean.of(true).hashCode());
    }

    @Test
    public void unequalValuesDoNotShareHash() {
        assertNotEquals(AlchemyBoolean.of(false).hashCode(), AlchemyBoolean.of(true).hashCode());
    }

    @Test
    public void toStringBehavesCorrectly() {
        Data a = AlchemyBoolean.of(true);
        Data b = AlchemyBoolean.of(false);

        assertEquals("True", a.toString());
        assertEquals("False", b.toString());
    }

    @Test
    public void toStringAndPrintIdentical() {
        Printable a = AlchemyBoolean.of(true);
        Printable b = AlchemyBoolean.of(false);

        assertEquals(a.toString(), a.print());
        assertEquals(b.toString(), b.print());
    }

    @Test
    public void truthinessIsCorrect() {
        assertTrue(AlchemyBoolean.TRUE.toBoolean());
        assertFalse(AlchemyBoolean.FALSE.toBoolean());
    }
}