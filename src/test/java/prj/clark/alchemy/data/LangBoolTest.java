package prj.clark.alchemy.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class LangBoolTest {
    @Test
    public void trueStringCreatesTrue() {
        Data b = LangBool.of("true");
        assertTrue(((LangBool) b).getValue());
    }

    @Test
    public void falseStringCreatesFalse() {
        Data b = LangBool.of("false");
        assertFalse(((LangBool) b).getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalStringThrowsException() {
        Data b = LangBool.of("garbage");
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectCasingThrowsException() {
        Data b = LangBool.of("True");
    }

    @Test
    public void literalTrueIsTrue() {
        Data b = LangBool.of(true);
        assertTrue(((LangBool) b).getValue());
    }

    @Test
    public void literalFalseIsFalse() {
        Data b = LangBool.of(false);
        assertFalse(((LangBool) b).getValue());
    }

    @Test
    public void trueValuesEqual() {
        Data a = LangBool.of("true");
        Data b = LangBool.of(true);
        assertEquals(a, b);
    }

    @Test
    public void falseValuesEqual() {
        Data a = LangBool.of("false");
        Data b = LangBool.of(false);
        assertEquals(a, b);
    }

    @Test
    public void trueAndFalseNotEqual() {
        Data a = LangBool.of(true);
        Data b = LangBool.of(false);
        assertNotEquals(a, b);

    }

    @Test
    public void generatedValuesOfTypeBool() {
        Data a = LangBool.of("true");
        Data b = LangBool.of(true);
        Data c = LangBool.of("false");
        Data d = LangBool.of(false);

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