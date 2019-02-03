package prj.clark.alchemy.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlchemyStringTest {
    @Test
    public void toStringReturnsCorrectStringValue() {
        Data s = AlchemyString.of("asdf");
        Data t = AlchemyString.of("lkjlkhkj");

        assertEquals("asdf", s.toString());
        assertEquals("lkjlkhkj", t.toString());
    }

    @Test
    public void toStringBehavesCorrectly() {
        Data s = AlchemyString.of("123");
        assertEquals("123", s.toString());
    }

    @Test
    public void ensurePrintAndToStringSame() {
        AlchemyString s = AlchemyString.of("asdf");
        AlchemyString t = AlchemyString.of("jlk;");
        assertEquals(s.toString(), s.print());
        assertEquals(t.toString(), s.print());
    }

    @Test
    public void equalValuesEqual() {
        Data s = AlchemyString.of("hello");
        // While the call to new is redundant, it does allow for me to ensure that there isn't an underlying bug being
        // hidden by the caching of the string literal.
        Data t = AlchemyString.of(new String("hello"));
        assertEquals(s, t);
    }

    @Test
    public void unequalValuesNotEqual() {
        Data s = AlchemyString.of("hello");
        Data t = AlchemyString.of("goodbye");
        assertNotEquals(s, t);
    }

    @Test
    public void equalValuesShareHash() {
        assertEquals(AlchemyString.of("hello").hashCode(), AlchemyString.of("hello").hashCode());
    }

    @Test
    public void unqualValuesDoNotShareHash() {
        assertNotEquals(AlchemyString.of("abc").hashCode(), AlchemyString.of("xyzasdf").hashCode());
    }

    @Test
    public void truthinessIsCorrect() {
        assertTrue(AlchemyString.of("a").toBoolean());
        assertFalse(AlchemyString.of("").toBoolean());
    }
}