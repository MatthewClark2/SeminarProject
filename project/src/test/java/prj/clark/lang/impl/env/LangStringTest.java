package prj.clark.lang.impl.env;

import org.junit.Test;

import static org.junit.Assert.*;

public class LangStringTest {
    @Test
    public void equalValuesEqual() {
        Data s = LangString.of("hello");
        // While the call to new is redundant, it does allow for me to ensure that there isn't an underlying bug being
        // hidden by the caching of the string literal.
        Data t = LangString.of(new String("hello"));
        assertEquals(s, t);
    }

    @Test
    public void unequalValuesNotEqual() {
        Data s = LangString.of("hello");
        Data t = LangString.of("goodbye");
        assertNotEquals(s, t);
    }

    @Test
    public void generatedValuesOfTypeString() {
        Data s = LangString.of("hello world");
        assertEquals(PrimitiveType.STRING, s.getType());
    }
}