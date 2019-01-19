package prj.clark.alchemy.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlchemyBooleanTest {
    @Test
    public void trueStringCreatesTrue() {
        Data b = AlchemyBoolean.of("true");
        assertTrue(((AlchemyBoolean) b).getValue());
    }

    @Test
    public void falseStringCreatesFalse() {
        Data b = AlchemyBoolean.of("false");
        assertFalse(((AlchemyBoolean) b).getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalStringThrowsException() {
        Data b = AlchemyBoolean.of("garbage");
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectCasingThrowsException() {
        Data b = AlchemyBoolean.of("True");
    }

    @Test
    public void literalTrueIsTrue() {
        Data b = AlchemyBoolean.of(true);
        assertTrue(((AlchemyBoolean) b).getValue());
    }

    @Test
    public void literalFalseIsFalse() {
        Data b = AlchemyBoolean.of(false);
        assertFalse(((AlchemyBoolean) b).getValue());
    }

    @Test
    public void trueValuesEqual() {
        Data a = AlchemyBoolean.of("true");
        Data b = AlchemyBoolean.of(true);
        assertEquals(a, b);
    }

    @Test
    public void falseValuesEqual() {
        Data a = AlchemyBoolean.of("false");
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
    public void generatedValuesOfTypeBool() {
        Data a = AlchemyBoolean.of("true");
        Data b = AlchemyBoolean.of(true);
        Data c = AlchemyBoolean.of("false");
        Data d = AlchemyBoolean.of(false);

        assertEquals(PrimitiveType.BOOL, a.getType());
        assertEquals(PrimitiveType.BOOL, b.getType());
        assertEquals(PrimitiveType.BOOL, c.getType());
        assertEquals(PrimitiveType.BOOL, d.getType());
    }

    @Test
    public void trueToString() {

    }

    @Test
    public void falseToString() {

    }
}